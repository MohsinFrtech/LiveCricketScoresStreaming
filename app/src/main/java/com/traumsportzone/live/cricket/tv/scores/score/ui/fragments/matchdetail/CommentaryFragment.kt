package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.CommentaryFragmentBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentLiveDetailBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.CommentaryAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.OverAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.SquadAdapter
import com.traumsportzone.live.cricket.tv.scores.score.model.CommentryModelClass
import com.traumsportzone.live.cricket.tv.scores.score.model.OverItemModel
import com.traumsportzone.live.cricket.tv.scores.score.utility.listeners.ApiResponseListener
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.CommentaryViewModel

class CommentaryFragment : Fragment(), ApiResponseListener {
    private val commentaryViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CommentaryViewModel::class.java]
    }
    private var binding: CommentaryFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.commentary_fragment, container, false)
        binding = DataBindingUtil.bind(layout)
        binding?.lifecycleOwner = this
        commentaryViewModel.apiResponseListener = this
        initialState()
        getAllCommentaryRelatedToMatch()

        getSquadInfo()
        return layout
    }



    private fun initialState() {

        binding?.recentOvs?.visibility = View.GONE
        binding?.liveMatchDetail?.visibility = View.GONE
        binding?.recyclerViewCommentary?.visibility = View.GONE
        binding?.ketStatResult?.visibility = View.GONE
        binding?.playerOftheMatch?.visibility = View.GONE
        binding?.playerOftheMatchName?.visibility = View.GONE
        binding?.commentaryNotAvailable?.visibility = View.GONE
    }

    private fun getSquadInfo() {
        commentaryViewModel.getMatchTeamSquad()
        commentaryViewModel.squadModel.observe(viewLifecycleOwner, Observer {
            if (it != null) {

            }
        })
    }

    private fun getAllCommentaryRelatedToMatch() {
        commentaryViewModel.loadCommentary()
        commentaryViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding?.MainLottie?.visibility = View.VISIBLE
            } else {
                binding?.MainLottie?.visibility = View.GONE
            }
        })

        commentaryViewModel?.commentaryModel?.observe(viewLifecycleOwner, Observer {
            if (!it?.comWrapper.isNullOrEmpty()) {
                binding?.model2 = it
                if (it != null) {
                    setUpCommentaryData(it)
                }
            }
            else{
                binding?.recentOvs?.visibility = View.GONE
                binding?.liveMatchDetail?.visibility = View.GONE
                binding?.recyclerViewCommentary?.visibility = View.GONE
                binding?.ketStatResult?.visibility = View.GONE
                binding?.playerOftheMatch?.visibility = View.GONE
                binding?.playerOftheMatchName?.visibility = View.GONE
                binding?.commentaryNotAvailable?.visibility = View.VISIBLE
            }
        })
    }

    //Set up of commentary data....
    private fun setUpCommentaryData(commentryModelClass: CommentryModelClass) {
        if (commentryModelClass?.matchHeaders != null) {
            binding?.commentaryNotAvailable?.visibility = View.GONE
            if (commentryModelClass?.matchHeaders?.state?.equals("Preview", true) == true
                || commentryModelClass?.matchHeaders?.state?.equals("Delay", true) == true
                ||commentryModelClass?.matchHeaders?.state?.equals("Abandon", true) == true

            ) {
                //match is preview means will started soon.
                binding?.ketStatResult?.visibility = View.GONE
                binding?.rcText?.visibility = View.GONE
                binding?.recentOvs?.visibility = View.GONE
                binding?.playerOftheMatch?.visibility = View.GONE
                binding?.playerOftheMatchName?.visibility = View.GONE
                binding?.liveMatchDetail?.visibility = View.GONE
                if (!commentryModelClass.comWrapper.isNullOrEmpty()) {

                    val listAdapter = CommentaryAdapter()
                    binding?.recyclerViewCommentary?.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding?.recyclerViewCommentary?.adapter = listAdapter
                    listAdapter.submitList(commentryModelClass.comWrapper)
                    binding?.recyclerViewCommentary?.visibility = View.VISIBLE

                }
            } else {

                if (commentryModelClass?.matchHeaders?.state?.equals("Complete", true) == true) {

                    if (!commentryModelClass.matchHeaders!!.playersOfTheMatch.isNullOrEmpty()) {

                        commentryModelClass.matchHeaders!!.playersOfTheMatch.forEach {
                            binding?.playerOftheMatch?.visibility = View.VISIBLE
                            binding?.playerOftheMatchName?.visibility = View.VISIBLE
                            binding?.playerOftheMatchName?.text = it.fullName
                        }
                    } else {
                        binding?.playerOftheMatch?.visibility = View.GONE
                        binding?.playerOftheMatchName?.visibility = View.GONE
                    }
                } else {
                    binding?.playerOftheMatch?.visibility = View.GONE
                    binding?.playerOftheMatchName?.visibility = View.GONE
                }
                if (!commentryModelClass.comWrapper.isNullOrEmpty()) {

                    val listAdapter = CommentaryAdapter()
                    binding?.recyclerViewCommentary?.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding?.recyclerViewCommentary?.adapter = listAdapter
                    listAdapter.submitList(commentryModelClass.comWrapper)
                    binding?.recyclerViewCommentary?.visibility = View.VISIBLE

                } else {
                    binding?.recyclerViewCommentary?.visibility = View.GONE
                }

                if (commentryModelClass?.matchHeaders?.state?.equals("Toss", true) == true) {
                    binding?.liveMatchDetail?.visibility = View.GONE
                    binding?.ketStatResult?.visibility = View.GONE
                    binding?.recentOvs?.visibility = View.GONE
                    binding?.rcText?.visibility = View.GONE
                    binding?.playerOftheMatch?.visibility = View.GONE
                    binding?.playerOftheMatchName?.visibility = View.GONE

                } else {
                    if (commentryModelClass.miniscore != null) {
                        binding?.ketStatResult?.visibility = View.VISIBLE
                        if (commentryModelClass.miniscore!!.recentOvsStats != null) {
                            val array =
                                commentryModelClass.miniscore!!.recentOvsStats?.toCharArray()
                            val newcharArray = ArrayList<OverItemModel>()
                            var count = 0
                            var scoreCount = 0

                            array?.forEach {
                                if (!Character.isWhitespace(it)) {
                                    if (it != '|') {
                                        if (it != '.') {

                                            if (isIntegerInRange(it)) {
                                                val intValue =Character.getNumericValue(it)
                                                scoreCount += intValue
                                            }

//                                            newcharArray.add(it.toString())
//                                            Log.d("RecentOvssss", "ovs" + it)

                                            if (count == "5".toInt() || count == "11".toInt()) {

                                                newcharArray.add(OverItemModel(it.toString()))
                                                newcharArray.add(OverItemModel("|  $scoreCount"))
//                                                if (newcharArray.remove("|")){
//                                                    scoreCount = newcharArray.sumOf { it.toInt() }
//                                                }

                                                Log.d("SumofScore", scoreCount.toString())
                                                scoreCount =0
                                                //  newcharArray.add("| "+scoreCount)
                                                count++
                                            } else {
                                                newcharArray.add(OverItemModel(it.toString()))
                                                Log.d("RecentOvssss", "ovs" + it)
                                                count++
                                            }
                                        }
                                    }
                                }
                            }
                            binding?.recentOvs?.visibility = View.VISIBLE
                            binding?.rcText?.visibility = View.VISIBLE

                            val listAdapter = OverAdapter(requireContext(), newcharArray)
                            binding?.recentOvs?.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            binding?.recentOvs?.adapter = listAdapter
                            listAdapter.submitList(newcharArray)
                        } else {
                            binding?.recentOvs?.visibility = View.GONE
                        }


                        if (commentryModelClass.miniscore!!.partnerShip != null) {
                            binding?.presult?.text =   commentryModelClass?.miniscore?.partnerShip.toString()
                        } else {
                            binding?.patner?.visibility = View.GONE
                            binding?.presult?.visibility = View.GONE
                        }

                        if (commentryModelClass.miniscore!!.lastWicket != null) {

                            if (commentryModelClass.miniscore!!.lastWicket != null) {
                                binding?.lastwicresult?.visibility = View.VISIBLE
                                binding?.lastwicresult?.text =
                                    commentryModelClass.miniscore!!.lastWicket.toString()
                            } else {
                                binding?.lastwic?.visibility = View.GONE
                                binding?.lastwicresult?.visibility = View.GONE
                            }
                        } else {
                            binding?.lastwic?.visibility = View.GONE
                            binding?.lastwicresult?.visibility = View.GONE
                        }

                        //overs left....
                        if (commentryModelClass.miniscore!!.oversRem != null) {

                            if (commentryModelClass.miniscore!!.oversRem != null) {
                                binding?.overLeftResult?.visibility = View.VISIBLE
                                binding?.overLeftResult?.text =
                                    commentryModelClass.miniscore!!.oversRem.toString()
                            } else {
                                binding?.overLeft?.visibility = View.GONE
                                binding?.overLeftResult?.visibility = View.GONE
                            }
                        } else {
                            binding?.overLeft?.visibility = View.GONE
                            binding?.overLeftResult?.visibility = View.GONE
                        }

                        //Toss Result
                        if (commentryModelClass.matchHeaders != null) {

                            if (commentryModelClass.matchHeaders?.tossResults != null) {
                                binding?.tossResult?.visibility = View.VISIBLE
                                binding?.tossResult?.text =
                                    "" + commentryModelClass.matchHeaders?.tossResults?.tossWinnerName.toString() + "" +
                                            "(" + commentryModelClass.matchHeaders?.tossResults?.decision.toString() + ")"
                            } else {
                                binding?.toss?.visibility = View.GONE
                                binding?.tossResult?.visibility = View.GONE
                            }
                        } else {
                            binding?.toss?.visibility = View.GONE
                            binding?.tossResult?.visibility = View.GONE
                        }

                        binding?.liveMatchDetail?.visibility = View.VISIBLE

                        /////////////////
                        if (commentryModelClass?.matchHeaders?.state?.equals("Complete", true) == true) {
                            binding?.liveMatchDetail?.visibility = View.GONE
                        }

                    } else {
                        binding?.liveMatchDetail?.visibility = View.GONE
                    }
                }


            }

        }
    }

    fun isIntegerInRange(char: Char): Boolean {
        return char.isDigit() && char in '0'..'9'
    }


    override fun onStarted() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
        binding?.recentOvs?.visibility = View.GONE
        binding?.liveMatchDetail?.visibility = View.GONE
        binding?.recyclerViewCommentary?.visibility = View.GONE
        binding?.ketStatResult?.visibility = View.GONE
        binding?.playerOftheMatch?.visibility = View.GONE
        binding?.playerOftheMatchName?.visibility = View.GONE
        binding?.commentaryNotAvailable?.visibility = View.VISIBLE
    }
}
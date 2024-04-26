package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.FragmentTeamsRankBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.TeamRankAdapterNew
import com.traumsportzone.live.cricket.tv.scores.score.model.RankingTeams
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.RankingViewModel
import com.traumsportzone.live.cricket.tv.scores.utils.InternetUtil
import com.traumsportzone.live.cricket.tv.scores.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsRankingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var binding: FragmentTeamsRankBinding? = null
    private val tags = "TeamsRankingFragment"
    private val logger = Logger()
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[RankingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_teams_rank, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this

        showLoading()

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.getSpinnerData()
        )
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding?.leagueRounds?.adapter = aa

        binding?.leagueRounds?.onItemSelectedListener = this

        viewModel.teamsOdiList.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                hideLoading()
            } else {
                this.lifecycleScope.launch(Dispatchers.IO) {
                    if (InternetUtil.isInternetOn(requireContext())) {
                        ////////////   call players ranking Api  ///////////
                        viewModel.getODIRanking()
                        viewModel.getT20Ranking()
                        viewModel.getTestRanking()
                    }
                }

            }
        }



        binding?.ivBackArrow?.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }


    private fun setAdapter(rankingTeams: List<RankingTeams>?) {
        val sortedList = rankingTeams?.sortedWith(compareBy { it.rank })

        val listAdapter = TeamRankAdapterNew {
            if (!it.name.isNullOrEmpty()) {
                this.findNavController().navigate(
                    TeamsRankingFragmentDirections.actionRankingFragmentToTeamsMatchesFragment(
                        it.team_id!!,
                        it.name
                    )
                )
            }
        }
        this.lifecycleScope.launch(Dispatchers.Main) {
            binding?.recyclerViewTeams?.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding?.recyclerViewTeams?.adapter = listAdapter
            listAdapter.submitList(sortedList)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //binding.leagueRounds.selectedItem=parent?.getItemAtPosition(position)

        formatTeamsSelected(position)

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun formatTeamsSelected(name: Any) {
        when (name) {
            0 -> {
                viewModel.teamsOdiList.observe(viewLifecycleOwner) { it ->
                    if (it != null) {
                        setAdapter(it)
                    }
                }
            }
            1 -> {
                viewModel.teamsT20List.observe(viewLifecycleOwner) { it ->
                    if (it != null) {
                        setAdapter(it)
                    }
                }

            }
            2 -> {
                viewModel.teamsTestList.observe(viewLifecycleOwner) { it ->
                    if (it != null) {
                        setAdapter(it)
                    }
                }

            }
        }

    }

    private fun showLoading() {
        binding?.homeAnimLayout?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding?.homeAnimLayout?.visibility = View.GONE
    }

}
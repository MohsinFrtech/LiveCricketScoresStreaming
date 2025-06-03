package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.statsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.StatsFormatFragmentsBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsAverageAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsFiftiesAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsFoursAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsHunderedAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsNintiesAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsRunsAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsSRAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsScoreAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.StatsSixesAdapter
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.StatsViewModel
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.CodeUtils.visibility
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants


class OdiStatsFragment: Fragment() {

    private val statsViewModel by lazy {
        ViewModelProvider(requireActivity())[StatsViewModel::class.java]
    }

    var binding: StatsFormatFragmentsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.stats_format_fragments,container,false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        binding?.modelData = statsViewModel
        if(Constants.statTypeId == Constants.mostWicketstattypeid) {
            setOdiDataMostWickets()
        }else if (Constants.statTypeId == Constants.mostRunsstattypeid) {
            binding?.labelWickets?.text = "R"
            binding?.labelOvers?.text = "I"
            setOdiDataMostRuns()
        }else if (Constants.statTypeId == Constants.highestScorestattypeid) {
            binding?.labelAvg?.text = "VS"
            binding?.labelOvers?.text = "Balls"
            binding?.labelMatches?.text = "SR"
            binding?.labelWickets?.text = "HS"
            setOdiDataHighestScore()
        }else if (Constants.statTypeId == Constants.highestSrstattypeid){
            binding?.labelAvg?.text = "I"
            binding?.labelOvers?.text = "M"
            binding?.labelMatches?.text = "R"
            binding?.labelWickets?.text = "SR"
            setOdiDataHighestSr()
        }else if (Constants.statTypeId == Constants.mostHundredsstattypeid) {
            binding?.labelAvg?.text = "100's"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            setOdiDataMostHundered()
        }else if (Constants.statTypeId == Constants.mostFiftiesstattypeid) {
            binding?.labelAvg?.text = "50s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            setOdiDataMostFifties()
        }else if (Constants.statTypeId == Constants.mostSixesstattypeid) {
            binding?.labelAvg?.text = "6s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            setOdiDataMostSixes()
        }else if (Constants.statTypeId == Constants.mostFoursstattypeid) {
            binding?.labelAvg?.text = "4s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            setOdiDataMostFours()
        }else if (Constants.statTypeId == Constants.highestAvgstattypeid) {
            binding?.labelAvg?.text = "Avg"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            setOdiDataHighestAvg()
        }else if (Constants.statTypeId == Constants.mostNinetiesstattypeid) {
            binding?.labelAvg?.text = "90's"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            setOdiDataMostNinties()
        }

        return view
    }

    //region ODI Most Wickets
    private fun setOdiDataMostWickets(){
        statsViewModel.mostWicket.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                binding?.noResult?.visibility(false)
                val sortedList = it.records

                val listAdapter = StatsAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }


    //region ODI Most Runs
    private fun setOdiDataMostRuns(){
        statsViewModel.mostODIRuns.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                binding?.noResult?.visibility(false)
                val sortedList = it.records

                val listAdapter = StatsRunsAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }
    //endregion

    //region ODI Highest Score
    private fun setOdiDataHighestScore(){
        statsViewModel.mostODIHighestScore.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                binding?.noResult?.visibility(false)
                val sortedList = it.records

                val listAdapter = StatsScoreAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }
    //endregion

    //region ODI Highest Sr
    private fun setOdiDataHighestSr(){
        statsViewModel.mostODIHighestSr.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                binding?.noResult?.visibility(false)
                val sortedList = it.records

                val listAdapter = StatsSRAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }
    //endregion

    //region ODI Most Hundered
    private fun setOdiDataMostHundered(){
        statsViewModel.mostODIMostHundered.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                binding?.noResult?.visibility(false)
                val sortedList = it.records

                val listAdapter = StatsHunderedAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }

    //endregion

    //region ODI Most Fifties
    private fun setOdiDataMostFifties(){
        statsViewModel.mostODIMostFifties.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                binding?.noResult?.visibility(false)
                val sortedList = it.records

                val listAdapter = StatsFiftiesAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }


    //endregion

    //region ODI Most Sixes
    private fun setOdiDataMostSixes(){
        statsViewModel.mostODIMostSix.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                val sortedList = it.records

                val listAdapter = StatsSixesAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }

    //endregion

    //region ODI Most Fours
    private fun setOdiDataMostFours(){
        statsViewModel.mostODIMostFour.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                val sortedList = it.records

                val listAdapter = StatsFoursAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }

    //endregion

    //region ODI Highest Avg
    private fun setOdiDataHighestAvg(){
        statsViewModel.mostODIHighestAverage.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                val sortedList = it.records

                val listAdapter = StatsAverageAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }

    //endregion

    //region ODI Most Ninties
    private fun setOdiDataMostNinties(){
        statsViewModel.mostODIMostNinties.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                val sortedList = it.records

                val listAdapter = StatsNintiesAdapter()
                binding?.statsRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }

    //endregion

}
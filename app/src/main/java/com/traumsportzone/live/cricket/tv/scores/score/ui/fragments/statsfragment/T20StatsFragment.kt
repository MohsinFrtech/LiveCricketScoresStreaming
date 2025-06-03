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
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestAvgstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestScorestattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.highestSrstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostFiftiesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostFoursstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostHundredsstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostNinetiesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostRunsstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostSixesstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.mostWicketstattypeid
import com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants.statTypeId


class T20StatsFragment: Fragment() {

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
        if(statTypeId == mostWicketstattypeid) {
            sett20DataMostWickets()
        }else if (statTypeId == mostRunsstattypeid) {
            binding?.labelWickets?.text = "R"
            binding?.labelOvers?.text = "I"
            sett20DataMostRuns()
        }else if (statTypeId == highestScorestattypeid) {
            binding?.labelAvg?.text = "VS"
            binding?.labelOvers?.text = "Balls"
            binding?.labelMatches?.text = "SR"
            binding?.labelWickets?.text = "HS"
            sett20DataHighestScores()
        }else if (statTypeId == highestSrstattypeid){
            binding?.labelAvg?.text = "I"
            binding?.labelOvers?.text = "M"
            binding?.labelMatches?.text = "R"
            binding?.labelWickets?.text = "SR"
            sett20DataHighestSr()
        }else if (statTypeId == mostHundredsstattypeid) {
            binding?.labelAvg?.text = "100s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            sett20DataMostHundered()
        }else if (statTypeId == mostFiftiesstattypeid) {
            binding?.labelAvg?.text = "50s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            sett20DataMostFifties()
        }else if (statTypeId == mostSixesstattypeid) {
            binding?.labelAvg?.text = "6s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            sett20DataMostSixes()
        }else if (statTypeId == mostFoursstattypeid) {
            binding?.labelAvg?.text = "4s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            sett20DataMostFours()
        }else if (statTypeId == highestAvgstattypeid) {
            binding?.labelAvg?.text = "Avg"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            sett20DataHighestAvg()
        }else if (statTypeId == mostNinetiesstattypeid) {
            binding?.labelAvg?.text = "90s"
            binding?.labelOvers?.text = "I"
            binding?.labelMatches?.text = "M"
            binding?.labelWickets?.text = "R"
            sett20DataMostNinties()
        }

        return view
    }

    //region Most Wickets
    private fun sett20DataMostWickets(){
        statsViewModel.mostt20Wicket.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()) {
                val sortedList = it.records

                val listAdapter = StatsAdapter()
                binding?.statsRecycler?.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding?.statsRecycler?.adapter = listAdapter
                listAdapter.submitList(sortedList)
            }else{
                binding?.noResult?.text = "No Records are available"
                binding?.noResult?.visibility(true)
            }
        })
    }


    //region Most Runs
    private fun sett20DataMostRuns(){
        statsViewModel.mostT20Runs.observe(viewLifecycleOwner, Observer {
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

    //region Highest Score
    private fun sett20DataHighestScores(){
        statsViewModel.mostT20HighestScore.observe(viewLifecycleOwner, Observer {
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

    //region Highest Sr
    private fun sett20DataHighestSr(){
        statsViewModel.mostT20HighestSr.observe(viewLifecycleOwner, Observer {
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

    //region Most Hundered
    private fun sett20DataMostHundered(){
        statsViewModel.mostT20MostHundered.observe(viewLifecycleOwner, Observer {
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

    //region Most Fifties
    private fun sett20DataMostFifties(){
        statsViewModel.mostT20MostFifties.observe(viewLifecycleOwner, Observer {
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

    //region Most Sixes
    private fun sett20DataMostSixes(){
        statsViewModel.mostT20MostSix.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()){

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

    //region Most Fours
    private fun sett20DataMostFours(){
        statsViewModel.mostT20MostFour.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()){

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

    //region Highest Avg
    private fun sett20DataHighestAvg(){
        statsViewModel.mostT20HighestAverage.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()){

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

    //region Most Ninties
    private fun sett20DataMostNinties(){
        statsViewModel.mostT20MostNinties.observe(viewLifecycleOwner, Observer {
            if (!it.records.isNullOrEmpty()){

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
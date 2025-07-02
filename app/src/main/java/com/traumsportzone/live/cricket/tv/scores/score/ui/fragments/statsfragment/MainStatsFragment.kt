package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.statsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.MainStatsLayBinding
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

class MainStatsFragment :Fragment()
{

    var mainStatsFragment:MainStatsLayBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layStats = inflater.inflate(R.layout.main_stats_lay,container,false)
        mainStatsFragment = DataBindingUtil.bind(layStats)
        mainStatsFragment?.backarrow?.setOnClickListener {
            findNavController()?.popBackStack()
        }

        //Most wickets click listener......
        mainStatsFragment?.seriesIcon?.setOnClickListener {
            statTypeId = mostWicketstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.mostwicketText?.setOnClickListener {
            statTypeId = mostWicketstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        //Most runs click listener.....
        mainStatsFragment?.mostrunIcon?.setOnClickListener {
            statTypeId = mostRunsstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.mostRunText?.setOnClickListener {
            statTypeId = mostRunsstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        /// Highest score icon and text click listener...
        mainStatsFragment?.highestscoreIcon?.setOnClickListener {
            statTypeId = highestScorestattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.highestscoreText?.setOnClickListener {
            statTypeId = highestScorestattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        //Highest strike icon and text click listener....
        mainStatsFragment?.higheststrikeIcon?.setOnClickListener {
            statTypeId = highestSrstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.higheststrikeText?.setOnClickListener {
            statTypeId = highestSrstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        //Most hundered click listener.....
        mainStatsFragment?.mosthunderedIcon?.setOnClickListener {
            statTypeId = mostHundredsstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.mosthunderedText?.setOnClickListener {
            statTypeId = mostHundredsstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }
9
        ///Most fifties icon and text click listener....
        mainStatsFragment?.mostfiftiesIcon?.setOnClickListener {
            statTypeId = mostFiftiesstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.mostfiftiesText?.setOnClickListener {
            statTypeId = mostFiftiesstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        ////Most sixes icon and text click listener....
        mainStatsFragment?.mostsixIcon?.setOnClickListener {
            statTypeId = mostSixesstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.mostsixText?.setOnClickListener {
            statTypeId = mostSixesstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }


        ///Most fours icon and click listener....
        mainStatsFragment?.mostfourIcon?.setOnClickListener {
            statTypeId = mostFoursstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.mostfourText?.setOnClickListener {
            statTypeId = mostFoursstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        //Highest average icon and text click listener....

        mainStatsFragment?.highestaverageIcon?.setOnClickListener {
            statTypeId = highestAvgstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        mainStatsFragment?.highestaverageText?.setOnClickListener {
            statTypeId = highestAvgstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        ///Most ninetees icon and text click listener...
        mainStatsFragment?.mostnintiesIcon?.setOnClickListener {
            statTypeId = mostNinetiesstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }
        mainStatsFragment?.mostnintiesText?.setOnClickListener {
            statTypeId = mostNinetiesstattypeid
            val direction = MainStatsFragmentDirections.actionMainStatsFragmentToStatFragment()
            findNavController().navigate(direction)
        }

        return layStats

    }


}
package com.traumsportzone.live.cricket.tv.scores.score.ui.fragments.matchdetail

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
import com.traumsportzone.live.cricket.tv.scores.databinding.NewsDetailBinding
import com.traumsportzone.live.cricket.tv.scores.score.adapter.NewsDetailAdapter
import com.traumsportzone.live.cricket.tv.scores.score.adapter.OverAdapter
import com.traumsportzone.live.cricket.tv.scores.score.model.Content
import com.traumsportzone.live.cricket.tv.scores.score.model.ContentInside
import com.traumsportzone.live.cricket.tv.scores.score.model.NewsdetailModel
import com.traumsportzone.live.cricket.tv.scores.score.utility.Cons
import com.traumsportzone.live.cricket.tv.scores.score.viewmodel.CommentaryViewModel

class NewsDetailFragment:Fragment() {

    private var bindingDetail:NewsDetailBinding?=null
    private val newsViewModel by lazy {
        ViewModelProvider(requireParentFragment())[CommentaryViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layNews = inflater.inflate(R.layout.news_detail,container,false)
        bindingDetail = DataBindingUtil.bind(layNews)
        setUpNewsData()
        return layNews
    }

    private fun setUpNewsData() {
        newsViewModel.loadNewsDetail()
        newsViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                bindingDetail?.MainLottie?.visibility = View.VISIBLE
            } else {
                bindingDetail?.MainLottie?.visibility = View.GONE
            }
        })

        newsViewModel.newsDetail?.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.headline!=null){
                    setUpData(it)
                }

                if (!it.content.isNullOrEmpty()){
                    setUpNewsDetailAdapter(it.content)
                }
            }
        })
    }

    private fun setUpNewsDetailAdapter(content: ArrayList<ContentInside>) {
        val listAdapter =
            NewsDetailAdapter()
        bindingDetail?.recyclerViewNewsText?.layoutManager = LinearLayoutManager(
            requireContext(),
        )
        bindingDetail?.recyclerViewNewsText?.adapter = listAdapter
        listAdapter.submitList(content)
    }

    private fun setUpData(it: NewsdetailModel) {
        bindingDetail?.newsH?.text = it.headline.toString()
        bindingDetail?.newsIntro?.text = it.intro.toString()
        if (it.publishTime!=null){
            val convertDate = it.publishTime?.toLong()
                ?.let { Cons.convertDateAndTime(it) }
            bindingDetail?.newsDate?.text = Cons.dateAndTime(convertDate)
        }
    }
}
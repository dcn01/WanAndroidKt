package com.black.multi.videosample.fragment

import android.os.Bundle
import android.view.ViewGroup
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.base.ui.BaseAgentWebFragment
import com.black.multi.videosample.databinding.FragmentHomeDetailBinding
import com.black.multi.videosample.utils.HOME_DETAIL_PAGE
import com.black.multi.videosample.utils.HomeDetailFragment_Title
import com.black.multi.videosample.utils.HomeDetailFragment_Url


/**
 * Created by wei.
 * Date: 2020/5/26 15:41
 * Desc:
 */


@FragmentDestination(pageUrl = HOME_DETAIL_PAGE, asStartPage = false)
class HomeDetailFragment : BaseAgentWebFragment<FragmentHomeDetailBinding>() {
    private var url: String? = null
    private var title: String? = null

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mToolBar = binding.includeToolbar.toolbar
        getBundle()
        initTitle()
    }

    private fun initTitle(){
        binding.includeToolbar.titleTv.text = title
    }

    private fun getBundle() {
        url = arguments?.get(HomeDetailFragment_Url) as String
        title = arguments?.get(HomeDetailFragment_Title) as String
    }

    override fun getAgentWebParent(): ViewGroup {
        return binding.linearLayout
    }

    override fun getUrl(): String? {
        return url
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_detail
    }

}
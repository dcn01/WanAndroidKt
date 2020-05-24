package com.black.multi.videosample.fragment

import android.os.Bundle
import com.black.multi.libnavannotation.FragmentDestination
import com.black.multi.videosample.R
import com.black.multi.videosample.databinding.FragmentKnowledgeBinding
import com.black.multi.videosample.ui.base.ui.BaseFragment

/**
 * Created by wei.
 * Date: 2020/5/18 下午10:49
 * Description:
 */
@FragmentDestination(pageUrl = "main/tab/knowledge",asStartPage = false)
class KnowledgeFragment : BaseFragment<FragmentKnowledgeBinding>() {

    override fun beforeInitView(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    override fun afterInitView(savedInstanceState: Bundle?) {
    }


}
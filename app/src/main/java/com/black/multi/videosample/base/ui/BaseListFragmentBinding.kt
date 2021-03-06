package com.black.multi.videosample.base.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.black.multi.videosample.api.net.Resource
import com.black.multi.videosample.api.net.Status
import com.black.multi.videosample.viewmodel.BaseListViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * Created by wei.
 * Date: 2020/6/5 下午10:26
 * Description:
 */
abstract class BaseListFragmentBinding<B : ViewDataBinding, T> : BaseFragment<B>(), OnRefreshListener,
        OnLoadMoreListener {

    protected var page = 0
    private var isRefresh = true
    protected var mSmartRefreshLayout: SmartRefreshLayout?=null
    protected lateinit var mViewModel: BaseListViewModel<T>

    protected open fun initViews(
            savedInstanceState: Bundle?
    ) {
        mViewModel = createViewModel()
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout!!.setOnRefreshListener(this)
            mSmartRefreshLayout!!.setOnLoadMoreListener(this)
            mSmartRefreshLayout!!.setEnableLoadMore(true)
            showLoading()
            mSmartRefreshLayout!!.autoRefresh()
        }
    }

    protected abstract fun createViewModel(): BaseListViewModel<T>

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        isRefresh = true
        mViewModel.page = 0
        fetchData()
    }

    private fun fetchData() {
        mViewModel.getModels().observe(this,
                Observer {
                    when (it.status) {
                        Status.LOADING -> if (isRefresh) {
                            fetchDataLoading()
                        }
                        Status.SUCCESS -> {
                            finishSmartRefreshLayout()
                            showContent()
                            stopLoadData()
                            fetchDataSuccess(it, isRefresh, mSmartRefreshLayout, page)
                        }
                        Status.ERROR -> {
                            finishSmartRefreshLayout()
                            onRefreshFailure(it.msg)
                            fetchDataError(it.msg)
                        }
                    }
                })
    }

    private fun finishSmartRefreshLayout(){
        if (page == 0) {
            mSmartRefreshLayout!!.finishRefresh()
        } else {
            mSmartRefreshLayout!!.finishLoadMore()
        }
    }

    protected open fun fetchDataLoading() {}

    protected open fun fetchDataError(error: String?) {}

    private fun stopLoadData() {
        if (isRefresh) {
            mSmartRefreshLayout!!.finishRefresh()
        } else {
            mSmartRefreshLayout!!.finishLoadMore()
        }
    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        isRefresh = false
        mViewModel.page = page
        fetchData()
    }

    protected abstract fun fetchDataSuccess(
            resource: Resource<T>,
            isRefresh: Boolean,
            smartRefreshLayout: SmartRefreshLayout?,
            page: Int
    )
}
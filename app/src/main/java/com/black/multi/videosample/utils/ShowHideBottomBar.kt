package com.black.multi.videosample.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.black.multi.videosample.model.Destination
import com.black.multi.videosample.widget.MainBottomBar
import com.black.xcommon.utils.EzLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by wei.
 * Date: 2020/5/27 13:34
 * Desc:
 */
class ShowHideBottomBar {

    companion object {
        val instance: ShowHideBottomBar by lazy { ShowHideBottomBar() }
    }

    @SuppressLint("CheckResult")
    open fun showHideBottomBar(navController: NavController, toolbar: Toolbar, mainBottomBar: MainBottomBar) {
        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
                val id = destination.id
                val navigatorName = destination.navigatorName
                val parent = destination.parent
                EzLog.d("id-->${id}--navigatorName--->${navigatorName}---parent-->${parent?.id}")
                val destConfig = AppConfig.getDestConfig()
                Observable.just(destConfig)
                        .subscribeOn(Schedulers.io())
                        .flatMap(object : Function<HashMap<String, Destination>?, Observable<Boolean>> {
                            override fun apply(t: HashMap<String, Destination>): Observable<Boolean> {
                                val iterator = t!!.iterator()
                                while (iterator.hasNext()) {
                                    val next = iterator.next()
                                    val value = next.value

                                    //先找对应的 id，找到后，再判断该 id 是不是首页的几个 tab 中的一个
                                    //如果不是，则隐藏 toolbar 和 bottomBar
                                    if (id == value.id) {
                                        if (value.pageUrl.equals(HOME_PAGE, ignoreCase = true) || value.pageUrl.equals(KNOWLEDGE_PAGE, ignoreCase = true) || value.pageUrl.equals(PROJECT_PAGE, ignoreCase = true) || value.pageUrl.equals(NAV_PAGE, ignoreCase = true) || value.pageUrl.equals(PERSON_PAGE, ignoreCase = true)) {
                                            return Observable.just(false)

                                        }
                                        return Observable.just(true)
                                    }
                                }

                                return Observable.just(false)
                            }

                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Consumer<Boolean> { t ->
                            if (t) {
                                toolbar.visibility = View.GONE
                                mainBottomBar.visibility = View.GONE
                            }else{
                                toolbar.visibility = View.VISIBLE
                                mainBottomBar.visibility = View.VISIBLE
                            }
                        }, Consumer<Throwable> { t -> EzLog.d("ShowHideBottomBar--->${t?.message}") })
            }

        })
    }
}
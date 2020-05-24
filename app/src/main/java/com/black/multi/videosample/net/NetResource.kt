package com.black.multi.videosample.net

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.black.multi.videosample.api.FetchData
import com.black.multi.videosample.api.IService
import com.black.multi.videosample.api.ServiceResponse

import kotlinx.coroutines.Dispatchers
import retrofit2.Response

/**
 * Created by wei.
 * Date: 2020/5/23 下午12:38
 * Description:
 */
abstract class NetResource<T> {

    open fun fetchData(): LiveData<Resource<T>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val api = FetchData.getService()
            try {
                //json 与 javabean 的格式不对应，到这里是会报解析错误的，如果不捕获，app 直接崩溃
                val response = requestNetResource(api)
                if (response!!.isSuccessful) {
                    emit(Resource.success(response.body()?.data))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val code = response.code()
                    val message = response.message()
                    emit(Resource.error(ExceptionHandle.handleException(code, message)))
                }
            } catch (e: Exception) {
                emit(Resource.error(ExceptionHandle.handleException(e)))
            }
        }
    }

    abstract suspend fun requestNetResource(api: IService): Response<ServiceResponse<T>>?

}
package com.black.multi.videosample.model

/**
 * Created by wei.
 * Date: 2020/5/28 16:11
 * Desc:
 */
data class ProjectTitleModel(val courseId: Int = 0){
    var children: List<Any>?=null
//    val courseId: Int = 0
    val id: Int = 0
    var name: String?=null
    get() = field?.replace("&amp;","")
    val order: Int=0
    val parentChapterId: Int=0
    var userControlSetTop: Boolean = false
    var visible: Int = 0
}
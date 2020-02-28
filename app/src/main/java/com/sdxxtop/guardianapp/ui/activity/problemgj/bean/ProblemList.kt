package com.sdxxtop.guardianapp.ui.activity.problemgj.bean

/**
 * Date:2020/2/28
 * author:lwb
 * Desc:
 */

data class ListData(
        val event: List<Event>,
        val num: Int
)

data class Event(
        val add_time: String,
        val category_id: Int,
        val category_level_id: Int,
        val category_name: String,
        val check_id: Int,
        val classify_id: Int,
        val end_date: String,
        val event_id: Int,
        val important_type: Int,
        val is_claim: Int,
        val place: String,
        val send_id: Int,
        val settle_id: Int,
        val status: Int,
        val title: String,
        val userid: Int
)
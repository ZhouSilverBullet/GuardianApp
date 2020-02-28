package com.sdxxtop.guardianapp.ui.activity.problemgj.bean

import com.contrarywind.interfaces.IPickerViewData

/**
 * Date:2020/2/28
 * author:lwb
 * Desc:
 */
data class ProblemGJIndexBean(
        val category: List<Category>,
        val part: Part,
        val part_info: List<PartInfo>,
        val reportPath: ReportPath,
        val user: User
)

data class Category(
        val category_id: Int,
        val category_name: String,
        val children: List<Category>,
        val level: Int,
        val pid: Int
) : IPickerViewData {
    override fun getPickerViewText(): kotlin.String {
        return category_name
    }
}

class Part(
        val part_id: Int,
        val part_name: String
)

data class PartInfo(
        val parent_id: Int,
        val part_id: Int,
        val part_name: String,
        val sort_id: Int
)

data class ReportPath(
        val basicReview: Int,
        val eventClassification: Int,
        val img: Int,
        val isNeedLetter: Int,
        val ismeasures: Int,
        val isprescription: Int,
        val isproblem: Int,
        val isresponsibility: Int,
        val reportDescribe: Int,
        val reportFind: Int,
        val reportImg: Int,
        val reportPart: Int,
        val supplement: Int,
        val supplementNumber: Int,
        val title: Int,
        val userPart: Int,
        val userPhone: Int,
        val username: Int
)

data class User(
        val mobile: String,
        val name: String,
        val parent_id: Int,
        val part_id: Int,
        val part_name: String,
        val userid: Int
)

data class AddEventResult(val event_id: String)
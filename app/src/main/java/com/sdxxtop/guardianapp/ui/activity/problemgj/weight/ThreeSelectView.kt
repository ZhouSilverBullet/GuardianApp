package com.sdxxtop.guardianapp.ui.activity.problemgj.weight

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.CustomListener
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.ui.activity.problemgj.bean.Category
import java.lang.Exception

/**
 * Date:2020/2/28
 * author:lwb
 * Desc:
 */
class ThreeSelectView(private val mContext: Context, private val list: List<Category>) {

    private val TAG = this.javaClass.name
    private var mListener: OnPickerSelect? = null
    private var options1Items: ArrayList<Category> = ArrayList()
    private val options2Items = ArrayList<ArrayList<Category>>()
    private val options3Items = ArrayList<ArrayList<ArrayList<Category>>>()

    /**
     * 处理数据
     */
    init {
        for (item in list) {   // 遍历省份
            options1Items.add(item)
            var secondList = ArrayList<Category>() //该省的城市列表（第二级）
            var thirdList = ArrayList<ArrayList<Category>>() //该省的所有地区列表（第三极）
            if (item.children.isNotEmpty()) {
                item.children.forEach {
                    // 遍历城市
                    secondList.add(it) //  添加到城市列表
                    var thirdList1 = ArrayList<Category>()   //该城市的所有地区列表

                    if (it.children.isNotEmpty()) {
                        it.children.forEach { it ->
                            thirdList1.add(it)
                        }
                    } else {
                        thirdList1.add(Category(-1, "", listOf(), 0, 0))
                    }
                    thirdList.add(thirdList1)
                }
            } else {
                secondList.add(Category(-1, "", listOf(), 0, 0))
                var thirdList1 = ArrayList<Category>()   //该城市的所有地区列表
                thirdList1.add(Category(-1, "", listOf(), 0, 0))
                thirdList.add(thirdList1)
            }
            options2Items.add(secondList)
            options3Items.add(thirdList)
        }
        showPickerView()
    }

    fun show() {
        pvOptions?.show()
    }

    private var pvOptions: OptionsPickerView<Category>? = null
    private fun showPickerView() { // 弹出选择器
        pvOptions = OptionsPickerBuilder(mContext, OnOptionsSelectListener { options1, options2, options3, v ->
            //返回的分别是三个级别的选中位置
            Log.e(TAG, "item1 -- $options1 -- item2 -- $options2 -- item3 -- $options3")
            var categoryId = 0
            var categoryName = ""
            try {
                if (options1Items[options1].category_id != -1) {
                    categoryId = options1Items[options1].category_id
                    categoryName = options1Items[options1].category_name
                }
                if (options2Items[options1][options2].category_id != -1) {
                    categoryId = options2Items[options1][options2].category_id
                    categoryName = options2Items[options1][options2].category_name
                }
                if (options3Items[options1][options2][options3].category_id != -1) {
                    categoryId = options3Items[options1][options2][options3].category_id
                    categoryName = options3Items[options1][options2][options3].category_name
                }
            } catch (e: Exception) {

            }
            mListener?.pickerSelect(categoryId, categoryName)
        })
                .setLayoutRes(R.layout.pickerview_custom_options_select) { v ->
                    val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                    val tvCancel = v.findViewById<View>(R.id.tv_cancel) as TextView
                    tvSubmit.setOnClickListener {
                        pvOptions?.returnData()
                        pvOptions?.dismiss()
                    }
                    tvCancel.setOnClickListener { pvOptions?.dismiss() }
                }
                .setTitleText(" ")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build()
        pvOptions?.setPicker(options1Items, options2Items as List<MutableList<Category>>?, options3Items as List<MutableList<MutableList<Category>>>?) //三级选择器
    }

    interface OnPickerSelect {
        fun pickerSelect(categoryId: Int, categoryName: String)
    }

    fun setOnPickerSelect(listener: OnPickerSelect) {
        this.mListener = listener
    }
}

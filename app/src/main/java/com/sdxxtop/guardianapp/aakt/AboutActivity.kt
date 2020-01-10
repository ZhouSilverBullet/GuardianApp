package com.sdxxtop.guardianapp.aakt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.style.FontStyle
import com.bin.david.form.data.table.TableData
import com.bin.david.form.utils.DensityUtils
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.databinding.ActivityAboutBinding
import java.util.*

class AboutActivity : AppCompatActivity() {

    private lateinit var viewModel :AboutModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about)
        viewModel = ViewModelProviders.of(this)[AboutModel::class.java]
        binding.vm = viewModel
        viewModel.str.observe(this, androidx.lifecycle.Observer {
            println(it)
//            tvTest.text = it
        })

//        initView()
    }

    private fun initView() {
        FontStyle.setDefaultTextSize(DensityUtils.sp2px(this, 15f)) //设置全局字体大小
        var columns = arrayListOf<Column<*>>()
        val column1 = Column<Column<String>>("日期", "date")
        column1.isFixed = true
        columns.add(column1)
        columns.add(Column<Column<String>>("上报时间", "update"))
        columns.add(Column<Column<String>>("标题", "title"))
        columns.add(Column<Column<String>>("状态", "status"))
        columns.add(Column<Column<String>>("分值", "score"))
        columns.add(Column<Column<String>>("分类", "type"))

        val testData = ArrayList<UserInfo>()
        for (i in 0..100) {
            testData.add(UserInfo("2020-01-07", "2020-01-07 14:50:00", "垃圾分类如何处理是个问题,难到了许多人", "已完成", "+2", "垃圾类"))
        }

        var table = findViewById<SmartTable<UserInfo>>(R.id.table)
        //表格数据 datas是需要填充的数据
        val tableData = TableData<UserInfo>("", testData, columns)
        //设置数据
        table.tableData = tableData

        tableData.isShowCount = false
        table.config
                .setShowXSequence(false)
                .setShowYSequence(false)
    }

}

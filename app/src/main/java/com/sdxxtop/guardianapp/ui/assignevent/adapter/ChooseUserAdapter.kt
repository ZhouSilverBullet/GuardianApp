package com.sdxxtop.guardianapp.ui.assignevent.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.model.bean.PartAndUserBean
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Date:2020-02-13
 * author:lwb
 * Desc:
 */
class ChooseUserAdapter : BaseQuickAdapter<PartAndUserBean.AssignUserBean, BaseViewHolder>(R.layout.item_choose_view) {

    private var normalIcon: Drawable? = null
    private var selectIcon: Drawable? = null
    private var txNum: TextView? = null

    override fun convert(helper: BaseViewHolder?, item: PartAndUserBean.AssignUserBean?) {
        if (item == null || helper == null) return
        val ivSelect = helper.getView<ImageView>(R.id.ivSelect)
        if (item.isSelect) {
            ivSelect?.setImageDrawable(selectIcon
                    ?: mContext.resources.getDrawable(R.drawable.choose_pm_icon_select))
        } else {
            ivSelect?.setImageDrawable(normalIcon
                    ?: mContext.resources.getDrawable(R.drawable.choose_pm_icon_normal))
        }

        helper.setText(R.id.tvName, item.name)
        helper.setText(R.id.tvPart, item.part_name)
        val view = helper.getView<CircleImageView>(R.id.civ_header)
        Glide.with(mContext).load(item.img).into(view)

        helper.itemView.setOnClickListener {
            var item1 = getItem(helper.layoutPosition)
            item1?.isSelect = !item1?.isSelect!!
            txNum?.text = "已选（${getSelectItem().size}）"
            notifyDataSetChanged()
        }

    }

    override fun replaceData(data: MutableCollection<out PartAndUserBean.AssignUserBean>) {
        data.forEach { it.isSelect = false }
        super.replaceData(data)
    }

    fun getSelectItem(): ArrayList<PartAndUserBean.AssignUserBean> {
        var result = arrayListOf<PartAndUserBean.AssignUserBean>()
        data.forEach { if (it.isSelect) result.add(it) }
        return result
    }

    fun setDrawable(_normalIcon: Drawable, _selectIcon: Drawable?) {
        normalIcon = _normalIcon
        selectIcon = _selectIcon
    }

    fun setText(tvSelectNum: TextView?) {
        txNum = tvSelectNum
    }
}
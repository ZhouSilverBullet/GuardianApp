package com.sdxxtop.guardianapp.ui.assignevent.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sdxxtop.guardianapp.R
import com.sdxxtop.guardianapp.model.bean.MediaBean

/**
 * Date:2020-02-11
 * author:lwb
 * Desc:
 */
class AssignFileAdapter : BaseMultiItemQuickAdapter<MediaBean, BaseViewHolder> {
    constructor(data: MutableList<MediaBean>?) : super(data) {
        addItemType(1, R.layout.item_assign_detail_desc)
    }

    init {
        val list = arrayListOf<MediaBean>()
        list.add(MediaBean("http://a3.att.hudong.com/13/41/01300000201800122190411861466.jpg", 1))
        list.add(MediaBean("http://i2.chinanews.com/simg/cmshd/2020/02/10/c79bdde9ee3d439f96585d9a9728555c.jpg", 1))
        list.add(MediaBean("http://b2b.image.yuanlin.com/Biz/2012-4/201242221631294.jpg", 1))
        list.add(MediaBean("http://a0.att.hudong.com/81/87/01300309316150134708870886770.jpg", 1))
        list.add(MediaBean("http://bbsfiles.vivo.com.cn/vivobbs/attachment/forum/202001/07/112244s14v2ze1hhjes214.jpg", 1))
        list.add(MediaBean("http://www.jete.cn/uploads/allimg/130625/42-130625111206.jpg", 1))
        list.add(MediaBean("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1305/02/c0/20460023_1367464507058.jpg", 1))
        list.add(MediaBean("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1305/02/c0/20460023_1367464507058.jpg", 1))

    }

    override fun convert(helper: BaseViewHolder?, item: MediaBean?) {

    }
}

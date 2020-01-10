package com.sdxxtop.network

import android.content.Context
import java.lang.ref.WeakReference

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-07-30 11:46
 * Version: 1.0
 * Description:
 */
object NetworkSession {
    lateinit var contextDef: WeakReference<Context>
    var version: Int = 0


    fun initNetwork(context: Context, version: Int) {
        this@NetworkSession.contextDef = WeakReference(context)
        this@NetworkSession.version = version
    }

    @JvmStatic
    fun getContext(): Context {
        if (contextDef.get() == null) {
            throw IllegalAccessException("NetworkSession需要被初始化，用于检测网络,网络请求时所需的app版本号等...")
        }
        return contextDef.get()!!
    }
}
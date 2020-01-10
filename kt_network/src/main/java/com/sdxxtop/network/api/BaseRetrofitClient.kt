package com.sdxxtop.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Email: sdxxtop@163.com
 * Created by sdxxtop 2019-07-24 16:57
 * Version: 1.0
 * Description:
 */
abstract class BaseRetrofitClient {
    companion object {
        //默认为8
        private const val DEFAULT_TIME_OUT = 8
    }

    /**
     *  fun isDebug() :Boolean {
    return BuildConfig.DEBUG
    }
     */
    abstract fun isDebug(): Boolean;

    /**
     * 使用 s 为单位
     */
    open fun connectTimeOut(): Int {
        return DEFAULT_TIME_OUT
    }


    val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            if (isDebug()) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            builder.addInterceptor(logging)
                    .connectTimeout(connectTimeOut().toLong(), TimeUnit.SECONDS)


//            builder.retryOnConnectionFailure(true)
            handleBuilder(builder)

            return builder.build()
        }

    abstract fun handleBuilder(builder: OkHttpClient.Builder)


//    /**
//     * 通过 KotlinExtensions 文件中的  Retrofit.create() 仿写
//     * 返回值是什么类型，create中就会传递什么类型
//     *
//     */
//    inline fun <reified S> getService(baseUrl: String): S {
//        return Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(baseUrl)
//                .client(client)
//                .build()
//                .create(S::class.java)
//    }

    fun <S> getService(clazz: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build()
                .create(clazz)
    }

}
package com.acaroom.a08_15_practice

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication: Application() {

    lateinit var service: RetrofitService

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        createRetrofit()
        //chrome://inspect/#devices
    }

    fun createRetrofit() {
        val header = Interceptor {  // 핸드폰으로부터 나가는 통신을 original 변수에 잡아놓음
            val original = it.request()

            if (checkIstLogin()) {
                // null이 아닌 경우 let 블럭을 실행
                getUserToken()?.let { token ->
                    val request = original.newBuilder()
                        .header("Authorization", "token $token")    // 헤더 달아줌
                        .build()
                    it.proceed(request)
                }
            } else {
                it.proceed(original)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor()) // 통신을 낚아챔, StethoInterceptor가 낚아채서 화면에 보여줌
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    fun checkIstLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        return token != "null"
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        return if (token == "null") null
        else token
    }
}
package com.acaroom.a08_15_practice

import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
//    @GET("json/students/")
//    fun getStudentsList(): Call<ArrayList<PersonFromServer>>
//
//    @POST("json/students/")
//    fun createStudent(
//        @Body params : HashMap<String, Any>
//    ): Call<PersonFromServer>
//
//    @POST("json/students/")
//    fun createStudentEasy(
//        @Body person : PersonFromServer
//    ): Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded // Field를 하나하나 보낼 때 적어줘야 함
    fun register (
        // @Body register: Register // Body에 Register라는 객체를 넣어 api 요청
        @Field("username")username : String,
        @Field("password1")password1 : String,
        @Field("password2")password2 : String
    ): Call<User>   // 응답으로 User 객체가 반환

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("/instagram/post/list/all/")
    fun getAllPosts(): Call<ArrayList<Post>>
}
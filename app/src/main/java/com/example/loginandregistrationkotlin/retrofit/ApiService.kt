package com.example.loginandregistrationkotlin.retrofit

import com.example.loginandregistrationkotlin.model.BaseRes
import com.example.loginandregistrationkotlin.model.LoginRes
import com.example.loginandregistrationkotlin.model.SocialLoginRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.HashMap

interface ApiService {

    //TODO LOGIN
    @POST("userController/")
    fun login(@Body map: HashMap<String, String>): Call<BaseRes<LoginRes>>


    //TODO SIGN UP
    @POST("userController/")
    fun sociallogin(@Body map: HashMap<String, String>): Call<BaseRes<SocialLoginRes>>
}
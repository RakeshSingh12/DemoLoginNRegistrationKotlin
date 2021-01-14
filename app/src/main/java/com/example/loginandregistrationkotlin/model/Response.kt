package com.example.loginandregistrationkotlin.model

import com.google.gson.annotations.SerializedName


data class BaseRes<T>(
    @SerializedName("statusMessage") var statusMessage: String = "",
    @SerializedName("statusCode") var statusCode: Int,
    @SerializedName("error_string") var error_string: String = "",
    @SerializedName("result") var result: T

)

data class LoginRes(
    @SerializedName("user_id") var user_id: String="",
    @SerializedName("name") var name: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("mobile") var mobile: String = "",
    @SerializedName("profile") var profile: String = ""
)

data class SocialLoginRes(
    @SerializedName("user_id") var user_id: String="",
    @SerializedName("name") var name: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("mobile") var mobile: String = "",
    @SerializedName("profile") var profile: String = ""
)
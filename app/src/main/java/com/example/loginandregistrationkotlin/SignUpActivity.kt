package com.example.loginandregistrationkotlin

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import com.example.loginandregistrationkotlin.model.BaseRes
import com.example.loginandregistrationkotlin.model.SocialLoginRes
import com.example.loginandregistrationkotlin.retrofit.ApiService
import com.example.loginandregistrationkotlin.retrofit.RetrofitUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }


    //TODO Check Network
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    //TODO show progress dialog
    fun showProgressDialog(context: Context?) {
        if (progressDialog != null && progressDialog!!.isShowing()) {
        } else {
            progressDialog = ProgressDialog(context)
            val inflater = LayoutInflater.from(context)
            progressDialog!!.getWindow()!!.requestFeature(Window.FEATURE_NO_TITLE)
            progressDialog!!.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.show()
            progressDialog!!.setContentView(
                inflater.inflate(R.layout.layout_dialog,
                    null
                )
            )
        }
    }

    //TODO hide progress dialog
    fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing()) {
            progressDialog!!.dismiss()
        }
    }

    private fun socialLoginApi() {
        if (isNetworkAvailable(this@SignUpActivity)) {
            showProgressDialog(this@SignUpActivity)
            val map = HashMap<String, String>()
          /*  map.put(socialUser?.social_key!!, socialUser?.Id.toString())
            map.put("social_type", socialUser?.type!!)*/
            map.put("name", editName.text.toString())
            map.put("method", "signup")
            map.put("email", editEmail.text.toString())
            map.put("mobile", editPhoneNumber.text.toString())


            val apiService = RetrofitUtil.getRetrofitInstance().create(ApiService::class.java)
            val call = apiService.sociallogin(map)

            call.enqueue(object : Callback<BaseRes<SocialLoginRes>> {
                override fun onFailure(call: Call<BaseRes<SocialLoginRes>>, t: Throwable) {
                    Log.e("result", t.toString())
                    hideProgressDialog()
                }


                override fun onResponse(call: Call<BaseRes<SocialLoginRes>>, response: Response<BaseRes<SocialLoginRes>>) {
                   hideProgressDialog()

                    if (response.body() != null) {
                        //val result = response.body()
                        if (response.body()?.statusCode == 200) {
                            Log.d("login response", response.body()!!.result.email)

                        /*    prefs.save("user_id", response.body()!!.result.user_id)
                            prefs.save("name", response.body()!!.result.name)
                            prefs.save("email", response.body()!!.result.email)
                            prefs.save("mobile", response.body()!!.result.mobile)
                            prefs.save("isLogin", "true")
*/
                            val i = Intent(this@SignUpActivity, HomeActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(applicationContext, " " + response.body()?.statusMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        } else {
            Toast.makeText(applicationContext, " Please check internet connection" , Toast.LENGTH_SHORT).show()
        }
    }
}
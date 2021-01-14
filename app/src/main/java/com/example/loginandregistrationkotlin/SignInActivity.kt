package com.example.loginandregistrationkotlin
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginandregistrationkotlin.model.BaseRes
import com.example.loginandregistrationkotlin.model.LoginRes
import com.example.loginandregistrationkotlin.retrofit.ApiService
import com.example.loginandregistrationkotlin.retrofit.RetrofitUtil
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomLogin!!.setOnClickListener {
            doLogin(editUserEmail.text.toString(), editPassword.text.toString())
        }
        tvSignUp!!.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
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



    //TODO Login API
    private fun doLogin(email: String, password: String) {
        if(isNetworkAvailable(this@SignInActivity)) {
            showProgressDialog(this@SignInActivity)

            val map = HashMap<String, String>()
            map.put("email", email)
            map.put("password", password)
            map.put("method", "login")
            map.put("user_id", "2")

            //UserId = prefs.getValueString("user_id", "").toString()

            val apiService = RetrofitUtil.getRetrofitInstance().create(ApiService::class.java)
            val call = apiService.login(map)

            call.enqueue(object : Callback<BaseRes<LoginRes>> {
                override fun onFailure(call: Call<BaseRes<LoginRes>>, t: Throwable) {
                   hideProgressDialog()

                }


                override fun onResponse(call: Call<BaseRes<LoginRes>>, response: Response<BaseRes<LoginRes>>) {
                    hideProgressDialog()
                    if (response.body()?.statusCode == 200) {
                        val result = response.body()

                      /*  prefs.save("user_id", response.body()!!.result.user_id)
                        prefs.save("name", response.body()!!.result.name)
                        prefs.save("email", response.body()!!.result.email)
                        prefs.save("mobile", response.body()!!.result.mobile)
                        prefs.save("profile", response.body()!!.result.profile)
                        prefs.save("isLogin", "true")*/

                        val i = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(i)
                    } else if (response.body()?.statusCode == 404) {
                        Toast.makeText(applicationContext, " "  + response.body()?.statusMessage, Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(applicationContext, " " + response.body()?.statusMessage, Toast.LENGTH_SHORT).show()

                    }
                }
            })
        }else{
            Toast.makeText(applicationContext, " Please check internet connection" , Toast.LENGTH_SHORT).show()
        }

    }
}
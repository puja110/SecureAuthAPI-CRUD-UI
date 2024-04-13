package com.example.mdev1004_assignemnt3.view.auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.model.LoginRequest
import com.example.mdev1004_assignemnt3.model.LoginResponse
import com.example.mdev1004_assignemnt3.view.feature.BookActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response

/**
 * Filename: LoginActivity.kt.js
 * Student Name / Student ID:
 * Puja Shrestha, 200573293
 * Suyog Shrestha, 200565523
 * Date: April 5, 2024
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    private lateinit var etEmail : TextInputEditText
    private lateinit var etPassword : TextInputEditText

    private lateinit var apiClient: ApiClient
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        apiClient = ApiClient
        progressDialog = ProgressDialog(this)

        // user login and token generate with retrofit login method
        btnLogin.setOnClickListener {
            if (isValid()) {
                showProgress()
                val intent = Intent(this, BookActivity::class.java)

                apiClient.getApiService().login(
                    LoginRequest(
                        username = etEmail.text.toString(),
                        password = etPassword.text.toString()
                    )
                )
                .enqueue(object : retrofit2.Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        hideProgress()
                        Log.d("LoginOnFailure", t.toString())
                        Toast.makeText(this@LoginActivity, t.toString(), Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        hideProgress()
                        val loginResponse = response.body()

                        if (loginResponse?.message == "User login Successfull") {
                            Toast.makeText(this@LoginActivity, "User logged in Successful!", Toast.LENGTH_LONG).show()
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@LoginActivity, "Please enter the valid login credential", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }

        // navigate to the Register Screen
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // validation done to check if the required fields are empty
    private fun isValid(): Boolean {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Email field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this@LoginActivity, "Password field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun showProgress() {
        progressDialog?.setTitle("Please Wait")
        progressDialog?.setMessage("Loading ...")
        progressDialog?.show()
    }

    private fun hideProgress() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
        }
    }
}
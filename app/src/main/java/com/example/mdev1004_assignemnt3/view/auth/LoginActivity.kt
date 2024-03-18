package com.example.mdev1004_assignemnt3.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.model.LoginRequest
import com.example.mdev1004_assignemnt3.model.LoginResponse
import com.example.mdev1004_assignemnt3.view.feature.BookActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    private lateinit var etEmail : TextInputEditText
    private lateinit var etPassword : TextInputEditText

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        // navigate to the Main Screen
        btnLogin.setOnClickListener {
            if (isValid()) {
                apiClient = ApiClient
                sessionManager = SessionManager(this)
                val intent = Intent(this, BookActivity::class.java)

                apiClient.getApiService().login(
                    LoginRequest(
                        email = etEmail.text.toString(),
                        password = etPassword.text.toString()
                    )
                )
                    .enqueue(object : retrofit2.Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            // Error logging in
                            Log.d("LoginOnFailure", t.toString())
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            val loginResponse = response.body()

                            if (loginResponse?.message == "user logged in") {
                                sessionManager.saveAuthToken(loginResponse.accessToken)
                                startActivity(intent)
                            } else {
                                Log.d("User login: ", "Error logging in")
                            }
                        }
                    })
            }
        }

        // navigate to the Register Screen
        btnRegister.setOnClickListener {
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
        }
    }

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
}
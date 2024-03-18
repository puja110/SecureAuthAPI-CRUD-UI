package com.example.mdev1004_assignemnt3.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.model.LoginResponse
import com.example.mdev1004_assignemnt3.model.SignupRequest
import com.example.mdev1004_assignemnt3.model.SignupResponse
import com.example.mdev1004_assignemnt3.view.feature.BookActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    private lateinit var etEmail : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var etConfirmPassword : TextInputEditText

    // Const Elements
    private lateinit var etFirstName : String
    private lateinit var etLastName : String
    private lateinit var etAddress : String

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = "Test"
        etLastName = "User"
        etAddress = "Georgian Drive, Barrie, ON, CA"

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        val intent = Intent(this, LoginActivity::class.java)

        // navigate to the Main Screen
        btnLogin.setOnClickListener {
            if(isValid()) {

                apiClient = ApiClient
                sessionManager = SessionManager(this)
                val intent = Intent(this, BookActivity::class.java)

                apiClient.getApiService().signup(SignupRequest(etFirstName, etLastName, etAddress, etEmail, etPassword))
                    .enqueue(object : Callback<SignupResponse> {

                        override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(
                            call: Call<SignupResponse>,
                            response: Response<SignupResponse>
                        ) {
                            val signupResponse = response.body()

                            if (signupResponse?.message == "user created") {
                                startActivity(intent)
                            } else {
                                // Error logging in
                            }
                        }
                    })
            }
        }

        // navigate to the Register Screen
        btnRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    //    Validation done to check if the required fields are empty, email is in valid format
    private fun isValid(): Boolean {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Email field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Password field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (confirmPassword.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Confirm Password field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this@RegisterActivity, "Confirm Password and Password must be same!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
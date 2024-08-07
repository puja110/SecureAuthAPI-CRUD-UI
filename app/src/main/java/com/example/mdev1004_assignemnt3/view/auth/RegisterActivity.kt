package com.example.mdev1004_assignemnt3.view.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdev1004_assignemnt3.ApiClient
import com.example.mdev1004_assignemnt3.R
import com.example.mdev1004_assignemnt3.SessionManager
import com.example.mdev1004_assignemnt3.model.SignupRequest
import com.example.mdev1004_assignemnt3.model.SignupResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    private lateinit var etEmail : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var etConfirmPassword : TextInputEditText

    private lateinit var etFirstName : TextInputEditText
    private lateinit var etLastName : TextInputEditText
    private lateinit var etAddress : TextInputEditText

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private var progressDialog: ProgressDialog? = null

    // defining password pattern
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
"^" +
        "(?=.*[@#$%^&+=])" +  // at least 1 special character
        "(?=\\S+$)" +  // no white spaces
        ".{8,}" +  // at least 8 characters
        "$"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etAddress = findViewById(R.id.et_address)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)

        apiClient = ApiClient
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)

        // navigate to the Login screen upon successful registration
        btnRegister.setOnClickListener {
            if(isValid()) {
                showProgress()
                val intent = Intent(this, LoginActivity::class.java)

                // signup api function to register new user account
                apiClient.getApiService().signup(
                    SignupRequest(
                        firstName = etFirstName.text.toString(),
                        lastName = etLastName.text.toString(),
                        address = etAddress.text.toString(),
                        email = etEmail.text.toString(),
                        password = etPassword.text.toString()
                    )
                )
                .enqueue(object : Callback<SignupResponse> {

                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                        hideProgress()
                        Log.d("User registration error: ", t.toString())
                        Toast.makeText(this@RegisterActivity, t.toString(), Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<SignupResponse>,
                        response: Response<SignupResponse>
                    ) {
                        hideProgress()
                        val signupResponse = response.body()
                        if (signupResponse?.message == "user created") {
                            startActivity(intent)
                            Toast.makeText(this@RegisterActivity, "User Registration Successful!", Toast.LENGTH_LONG).show()
                        } else {
                            hideProgress()
                            Toast.makeText(this@RegisterActivity, "Please put the strong password", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }

        // navigate back to the Login Screen
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // validation done to check if the required fields are empty, email is in valid format
    private fun isValid(): Boolean {
        val firstname = etFirstName.text.toString()
        val lastname = etLastName.text.toString()
        val address = etAddress.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (firstname.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Firstname field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (lastname.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Lastname field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (address.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Address field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Email field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this@RegisterActivity, "Password field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }

        if(password.length < 8) {
            Toast.makeText(this@RegisterActivity, "Password should be at least 8 character long!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(this@RegisterActivity, "Password is too weak!", Toast.LENGTH_SHORT).show()
            return false;
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
package com.example.lms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lms.databinding.ActivityLoginPasswordBinding


class LoginPasswordActivity : AppCompatActivity(), LoginView{
    lateinit var binding: ActivityLoginPasswordBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent //전달할 데이터를 받을 Intent
        val email = intent.getStringExtra("email")
        binding.loginIdEt.setText(email)


        binding!!.backBtn.setOnClickListener{
            startActivity(Intent(this,LoginEmailActivity::class.java))
        }

        binding!!.okBtn.setOnClickListener{
            //startMainActivity()
            login()
        }

    }

    private fun login() {
        if (binding.loginPwdEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(getUser())
        Log.d("Login-ACT/ASYNC", "Hello, LMS")
    }

    private fun getUser(): User {
        val email = binding.loginIdEt.text.toString()
        val pwd = binding.loginPwdEt.text.toString()

        return User(email = email, pwd = pwd,name ="", univ ="",ssn="")
    }

    private fun startMainActivity() {
        startActivity(Intent(this,LoginSuccessActivity::class.java))
    }


    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth2" , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code : Int , result: Result) {
        when(code) {
            1000 -> {
                saveJwt2(result.jwt)
                startMainActivity()
                Log.d("Logintest", "로그인 성공")

            }
        }
    }

    override fun onLoginFailure() {

    }

}
package com.mtjin.nomoneytrip.views.email_login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.base.BaseActivity
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.databinding.ActivityEmailLoginBinding
import com.mtjin.nomoneytrip.utils.fcm
import com.mtjin.nomoneytrip.utils.uuid
import com.mtjin.nomoneytrip.views.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailLoginActivity : BaseActivity<ActivityEmailLoginBinding>(R.layout.activity_email_login) {
    private val viewModel: EmailLoginViewModel by viewModel()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initViewModelCallback()
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            isEmailEmpty.observe(this@EmailLoginActivity, Observer {
                binding.etEmail.error = "이메일을 입력해주세요"
            })
            isPwEmpty.observe(this@EmailLoginActivity, Observer {
                binding.etPw.error = "비밀번호를 입력해주세요"
            })
            login.observe(this@EmailLoginActivity, Observer {
                emailLogin(email.value!!, pw.value!!)
            })
        }
    }

    private fun emailLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    uuid = auth.currentUser?.uid.toString()
                    viewModel.insertUser(
                        User(
                            id = uuid,
                            fcm = fcm,
                            email = email,
                            pw = password,
                            image = ""
                        )
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                    showToast("로그인 성공")
                    viewModel.hideProgress()
                    finish()
                } else {
                    showToast("로그인 실패")
                    viewModel.hideProgress()
                }
            }
    }
}

package com.example.kimali

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class login_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_login_activity)

        val signUpTextView=findViewById<TextView>(R.id.txtLoginSignup)
        val loginId=findViewById<EditText>(R.id.edtLoginID)
        val loginPw=findViewById<EditText>(R.id.edtLoginPW)
        val loginButton=findViewById<Button>(R.id.loginButton)

        signUpTextView.setOnClickListener { view ->
            val intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener { view->
            val intent=Intent(this,Parent_firstView::class.java)
            startActivity(intent)
        }

    }
}

package com.example.kimali.Login

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity(){
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_signup)

        var who="보호자"

        val radioGroup=findViewById<RadioGroup>(R.id.radio_group)
        var signupId=findViewById(R.id.edtSignupID) as EditText
        val signupPw=findViewById<EditText>(R.id.edtSignupPW)
        val childName=findViewById<EditText>(R.id.edtChildName)
        val signupConfirm=findViewById<EditText>(R.id.edtSignupPWConfirm)
        val signupButton=findViewById<Button>(R.id.btnSignupSubmit)

        database = FirebaseDatabase.getInstance().reference

        //라디오 버튼 선택 함수
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.child_radioButton -> {
                    childName.setVisibility(View.VISIBLE)
                    who = "자녀"
                }
                R.id.parent_radioButton -> {
                    childName.setText("")
                    childName.setVisibility(View.GONE)
                    who = "보호자"
                }
            }
        })

        //회원가입 버튼
        signupButton.setOnClickListener { view->
            val signupIdText = signupId.text.toString()
            val signupPwText = signupPw.text.toString()
            val childIdText = childName.getText().toString()
            val signup_u_pw_confirm = signupConfirm.text.toString()

            if(signupPwText.equals(signup_u_pw_confirm)){
                writeNewUser(signupIdText, signupPwText, who)
            }
            else {
                password_dialog()
            }
        }

    }

    fun password_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("경고")
        builder.setMessage("비밀번호가 일치하지 않습니다.");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

    private fun writeNewUser(userId: String, user_pw: String?, who: String?) {
        val user = User(user_pw, who)
        database.child("users").child(userId).setValue(user)
    }

}
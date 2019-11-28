package com.example.kimali

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_signup)

        var who="보호자"

        val radioGroup=findViewById<RadioGroup>(R.id.radio_group)
        var signupId=findViewById(R.id.edtSignupID) as EditText
        val signupPw=findViewById<EditText>(R.id.edtSignupPW)
        val childId=findViewById<EditText>(R.id.edtChildID)
        val signupConfirm=findViewById<EditText>(R.id.edtSignupPWConfirm)
        val signupButton=findViewById<Button>(R.id.btnSignupSubmit)


        //라디오 버튼 선택 함수
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.parent_radioButton -> {
                    childId.setVisibility(View.VISIBLE)
                    who = "보호자"
                }
                R.id.child_radioButton -> {
                    childId.setText("")
                    childId.setVisibility(View.GONE)
                    who = "자녀"
                }
            }
        })

        //회원가입 버튼
        signupButton.setOnClickListener { view->
            val signupIdText = signupId.text.toString()
            val signupPwText = signupPw.text.toString()
            val childIdText = childId.getText().toString()
            val signup_u_pw_confirm = signupConfirm.text.toString()

            if(signupPwText.equals(signupConfirm)){

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
        /*val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.normal_dialog, null)
        val location_edit = view.findViewById<TextView>(R.id.delete_text)*/
        /*location_edit.setTextColor(Color.GRAY)
        location_edit.text = "비밀번호가 일치하지 않습니다."
        builder.setView(view)*/
        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

}
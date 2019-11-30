package com.example.kimali.Login

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SignupActivity : AppCompatActivity(){
    private lateinit var database: DatabaseReference
    lateinit var signupIdText: String
    lateinit var signupPwText: String
    lateinit var nameText: String
    lateinit var topic: String

    var who="보호자"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_signup)



        val radioGroup=findViewById<RadioGroup>(R.id.radio_group)
        var signupId=findViewById(R.id.edtSignupID) as EditText
        val signupPw=findViewById<EditText>(R.id.edtSignupPW)
        val name=findViewById<EditText>(R.id.edtName)
        val signupConfirm=findViewById<EditText>(R.id.edtSignupPWConfirm)
        val signupButton=findViewById<Button>(R.id.btnSignupSubmit)

        database = FirebaseDatabase.getInstance().reference

        //라디오 버튼 선택 함수
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.child_radioButton -> {
                    who = "자녀"
                }
                R.id.parent_radioButton -> {
                    who = "보호자"
                }
            }
        })

        //회원가입 버튼
        signupButton.setOnClickListener { view->
            signupIdText = signupId.text.toString()
            signupPwText = signupPw.text.toString()
            nameText = name.getText().toString()
            topic = randomString()
            val signup_u_pw_confirm = signupConfirm.text.toString()

            if(signupPwText.equals(signup_u_pw_confirm)){
                confirm_dialog()

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

    fun confirm_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("확인")
        builder.setMessage("회원가입 되었습니다.");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->
                writeNewUser(signupIdText, signupPwText, who, nameText, topic)
                finish()
            })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener{ dialog, which ->
            })
        builder.show()
    }

    //파이어베이스에 데이터 쓰는 메소드
    private fun writeNewUser(userId: String, user_pw: String?, who: String, nameText: String?, topic: String) {
        val user = User(user_pw, nameText)
        val child_user = Child(user_pw, nameText, topic)
        if(who == "보호자"){
            database.child("users").child(who).child(userId).setValue(user)
        }
        else{
            database.child("users").child(who).child(userId).setValue(child_user)

        }
    }

    fun randomString(): String {
        val topic = StringBuffer()
        val rnd = Random()
        for (i in 0..19) {
            val rIndex = rnd.nextInt(3)
            when (rIndex) {
                0 ->  // a-z
                    topic.append((rnd.nextInt(26) + 97).toChar())
                1 ->  // A-Z
                    topic.append((rnd.nextInt(26) + 65).toChar())
                2 ->  // 0-9
                    topic.append(rnd.nextInt(10))
            }
        }
        return topic.toString()
    }

}
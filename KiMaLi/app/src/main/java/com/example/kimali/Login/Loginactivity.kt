package com.example.kimali.Login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.BridgeActivity
import com.example.kimali.Parent_first_view.ParentFirstViewActivity
import com.example.kimali.R
import com.google.firebase.database.*


class Loginactivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    lateinit var login_id_list: ArrayList<String>
    lateinit var loginId: String
    lateinit var loginPw: String
    var okay: Int = 0
    var who="보호자"
    lateinit var name: String
    lateinit var topic: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_login_activity)

        val signUpTextView=findViewById<TextView>(R.id.txtLoginSignup)
        val loginIdText=findViewById<EditText>(R.id.edtLoginID)
        val loginPwText=findViewById<EditText>(R.id.edtLoginPW)
        val loginButton=findViewById<Button>(R.id.loginButton)

        val radioGroup=findViewById<RadioGroup>(R.id.radio_group_login)
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.child_radioButton_login -> {
                    who = "자녀"
                }
                R.id.parent_radioButton_login -> {
                    who = "보호자"
                }
            }
        })


        login_id_list = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().reference




        signUpTextView.setOnClickListener { view ->
            val intent= Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener { view->
            okay = 0
            loginId = loginIdText.text.toString()
            loginPw = loginPwText.text.toString()
            mDatabase.child("users").child(who).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) { // Get user value
                        //firebase에서 user-id 전부 가져온다
                        for (messageData in dataSnapshot.getChildren()){
                            var login_id = messageData.key.toString()
                            Log.d("sangmin", login_id)
                            login_id_list.add(login_id)
                        }
                        name = dataSnapshot.child(loginId).child("nameText").value.toString()
                        if(who == "자녀"){
                            topic = dataSnapshot.child(loginId).child("topic").value.toString()
                        }

                        //입력한 id와 데이터베이스 user_id 비교
                        for(login_id in login_id_list){

                            if (login_id==loginId){
                                okay = 1
                                var pw = dataSnapshot.child(login_id).child("user_pw").value.toString()
                                if (pw == loginPw) {
                                    login_dialog()
                                } else {
                                    pw_wrong_dialog()
                                }

                                break
                            }
                        }

                        if (okay == 0) {
                            id_wrong_dialog()
                        }

                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })



        }

    }

    fun id_wrong_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("경고")
        builder.setMessage("아이디가 일치하지 않습니다.");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

    fun pw_wrong_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("경고")
        builder.setMessage("비밀번호가 일치하지 않습니다.");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

    fun login_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("확인")
        builder.setMessage("로그인 되었습니다.");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->
                if (who == "보호자") {
                    val intent=Intent(applicationContext, ParentFirstViewActivity::class.java)
                    intent.putExtra("id", loginId)
                    intent.putExtra("who", who)
                    intent.putExtra("name", name)
                    startActivity(intent)
                } else {
                    val intent=Intent(applicationContext, BridgeActivity::class.java)
                    intent.putExtra("id", loginId)
                    intent.putExtra("who", who)
                    intent.putExtra("name", name)
                    intent.putExtra("topic", topic)
                    startActivity(intent)
                }
            })

        builder.show()
    }

    override fun onBackPressed() {
        finish()
    }

}

package com.example.kimali

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kimali.Login.Loginactivity
import com.example.kimali.Parent_first_view.ParentFirstViewActivity
import com.example.kimali.compensation.compensation_firstActivity

lateinit var text: String
lateinit var confirm_1: String

class BridgeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)

        if (intent.hasExtra("id")) {
            confirm_1 = "0";
            text = intent.getStringExtra("id")
            setTitle(text)
        } else if(intent.hasExtra("selectedString")){
            confirm_1 = "1";
            text = intent.getStringExtra("selectedString")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }



        var mission_list_btn = findViewById(R.id.mission_list_btn) as Button
        var reward_btn = findViewById(R.id.reward_btn) as Button

        mission_list_btn.setOnClickListener {
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("selectedString", text)
            this.startActivity(intent)
        }

        reward_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("selectedString", text)
            this.startActivity(intent)
        }
    }

    fun logout() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("알림")
        builder.setMessage("로그아웃 하시겠습니까?")

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, Loginactivity::class.java)
                this.startActivity(intent)
            })

        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

    override fun onBackPressed() {
        if (confirm_1.equals("0")) {
            logout()
        }
        else if (confirm_1.equals("1")){
            val intent = Intent(this, ParentFirstViewActivity::class.java)
            intent.putExtra("selectedString", text)
            this.startActivity(intent)
        }
        else{}
    }

}

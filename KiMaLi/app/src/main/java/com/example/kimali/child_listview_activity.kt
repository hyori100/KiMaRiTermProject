package com.example.kimali

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_compensation_money.*

class child_listview_activity : AppCompatActivity() {

    lateinit var text : String
    lateinit var who : String

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.hasExtra("selectedString")) {
            text = intent.getStringExtra("selectedString")
            who = intent.getStringExtra("who")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_child_listview_activity)


        val okButton=findViewById<Button>(R.id.ok_mission_button)

        val deadLineText=findViewById<TextView>(R.id.deadline_textview)
        val moneyText=findViewById<TextView>(R.id.moneyText)
        val pcTimeText=findViewById<TextView>(R.id.pcTimeText)

        val missionMessage=findViewById<TextView>(R.id.mission_Message_textView)
        val missionName=findViewById<TextView>(R.id.mission_Name_TextView)

        okButton.setOnClickListener { view->
            // 여기서 버튼을 누르면 부모에게 알림이 전송될 수 있게 설정해줘야함
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            //여기에 파이어베이스에서 현재 자녀의 이름 가지고오는 코드가 들어가야함..//
            this.startActivity(intent)
        }
    }

}
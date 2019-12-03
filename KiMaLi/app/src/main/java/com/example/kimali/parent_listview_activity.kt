package com.example.kimali

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class parent_listview_activity : AppCompatActivity() {

    lateinit var text : String
    lateinit var who : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_parent_listview)


        val okButton=findViewById<Button>(R.id.ok_mission_button)
        val modifyButton=findViewById<Button>(R.id.modify_mission_button)
        val deleteButton=findViewById<Button>(R.id.delete_mission_button)

        val deadLineText=findViewById<TextView>(R.id.deadline_textview)
        val moneyText=findViewById<TextView>(R.id.moneyText)
        val pcTimeText=findViewById<TextView>(R.id.pcTimeText)

        val missionMessage=findViewById<TextView>(R.id.mission_Message_textView)
        val missionName=findViewById<TextView>(R.id.mission_Name_TextView)

        okButton.setOnClickListener { view->
            val intent = Intent(this, Parent_missionList::class.java)

            //여기에 파이어베이스에서 현재 자녀의 이름 가지고오는 코드가 들어가야함..//
            this.startActivity(intent)
        }
        // 이부분이 수정버튼 클릭시
        modifyButton.setOnClickListener { view->
            // 여기서 파이어베이스에 현재 바뀐 내용을 저장할 수 있는 코드 작성해야함
            val intent = Intent(this, this::class.java)
            this.startActivity(intent)
        }
        // 삭제하기 버튼
        deleteButton.setOnClickListener { view->
            // 삭제 시 파이어베이스에서 현재 내용을 전부 다 delete
            val intent = Intent(this, this::class.java)
            this.startActivity(intent)
        }

    }

}
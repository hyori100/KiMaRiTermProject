package com.example.kimali

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class parent_listview_activity : AppCompatActivity() {

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
        modifyButton.setOnClickListener { view->
            val intent = Intent(this, this::class.java)
            this.startActivity(intent)
        }
        deleteButton.setOnClickListener { view->

        }

    }

}
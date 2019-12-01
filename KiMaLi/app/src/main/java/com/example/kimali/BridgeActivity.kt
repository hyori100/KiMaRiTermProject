package com.example.kimali

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.kimali.compensation.compensation_firstActivity

lateinit var text: String

class BridgeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)

        if (intent.hasExtra("id")) {
            text = intent.getStringExtra("id")
            setTitle(text)
        } else if(intent.hasExtra("selectedString")){
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
}

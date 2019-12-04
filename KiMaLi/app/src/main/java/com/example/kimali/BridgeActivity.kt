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



class BridgeActivity : AppCompatActivity() {
    lateinit var text: String
    lateinit var who: String
    lateinit var name: String
    lateinit var confirm_1: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)

        if (intent.hasExtra("id")) {
            confirm_1 = "0";
            text = intent.getStringExtra("id")
            who = intent.getStringExtra("who")
            name = intent.getStringExtra("name")
            setTitle(name)
        } else if(intent.hasExtra("selectedString")){
            confirm_1 = "1";
            text = intent.getStringExtra("selectedString")
            who = intent.getStringExtra("who")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }



        var mission_list_btn = findViewById(R.id.mission_list_btn) as Button
        var reward_btn = findViewById(R.id.reward_btn) as Button

        mission_list_btn.setOnClickListener {
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            this.startActivity(intent)
        }

        reward_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            this.startActivity(intent)
        }
    }


}

package com.example.kimali

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kimali.Mission.Parent_missionList
import com.example.kimali.compensation.compensation_firstActivity



class BridgeActivity : AppCompatActivity() {
    lateinit var text: String

    lateinit var who: String
    lateinit var name: String
    lateinit var userId: String
    lateinit var topic: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        Log.d("sangmee", topic)
        setTitle(name)

        var mission_list_btn = findViewById(R.id.mission_list_btn) as Button
        var reward_btn = findViewById(R.id.reward_btn) as Button

        mission_list_btn.setOnClickListener {
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("name", name)
            intent.putExtra("who",who)
            intent.putExtra("topic", topic)
            this.startActivity(intent)
        }

        reward_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("name", name)
            intent.putExtra("who",who)
            intent.putExtra("topic", topic)
            this.startActivity(intent)
        }
    }


}

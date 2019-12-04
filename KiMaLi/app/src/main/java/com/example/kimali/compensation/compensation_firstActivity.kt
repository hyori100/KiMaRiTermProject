package com.example.kimali.compensation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kimali.*

class compensation_firstActivity : AppCompatActivity() {
    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compensation_first)


        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        Log.d("sangmee", topic)
        setTitle(name)



        var money_btn = findViewById(R.id.money_btn) as Button
        var pc_btn = findViewById(R.id.pc_btn) as Button

        money_btn.setOnClickListener {
            val intent = Intent(this, compensation_money::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            this.startActivity(intent)
        }

        pc_btn.setOnClickListener {
            val intent = Intent(this, compensation_pc::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            this.startActivity(intent)
        }




    }

}

package com.example.kimali.compensation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kimali.*
import com.example.kimali.R
import com.google.firebase.database.*

class compensation_firstActivity : AppCompatActivity() {
    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String
    private lateinit var mDatabase: DatabaseReference
    lateinit var total_money :String
    lateinit var total_pcTime :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compensation_first)


        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        Log.d("sangmee", topic)
        setTitle(name)
        mDatabase = FirebaseDatabase.getInstance().reference


        mDatabase.child("mission").child(topic).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // Get user value

                    total_money = dataSnapshot.child("total_money").child("moneys").value.toString()
                    total_pcTime = dataSnapshot.child("total_pcTime").child("pcTimes").value.toString()
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        var money_btn = findViewById(R.id.money_btn) as Button
        var pc_btn = findViewById(R.id.pc_btn) as Button

        money_btn.setOnClickListener {
            val intent = Intent(this, compensation_money::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            intent.putExtra("total_money", total_money)
            this.startActivity(intent)
        }

        pc_btn.setOnClickListener {
            val intent = Intent(this, compensation_pc::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            intent.putExtra("total_pcTime", total_pcTime)
            this.startActivity(intent)
        }




    }
    override fun onBackPressed() {
        val intent = Intent(this, BridgeActivity::class.java)
        intent.putExtra("id", userId)
        intent.putExtra("name", name)
        intent.putExtra("who", who)
        intent.putExtra("topic", topic)
        this.startActivity(intent)

    }

}

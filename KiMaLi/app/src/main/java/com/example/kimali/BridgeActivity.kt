package com.example.kimali

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kimali.Mission.MissionList
import com.example.kimali.compensation.compensation_firstActivity
import com.google.firebase.database.*


class BridgeActivity : AppCompatActivity() {
    private lateinit var mDatabase: DatabaseReference


    lateinit var who: String
    lateinit var name: String
    lateinit var userId: String
    lateinit var topic: String
    lateinit var missionName_list: ArrayList<String>
    lateinit var deadline_list: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        mDatabase = FirebaseDatabase.getInstance().reference
        missionName_list = ArrayList()
        deadline_list = ArrayList()

        Log.d("sangmee", topic)
        setTitle(name)

        var mission_list_btn = findViewById(R.id.mission_list_btn) as Button
        var reward_btn = findViewById(R.id.reward_btn) as Button

        mDatabase.child("mission").child(topic).child("detailmission").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // Get user value
                    //firebase에서 user-id 전부 가져온다
                    for (messageData in dataSnapshot.getChildren()){
                        var missionName = messageData.key.toString()
                        Log.d("sangmeeMission", missionName)
                        missionName_list.add(missionName)

                    }
                    for (i in missionName_list){
                        var deadline = dataSnapshot.child(i).child("deadLineString").value.toString()
                        deadline_list.add(deadline)
                        Log.d("sangmeeDeadLine", deadline)
                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        mission_list_btn.setOnClickListener {
            val intent = Intent(this, MissionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("name", name)
            intent.putExtra("who",who)
            intent.putExtra("topic", topic)
            intent.putExtra("missionName_list", missionName_list)
            intent.putExtra("deadline_list", deadline_list)
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

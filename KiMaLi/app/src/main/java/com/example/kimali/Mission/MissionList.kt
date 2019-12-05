package com.example.kimali.Mission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.kimali.R
import com.example.kimali.ChildDetailMission
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mission_list.*
import java.util.*
import kotlin.collections.ArrayList

class MissionList : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    lateinit var userId : String
    lateinit var who : String
    lateinit var name : String
    lateinit var topic: String
    var missionName_list: ArrayList<String>  = ArrayList()
    var deadline_list: ArrayList<String> = ArrayList()
    var mission_message_list: ArrayList<String>  = ArrayList()
    var money_list: ArrayList<String> = ArrayList()
    var pcTime_list: ArrayList<String>  = ArrayList()
    lateinit var missionName : String
    lateinit var deadline : String
    lateinit var mission_message: String
    lateinit var money: String
    lateinit var pcTime: String



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_mission_list)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        missionName_list = intent.getStringArrayListExtra("missionName_list")
        deadline_list = intent.getStringArrayListExtra("deadline_list")
        mDatabase = FirebaseDatabase.getInstance().reference

        setTitle(name)

        val textView1 = findViewById(R.id.year_select) as TextView
        val textView2 = findViewById(R.id.month_select) as TextView
        val textView3 = findViewById(R.id.day_select) as TextView

        val c=Calendar.getInstance()
        textView1.setText(Integer.toString(c.get(Calendar.YEAR)))
        textView2.setText(Integer.toString(c.get(Calendar.MONTH)+1))
        textView3.setText(Integer.toString(c.get(Calendar.DAY_OF_MONTH)))



        val addButton=findViewById(R.id.addButton) as Button
        addButton.setOnClickListener { view->
            val intent = Intent(this, AddMission::class.java)
            intent.putExtra("who",who)
            intent.putExtra("name",name)
            intent.putExtra("id", userId)
            intent.putExtra("topic", topic)
            intent.putExtra("missionName_list", missionName_list)
            intent.putExtra("deadline_list", deadline_list)

            this.startActivity(intent)
        }

        if(who.equals("보호자")){
            addButton.setEnabled(true);
            addButton.setVisibility(Button.VISIBLE);
        }else {
            addButton.setEnabled(false);
            addButton.setVisibility(Button.INVISIBLE);
        }

        mDatabase.child("mission").child(topic).child("detailmission").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // Get user value

                    for (i in missionName_list){
                        var mission_message = dataSnapshot.child(i).child("mission_message").value.toString()
                        var money = dataSnapshot.child(i).child("money").value.toString()
                        var pcTime = dataSnapshot.child(i).child("pcTime").value.toString()
                        mission_message_list.add(mission_message)
                        money_list.add(money)
                        pcTime_list.add(pcTime)
                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })


        //baseAdapter로 생성
        mission_list.adapter =
            HBaseAdapter(this, missionName_list, deadline_list)
        mission_list.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Toast.makeText(this, "Clicked item :"+" "+selectedItem, Toast.LENGTH_SHORT).show()
            missionName= missionName_list.get(position)
            deadline = deadline_list.get(position)
            mission_message = mission_message_list.get(position)
            money = money_list.get(position)
            pcTime = pcTime_list.get(position)

            if(who=="보호자") {

                val intent = Intent(this, DetailMission::class.java)
                intent.putExtra("who", who)
                intent.putExtra("id", userId)
                intent.putExtra("name", name)
                intent.putExtra("topic", topic)
                intent.putExtra("missionName_list", missionName_list)
                intent.putExtra("deadline_list", deadline_list)
                intent.putExtra("missionName", missionName)
                intent.putExtra("deadline", deadline)
                intent.putExtra("mission_message", mission_message)
                intent.putExtra("money", money)
                intent.putExtra("pcTime", pcTime)
                intent.putExtra("money_list", money_list)
                intent.putExtra("pcTime_list", pcTime_list)

                this.startActivity(intent)
            }
            else {

                missionName= missionName_list.get(position)
                deadline = deadline_list.get(position)

                val intent = Intent(this, ChildDetailMission::class.java)
                intent.putExtra("who", who)
                intent.putExtra("id", userId)
                intent.putExtra("name", name)
                intent.putExtra("topic", topic)
                intent.putExtra("missionName_list", missionName_list)
                intent.putExtra("deadline_list", deadline_list)
                intent.putExtra("missionName", missionName)
                intent.putExtra("deadline", deadline)
                intent.putExtra("mission_message", mission_message)
                intent.putExtra("money", money)
                intent.putExtra("pcTime", pcTime)
                intent.putExtra("money_list", money_list)
                intent.putExtra("pcTime_list", pcTime_list)
                this.startActivity(intent)
            }
        }



    }


}
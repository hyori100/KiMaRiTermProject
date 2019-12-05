package com.example.kimali

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.Mission.Parent_missionList
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class child_listview_activity : AppCompatActivity() {

    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String

    var missionName_list: ArrayList<String> = ArrayList()
    var deadline_list: ArrayList<String> = ArrayList()

    lateinit var missionName : String
    lateinit var deadline : String
    lateinit var mission_message: String
    lateinit var money: String
    lateinit var pcTime: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_child_listview_activity)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        missionName_list = intent.getStringArrayListExtra("missionName_list")
        deadline_list = intent.getStringArrayListExtra("deadline_list")
        missionName = intent.getStringExtra("missionName")
        deadline = intent.getStringExtra("deadline")
        mission_message = intent.getStringExtra("mission_message")
        money = intent.getStringExtra("money")
        pcTime = intent.getStringExtra("pcTime")


        val okButton=findViewById<Button>(R.id.ok_mission_button)

        val deadLineText=findViewById<TextView>(R.id.deadline_textview)
        val moneyText=findViewById<TextView>(R.id.moneyText)
        val pcTimeText=findViewById<TextView>(R.id.pcTimeText)

        val missionMessage=findViewById<TextView>(R.id.mission_Message_textView)
        val missionName_text=findViewById<TextView>(R.id.mission_Name_TextView)

        missionName_text.setText(missionName)
        missionMessage.setText(mission_message)
        moneyText.setText(money)
        pcTimeText.setText(pcTime)



        val c= Calendar.getInstance()

        var dateString=""
        dateString+=Integer.toString(c.get(Calendar.YEAR))+"-"
        dateString+=Integer.toString(c.get(Calendar.MONTH)+1)+"-"
        dateString+=Integer.toString(c.get(Calendar.DAY_OF_MONTH))
        deadLineText.setText(dateString+" ~ "+deadline)

        okButton.setOnClickListener { view->
            // 여기서 버튼을 누르면 부모에게 알림이 전송될 수 있게 설정해줘야함
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            intent.putExtra("deadline_list", deadline_list)
            intent.putExtra("missionName_list", missionName_list)
            this.startActivity(intent)
            finish()
        }
    }

}
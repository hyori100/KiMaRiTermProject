package com.example.kimali.Mission

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.R
import com.google.firebase.database.*
import java.util.*

class DetailMission : AppCompatActivity() {
    private lateinit var mDatabase: DatabaseReference

    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String

    var missionName_list: ArrayList<String>  = ArrayList()
    var deadline_list: ArrayList<String> = ArrayList()

    lateinit var missionName : String
    lateinit var deadline : String
    lateinit var mission_message: String
    lateinit var money: String
    lateinit var pcTime: String
    var money_list: ArrayList<String> = ArrayList()
    var pcTime_list: ArrayList<String>  = ArrayList()
    var total_money = 0
    var total_pcTime = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {

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
        money_list = intent.getStringArrayListExtra("money_list")
        pcTime_list = intent.getStringArrayListExtra("pcTime_list")
        mDatabase = FirebaseDatabase.getInstance().reference





        Log.d("sangmee", topic)
        setTitle(name)


        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_detail_mission)


        val okButton=findViewById<Button>(R.id.ok_mission_button)
        val modifyButton=findViewById<Button>(R.id.modify_mission_button)
        val deleteButton=findViewById<Button>(R.id.delete_mission_button)

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
            total_money += money.toInt()
            total_pcTime += pcTime.toDouble()
            writeChild()
            val intent = Intent(this, MissionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            intent.putExtra("deadline_list", deadline_list)
            intent.putExtra("missionName_list", missionName_list)
            //여기에 파이어베이스에서 현재 자녀의 이름 가지고오는 코드가 들어가야함..//
            this.startActivity(intent)
            finish()
        }
        // 이부분이 수정버튼 클릭시
        modifyButton.setOnClickListener { view->
            // 여기서 파이어베이스에 현재 바뀐 내용을 저장할 수 있는 코드 작성해야함
            val intent = Intent(this, MissionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            intent.putExtra("deadline_list", deadline_list)
            intent.putExtra("missionName_list", missionName_list)
            this.startActivity(intent)
        }
        // 삭제하기 버튼
        deleteButton.setOnClickListener { view->
            // 삭제 시 파이어베이스에서 현재 내용을 전부 다 delete
            val intent = Intent(this, MissionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            intent.putExtra("deadline_list", deadline_list)
            intent.putExtra("missionName_list", missionName_list)
            this.startActivity(intent)
        }

    }

    private fun writeChild() {
        val post1 = Moneys(total_money.toString())
        val post2 = PcTimes(total_pcTime.toString())
        val postValues1 = post1.toMap()
        val postValues2 = post2.toMap()
        val childUpdates1 = HashMap<String, Any>()
        val childUpdates2 = HashMap<String, Any>()
        childUpdates1["/mission/$topic/moneys"] = postValues1
        childUpdates2["/mission/$topic/pcTimes"] = postValues2

        mDatabase.updateChildren(childUpdates1)
        mDatabase.updateChildren(childUpdates2)
    }

}
package com.example.kimali.Mission

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.ChildDetailMission
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mission_list.*
import org.eclipse.paho.android.service.MqttAndroidClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MissionList : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    lateinit var userId : String
    lateinit var who : String
    lateinit var name : String
    lateinit var topic: String
    var missionName_list: ArrayList<String>  = ArrayList()
    var dday_list:ArrayList<Int> = ArrayList()
    var deadline_list: ArrayList<String> = ArrayList()
    var mission_message_list: ArrayList<String>  = ArrayList()
    var money_list: ArrayList<String> = ArrayList()
    var pcTime_list: ArrayList<String>  = ArrayList()
    lateinit var missionName : String
    lateinit var deadline : String
    lateinit var mission_message: String
    lateinit var money: String
    lateinit var pcTime: String

    var client: MqttAndroidClient? = null
    lateinit var topic_value: String



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_mission_list)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")

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
            this.startActivity(intent)
        }

        if(who.equals("보호자")){
            addButton.setEnabled(true);
            addButton.setVisibility(Button.VISIBLE);

            //mqtt 코드!!!!!!!!!!!!!!!!!!!!!
            /*val recentPostsQuery: Query =
                mDatabase.child(userId).child("topic")
            recentPostsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    topic_value = dataSnapshot.value.toString()
                    Log.i("hyoriTopic", topic_value)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })*/


        }else {
            addButton.setEnabled(false);
            addButton.setVisibility(Button.INVISIBLE);

        }

        fun getDDay(myear: Int, mmonth: Int, mday: Int): Int {
            var mmonth = mmonth
            return try {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val todaCal = Calendar.getInstance()
                val ddayCal = Calendar.getInstance()

                mmonth -= 1
                ddayCal[myear, mmonth] = mday // D-day의 날짜를 입력
                Log.e("테스트", simpleDateFormat.format(todaCal.time).toString() + "")
                Log.e("테스트", simpleDateFormat.format(ddayCal.time).toString() + "")

                val today =
                    todaCal.timeInMillis / (24 * 60 * 60 * 1000)
                val dday = ddayCal.timeInMillis / (24 * 60 * 60 * 1000)
                val count = dday - today // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
                count.toInt()

            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }

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
                        val str2 = StringTokenizer(deadline, " -")
                        val i_array = IntArray(3)
                        for (i in 0 until str2.countTokens()) {
                            var str1=str2.nextToken().toInt()
                            i_array.set(i,str1)
                        }
                        var ddayInt=getDDay(i_array.get(0),i_array.get(1),i_array.get(2))
                        dday_list.add(ddayInt)
                        deadline_list.add(deadline)
                        Log.d("sangmeeDeadLine", deadline)
                    }


                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        mDatabase.child("mission").child(topic).child("detailmission").addChildEventListener(object :ChildEventListener{

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                mission_list.adapter =
                    HBaseAdapter(applicationContext, missionName_list, dday_list)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                mission_list.adapter =
                    HBaseAdapter(applicationContext, missionName_list, dday_list)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                mission_list.adapter =
                    HBaseAdapter(applicationContext, missionName_list, dday_list)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                mission_list.adapter =
                    HBaseAdapter(applicationContext, missionName_list, dday_list)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                mission_list.adapter =
                    HBaseAdapter(applicationContext, missionName_list, dday_list)
            }

        })






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
            HBaseAdapter(this, missionName_list, dday_list)
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

                intent.putExtra("missionName", missionName)
                intent.putExtra("deadline", deadline)
                intent.putExtra("mission_message", mission_message)
                intent.putExtra("money", money)
                intent.putExtra("pcTime", pcTime)

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

                intent.putExtra("missionName", missionName)
                intent.putExtra("deadline", deadline)
                intent.putExtra("mission_message", mission_message)
                intent.putExtra("money", money)
                intent.putExtra("pcTime", pcTime)
                this.startActivity(intent)
            }
        }



    }




}

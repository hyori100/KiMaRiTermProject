package com.example.kimali

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.Mission.MissionList
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

class ChildDetailMission : AppCompatActivity() {

    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String



    lateinit var missionName : String
    lateinit var deadline : String
    lateinit var mission_message: String
    lateinit var money: String
    lateinit var pcTime: String

    lateinit var client: MqttAndroidClient
    var topicStr = "안녕하세요"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_child_detail_mission)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")

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

        val clientId: String = MqttClient.generateClientId()
        client = MqttAndroidClient(applicationContext, "tcp://broker.hivemq.com:1883", clientId)
        Log.i("hyolls","부모 토픽값 : "+topic)

        //connect하는 부분
        try {
            Log.i("hyolls","확인")
            val token = client!!.connect(getMqttConnectionOption())
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) { //연결에 성공한 경우
                    Log.i("hyoriTopic", "connection1")
                    try {
                        client!!.subscribe(topic, 0) //topic값 받음.
                    } catch (e: MqttException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) { //연결에 실패한 경우
                    Toast.makeText(applicationContext, "연결에 실패하였습니다...(1)", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

        try {
            val qos = 0
            val subToken: IMqttToken =
                client.publish("topic", topicStr.toByteArray(), qos, false)
            subToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) { //연결에 성공한 경우
                    Log.i("hyolls", "connection2")
                    /*val text = "보호자에게 현재위치를 전송합니다."
                    Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()*/
                    topicStr = "안녕하세요"

                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) { //연결에 실패한 경우
                    Toast.makeText(applicationContext, "연결에 실패하였습니다...(2)", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (fe: MqttException) {
            fe.printStackTrace()
        }

        val c= Calendar.getInstance()

        var dateString=""
        dateString+=Integer.toString(c.get(Calendar.YEAR))+"-"
        dateString+=Integer.toString(c.get(Calendar.MONTH)+1)+"-"
        dateString+=Integer.toString(c.get(Calendar.DAY_OF_MONTH))
        deadLineText.setText(dateString+" ~ "+deadline)

        okButton.setOnClickListener { view->
            // 여기서 버튼을 누르면 부모에게 알림이 전송될 수 있게 설정해줘야함


            val intent = Intent(this, MissionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("who", who)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)

            this.startActivity(intent)
            finish()
        }
    }

    private fun getMqttConnectionOption(): MqttConnectOptions? {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.setAutomaticReconnect(true)
        mqttConnectOptions.setWill("aaa", "I am going offline".toByteArray(), 1, true)
        return mqttConnectOptions
    }


}
package com.example.kimali

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.kimali.Mission.MissionList
import com.example.kimali.compensation.compensation_firstActivity
import com.google.firebase.database.DatabaseReference
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*


class BridgeActivity : AppCompatActivity() {
    private lateinit var mDatabase: DatabaseReference


    lateinit var who: String
    lateinit var name: String
    lateinit var userId: String
    lateinit var topic: String
    //lateinit var missionName_list: ArrayList<String>
    //lateinit var deadline_list: ArrayList<String>
    var client: MqttAndroidClient? = null


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
            val intent = Intent(this, MissionList::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("name", name)
            intent.putExtra("who",who)
            intent.putExtra("topic", topic)
            //intent.putExtra("missionName_list", missionName_list)
            //intent.putExtra("deadline_list", deadline_list)
            this.startActivity(intent)
            finish();
        }

        reward_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("id", userId)
            intent.putExtra("name", name)
            intent.putExtra("who",who)
            intent.putExtra("topic", topic)
            this.startActivity(intent)
            finish();
        }

        val clientId: String = MqttClient.generateClientId()
        Log.i("hyolls","자식 토픽값 : "+topic)
        client = MqttAndroidClient(applicationContext, "tcp://broker.hivemq.com:1883", clientId)


        //connect하는 부분
        try {
            Log.i("hyolls","확인")
            val token = client!!.connect(getMqttConnectionOption())
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) { //연결에 성공한 경우
                    Log.i("hyoriTopic", "connection1")
                    try {
                        client!!.subscribe("topic", 0) //topic값 받음
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

        client!!.setCallback(object : MqttCallback {
            //콜백처리하는 부분
            override fun connectionLost(throwable: Throwable) {
                Toast.makeText(applicationContext, "연결이 끊겼습니다...", Toast.LENGTH_SHORT).show()
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                if (topic == topic) { //topic별로 나누어서
                    val msg = String(message.payload)
                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })

    }

    private fun getMqttConnectionOption(): MqttConnectOptions? {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.setAutomaticReconnect(true)
        mqttConnectOptions.setWill("aaa", "I am going offline".toByteArray(), 1, true)
        return mqttConnectOptions
    }

    ///////알림창 화면구성/////////
    private fun createNotification() {
        val builder =
            NotificationCompat.Builder(this, "default")

        builder.setSmallIcon(R.drawable.noti)
        var notificationTitle = "<알림>"
        var notificationText = "새로운 미션이 등록되었습니다."
        builder.setContentTitle(notificationTitle)
        builder.setContentText(notificationText)

        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true)

        // 알림 표시
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        // id값은
// 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build())
    }


}

package com.example.kimali.compensation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.R
import com.google.firebase.database.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class compensation_firstActivity : AppCompatActivity() {
    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String
    private lateinit var mDatabase: DatabaseReference
    lateinit var total_money :String
    lateinit var total_pcTime :String
    var client: MqttAndroidClient? = null

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

    private fun getMqttConnectionOption(): MqttConnectOptions? {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.setAutomaticReconnect(true)
        mqttConnectOptions.setWill("aaa", "I am going offline".toByteArray(), 1, true)
        return mqttConnectOptions
    }

}

package com.example.kimali

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.Login.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class parent_setting_add_activity : AppCompatActivity() {

    lateinit var text : String
    lateinit var who : String
    var money=0
    var pcTime=0

    var deadLineDate=""

    override fun onCreate(savedInstanceState: Bundle?) {


        if (intent.hasExtra("selectedString")) {
            text = intent.getStringExtra("selectedString")
            who = intent.getStringExtra("who")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_parent_adding)

        val missionName = findViewById(R.id.mission_Name_EditText) as EditText
        val missionMessage = findViewById(R.id.mission_Message_EditText) as EditText

        val finishButton=findViewById<Button>(R.id.add_finish_button)

        val radioGroup=findViewById<RadioGroup>(R.id.radioradio)

        var startYear=0
        var startMonth=0
        var startDay=0

        var lastYear=0
        var lastMonth=0
        var lastDay=0

        var strDate="date"
        var lastDate="date"



        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.moneyRadioButton -> {
                    setting_money_dialog()
                }
                R.id.pcRadioButton -> {
                    setting_pcTime_dialog()
                }
            }
        })

        //설정하게 되는 미션시작날짜
        var startDatebutton=findViewById<View>(R.id.startDateSettingButton)
        startDatebutton.setOnClickListener(View.OnClickListener { view ->
            val c = Calendar.getInstance()
            val nYear = c.get(Calendar.YEAR)
            val nMon = c.get(Calendar.MONTH)
            val nDay = c.get(Calendar.DAY_OF_MONTH)

            val mDateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    strDate = year.toString() + "년 "
                    strDate += (monthOfYear + 1).toString() + "월 "
                    strDate += dayOfMonth.toString() + "일"

                    startYear=year
                    startMonth=monthOfYear+1
                    startDay=dayOfMonth

                    /*textView1.setText(year.toString())
                    textView2.setText((monthOfYear+1).toString())
                    textView3.setText(dayOfMonth.toString())*/

                    Toast.makeText(applicationContext, strDate, Toast.LENGTH_SHORT).show()
                }

            val oDialog = DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                mDateSetListener, nYear, nMon, nDay
            )
            oDialog.show()

        })

        //설정하게 되는 미션완료날짜
        var lastDateButton=findViewById<View>(R.id.lastDateSettingButton)
        lastDateButton.setOnClickListener(View.OnClickListener { view ->
            val c = Calendar.getInstance()
            val nYear = c.get(Calendar.YEAR)
            val nMon = c.get(Calendar.MONTH)
            val nDay = c.get(Calendar.DAY_OF_MONTH)

            val mDateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    lastDate = year.toString() + "년 "
                    lastDate += (monthOfYear + 1).toString() + "월 "
                    lastDate += dayOfMonth.toString() + "일"

                    lastYear=year
                    lastMonth=monthOfYear+1
                    lastDay=dayOfMonth

                    /*textView1.setText(year.toString())
                    textView2.setText((monthOfYear+1).toString())
                    textView3.setText(dayOfMonth.toString())*/

                    Toast.makeText(applicationContext, lastDate, Toast.LENGTH_SHORT).show()
                }

            val oDialog = DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                mDateSetListener, nYear, nMon, nDay
            )
            oDialog.show()

        })

        //확인완료 버튼
        finishButton.setOnClickListener { view->
            deadLineDate=strDate+" ~ "+lastDate
            //한 미션의 데이터 확인을 위한 toast문
            Toast.makeText(applicationContext,missionName.getText().toString()+","+missionMessage.getText().toString()+","+
                money+","+pcTime+","+deadLineDate,Toast.LENGTH_LONG).show()

            writeNewMission(missionName.getText().toString(),missionMessage.getText().toString()
                ,money,pcTime,deadLineDate)
            val intent = Intent(this, parent_listview_activity::class.java)
            intent.putExtra("who",who)
            intent.putExtra("selectedString", text)
            this.startActivity(intent)


        }


    }

    //파이어베이스에 데이터 쓰는 메소드
    private fun writeNewMission(missionName: String, mission_message: String?, money: Int?, pcTime: Int?,deadLineString:String?) {
        val oneMission = OneMission(missionName,mission_message,money,pcTime,deadLineString)

        /*database.child("users").child(userId).setValue(user)*/
    }

    //보상해줄 금액 세팅하기 위한 다이얼로그
    fun setting_money_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        val inflater: LayoutInflater = layoutInflater
        val view = inflater.inflate(R.layout.money_custom_dialog, null)
        builder.setView(view)
        val money_setting = view.findViewById<EditText>(R.id.moneyText)


        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->
                money=Integer.parseInt(money_setting.getText().toString())

            })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()

    }

    //보상해줄 PC사용시간을 세팅하기 위한 다이얼로그
    fun setting_pcTime_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        val inflater: LayoutInflater = layoutInflater
        val view = inflater.inflate(R.layout.pctime_custom_dialog, null)
        builder.setView(view)
        val pc_setting = view.findViewById<EditText>(R.id.pcText)


        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->
                pcTime=Integer.parseInt(pc_setting.getText().toString())

            })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()

    }
}
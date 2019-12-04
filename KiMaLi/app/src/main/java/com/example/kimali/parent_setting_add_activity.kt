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
import java.text.SimpleDateFormat
import java.util.*


class parent_setting_add_activity : AppCompatActivity() {

    lateinit var text : String
    lateinit var who : String
    var money=0
    var pcTime=0

    var lastYear=0
    var lastMonth=0
    var lastDay=0

    var deadLineDate=""

    //디데이 값
    var ddayInt=0

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

        //현재 날짜를 텍스뷰로 가지고오는 부분
        val dateView=findViewById<TextView>(R.id.startDateText)
        val c=Calendar.getInstance()
        var dateString=""
        dateString+=Integer.toString(c.get(Calendar.YEAR))+"-"
        dateString+=Integer.toString(c.get(Calendar.MONTH)+1)+"-"
        dateString+=Integer.toString(c.get(Calendar.DAY_OF_MONTH))+" ~"
        dateView.setText(dateString)

        //설정하게 되는 미션완료날짜
        var lastDateButton=findViewById<View>(R.id.lastDateSettingButton)
        lastDateButton.setOnClickListener(View.OnClickListener { view ->
            val c = Calendar.getInstance()
            val nYear = c.get(Calendar.YEAR)
            val nMon = c.get(Calendar.MONTH)
            val nDay = c.get(Calendar.DAY_OF_MONTH)

            val mDateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    lastDate = year.toString() + "-"
                    lastDate += (monthOfYear + 1).toString() + "-"
                    lastDate += dayOfMonth.toString()

                    lastYear=year
                    lastMonth=monthOfYear+1
                    lastDay=dayOfMonth

                    //디데이 계산하기
                    ddayInt=getDDay(lastYear,lastMonth,lastDay)
                    Toast.makeText(applicationContext, "현재 dday는 "+ddayInt, Toast.LENGTH_SHORT).show()

                    /*textView1.setText(year.toString())
                    textView2.setText((monthOfYear+1).toString())
                    textView3.setText(dayOfMonth.toString())*/


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
            deadLineDate=lastDate
            //한 미션의 데이터 확인을 위한 toast문
            Toast.makeText(applicationContext,missionName.getText().toString()+","+missionMessage.getText().toString()+","+
                money+","+pcTime+","+deadLineDate,Toast.LENGTH_LONG).show()

            writeNewMission(missionName.getText().toString(),missionMessage.getText().toString()
                ,money,pcTime,deadLineDate,ddayInt)

            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("who",who)
            intent.putExtra("selectedString", text)
            this.startActivity(intent)


        }


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
    //파이어베이스에 데이터 쓰는 메소드
    private fun writeNewMission(missionName: String, mission_message: String?, money: Int, pcTime: Int,deadline:String?,dday:Int) {
        val oneMission = OneMission(missionName,mission_message,money,pcTime,deadline,dday)

        //oneMission 이라는 클래스 하나에 들어가는 정보 toast문으로 찍기
        Toast.makeText(applicationContext,oneMission.mission_name+","+oneMission.mission_message
                +","+oneMission.money+","+oneMission.pcTime+","+oneMission.deadLineString+","+oneMission.dday,Toast.LENGTH_LONG).show()

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
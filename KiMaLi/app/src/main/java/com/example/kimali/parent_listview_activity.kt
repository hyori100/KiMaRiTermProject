package com.example.kimali

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_compensation_money.*
import java.util.*

class parent_listview_activity : AppCompatActivity() {

    lateinit var text : String
    lateinit var who : String
    lateinit var mission_Name : String

    override fun onCreate(savedInstanceState: Bundle?) {

        if (intent.hasExtra("selectedString")) {
            text = intent.getStringExtra("selectedString")
            who = intent.getStringExtra("who")
            mission_Name=intent.getStringExtra("missionName")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }


        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_parent_listview)


        val okButton=findViewById<Button>(R.id.ok_mission_button)
        val modifyButton=findViewById<Button>(R.id.modify_mission_button)
        val deleteButton=findViewById<Button>(R.id.delete_mission_button)

        val deadLineText=findViewById<TextView>(R.id.deadline_textview)
        val moneyText=findViewById<TextView>(R.id.moneyText)
        val pcTimeText=findViewById<TextView>(R.id.pcTimeText)

        val missionMessage=findViewById<TextView>(R.id.mission_Message_textView)
        val missionName=findViewById<TextView>(R.id.mission_Name_TextView)

        //미션명은 listview 자체에서 전달받은 string으로 입력받음
        missionName.setText(mission_Name)

        ////////////////////////////////////////////////////////////////////////
        //이곳에서 파베를 불러와야함 하나의 미션 정보를
        val mission=OneMission("청소리돌리기","안녕",5000,5,"2019-12-28",5)

        //미션 상세내용은 파베에서 불러와야함.
        missionMessage.setText(mission.mission_message)

        //미션에서의 보상금 money, pcTime도 파베에서 불러와야함.
        moneyText.setText(Integer.toString(mission.money))
        pcTimeText.setText(Integer.toString(mission.pcTime))

        //설정된 기간은 현재 날짜와 mission.deadLineString으로 구성된다
        val lastInt=mission.dday

        val c= Calendar.getInstance()

        var dateString=""
        dateString+=Integer.toString(c.get(Calendar.YEAR))+"-"
        dateString+=Integer.toString(c.get(Calendar.MONTH)+1)+"-"
        dateString+=Integer.toString(c.get(Calendar.DAY_OF_MONTH))
        deadLineText.setText(dateString+" ~ "+mission.deadLineString)


        okButton.setOnClickListener { view->
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            //여기에 파이어베이스에서 현재 자녀의 이름 가지고오는 코드가 들어가야함..//
            this.startActivity(intent)
        }
        // 이부분이 수정버튼 클릭시
        modifyButton.setOnClickListener { view->
            // 여기서 파이어베이스에 현재 바뀐 내용을 저장할 수 있는 코드 작성해야함
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            this.startActivity(intent)
        }
        // 삭제하기 버튼
        deleteButton.setOnClickListener { view->
            // 삭제 시 파이어베이스에서 현재 내용을 전부 다 delete
            val intent = Intent(this, Parent_missionList::class.java)
            intent.putExtra("selectedString", text)
            intent.putExtra("who",who)
            this.startActivity(intent)
        }

    }

}
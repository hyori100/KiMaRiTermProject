package com.example.kimali.Mission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.kimali.HBaseAdapter
import com.example.kimali.R
import com.example.kimali.child_listview_activity
import kotlinx.android.synthetic.main.activity_parent_mission_list.*
import java.util.*

class Parent_missionList : AppCompatActivity() {

    lateinit var userId : String
    lateinit var who : String
    lateinit var name : String
    lateinit var topic: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_parent_mission_list)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")

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
            val intent = Intent(this, parent_setting_add_activity::class.java)
            intent.putExtra("who",who)
            intent.putExtra("name",name)
            intent.putExtra("id", userId)
            intent.putExtra("topic", topic)

            //intent.putExtra("selectedString", selectedItem)
            this.startActivity(intent)
        }


            /* "nameKey"라는 이름의 key에 저장된 값이 있다면
               textView의 내용을 "nameKey" key에서 꺼내온 값으로 바꾼다 */

        if(who.equals("보호자")){
            addButton.setEnabled(true);
            addButton.setVisibility(Button.VISIBLE);
        }else {
            addButton.setEnabled(false);
            addButton.setVisibility(Button.INVISIBLE);
        }

        //이 배열에는 하나의 자식에 추가된 미션들의 이름이 들어온다.
        val array: Array<String> = arrayOf("청소기돌리기","설거지하기","빨래하기")

        //이 배열에는 미션에 해당하는 현재 날짜로부터의 디데이를 가지고온다.
        val array2: Array<Int> = arrayOf(5000,3000,7000)


        //baseAdapter로 생성
        mission_list.adapter =
            HBaseAdapter(this, array, array2)
        mission_list.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Toast.makeText(this, "Clicked item :"+" "+selectedItem, Toast.LENGTH_SHORT).show()
            if(who=="보호자") {
                val intent = Intent(this, parent_listview_activity::class.java)
                intent.putExtra("missionName",selectedItem)
                intent.putExtra("who", who)
                intent.putExtra("id", userId)
                intent.putExtra("name", name)
                intent.putExtra("topic", topic)
                intent.putExtra("position", position)

                this.startActivity(intent)
            }
            else {
                val intent = Intent(this, child_listview_activity::class.java)
                intent.putExtra("who", who)
                intent.putExtra("id", userId)
                intent.putExtra("name", name)
                intent.putExtra("topic", topic)
                intent.putExtra("position", position)
                this.startActivity(intent)
            }
        }



    }


}

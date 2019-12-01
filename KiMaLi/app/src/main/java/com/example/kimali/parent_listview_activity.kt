package com.example.kimali

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class parent_listview_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_parent_listview)

        if (intent.hasExtra("selectedString")) {
            val text = intent.getStringExtra("selectedString")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        val textView = findViewById(R.id.textView3) as TextView
        textView.setText("수학의 정석 집합단원 풀기                   보상 : 1000원")
    }

    override fun onBackPressed() {
        val intent = Intent(this, Parent_missionList::class.java)
        intent.putExtra("selectedString", text)
        this.startActivity(intent)
    }
}
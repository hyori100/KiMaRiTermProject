package com.example.kimali.compensation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.kimali.R
import com.example.kimali.text

class compensation_pc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compensation_pc)

        if (intent.hasExtra("selectedString")) {
            text = intent.getStringExtra("selectedString")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

        var pc : Int = 9  // 이곳에 데이터베이스에 다 더해진 pc 사용 시간을 가지고 와야함

        var pc_text = findViewById(R.id.pc_text) as TextView
        var use_pc = findViewById(R.id.use_pc) as EditText
        var use_pc_btn = findViewById(R.id.use_pc_btn) as Button
        var confirm_pc_btn = findViewById(R.id.confirm_pc_btn) as Button

        pc_text.setText("pc 사용 가능 시간 : " + pc + " 시간")

        use_pc_btn.setOnClickListener {
            var i : String = use_pc.text.toString();
            Toast.makeText(this, i, Toast.LENGTH_SHORT).show()
        }

        confirm_pc_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("selectedString", text)
            this.startActivity(intent)
        }
    }
}

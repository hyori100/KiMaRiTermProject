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

class compensation_money : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compensation_money)

        if (intent.hasExtra("selectedString")) {
            text = intent.getStringExtra("selectedString")
            setTitle(text)
        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }


        var money : Int = 1000  // 이곳에 데이터베이스에 다 더해진 돈을 가지고 와야함

        var money_text = findViewById(R.id.money_text) as TextView
        var use_money = findViewById(R.id.use_money) as EditText
        var use_money_btn = findViewById(R.id.use_money_btn) as Button
        var confirm_money_btn = findViewById(R.id.confirm_money_btn) as Button

        money_text.setText("총 보상 금액 : " + money + " 원")

        use_money_btn.setOnClickListener {
            var i : String = use_money.text.toString();
            Toast.makeText(this, i, Toast.LENGTH_SHORT).show()
        }

        confirm_money_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("selectedString", text)
            this.startActivity(intent)
        }
    }
}

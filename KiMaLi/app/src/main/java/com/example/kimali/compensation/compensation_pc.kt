package com.example.kimali.compensation

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kimali.R
import kotlinx.android.synthetic.main.activity_compensation_money.*
import kotlinx.android.synthetic.main.activity_compensation_pc.*



class compensation_pc : AppCompatActivity() {
    var pc : Int = 0
    lateinit var userId: String
    lateinit var who: String
    lateinit var name: String
    lateinit var topic: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compensation_pc)

        userId = intent.getStringExtra("id")
        who = intent.getStringExtra("who")
        name = intent.getStringExtra("name")
        topic = intent.getStringExtra("topic")
        setTitle(name)

        if(who == "보호자"){
            use_pc.setEnabled(false);
            use_pc.setVisibility(Button.INVISIBLE);
            use_pc_btn.setEnabled(false);
            use_pc_btn.setVisibility(Button.INVISIBLE);
        }else {
            use_pc.setEnabled(true);
            use_pc.setVisibility(Button.VISIBLE);
            use_pc_btn.setEnabled(true);
            use_pc_btn.setVisibility(Button.VISIBLE);
        }

         pc = 9  // 이곳에 데이터베이스에 다 더해진 pc 사용 시간을 가지고 와야함

        var pc_text = findViewById(R.id.pc_text) as TextView
        var use_pc = findViewById(R.id.use_pc) as EditText
        var use_pc_btn = findViewById(R.id.use_pc_btn) as Button
        var confirm_pc_btn = findViewById(R.id.confirm_pc_btn) as Button

        pc_text.setText("pc 사용 가능 시간 : " + pc + " 시간")


        use_pc_btn.setOnClickListener {
            var i : Int
            if(!TextUtils.isEmpty(use_pc.text.toString())) {
                i = Integer.parseInt(use_pc.text.toString())
            }
            else
                i = 0
            use_pc(i)
        }

        confirm_pc_btn.setOnClickListener {
            val intent = Intent(this, compensation_firstActivity::class.java)
            intent.putExtra("who",who)
            intent.putExtra("id", userId)
            intent.putExtra("name", name)
            intent.putExtra("topic", topic)
            this.startActivity(intent)
        }
    }

    fun use_pc(i : Int) {
        if (i == 0) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            //tilte 부분 xml
            builder.setTitle("알림")
            builder.setMessage("시간을 입력해주세요")
            //확인버튼
            builder.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                })
            builder.show()
        } else if (pc >= i) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            //tilte 부분 xml
            builder.setTitle("알림")
            builder.setMessage("총 사용시간에서 " + i + " 시간 사용하시겠습니까?")
            //확인버튼
            builder.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                    pc = pc - i  // 여기서 다시 파이어베이스에 저장해줘야함
                    pc_text.setText("pc 사용 가능 시간 : " + pc + " 시간")
                    use_pc.setText("")
                })

            builder.setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, which -> })
            builder.show()
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            //tilte 부분 xml
            builder.setTitle("알림")
            builder.setMessage("입력한 시간이 총 시간보다 많습니다.")
            //확인버튼
            builder.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                    use_pc.setText("")
                })
            builder.show()
        }
    }
}

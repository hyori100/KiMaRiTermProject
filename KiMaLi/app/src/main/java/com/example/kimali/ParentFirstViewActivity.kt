package com.example.kimali

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ParentFirstViewActivity : AppCompatActivity() {
    val array: Array<String> = arrayOf("아들1","아들2","아들3","딸1","딸2")
    private lateinit var mDatabase: DatabaseReference
    lateinit var who : String
    var okay: Int = 0
    lateinit var login_id_list: ArrayList<String>
    lateinit var name_list: ArrayList<String>
    lateinit var child_id: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_first_view)
        mDatabase = FirebaseDatabase.getInstance().reference
        val intent: Intent = getIntent()
        val who = intent.getStringExtra("who")
        val loginId = intent.getStringExtra("id")
        login_id_list = ArrayList()
        name_list = ArrayList()

        val listview = findViewById(R.id.child_list) as ListView
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)

        listview.adapter = adapter


    }

    fun add_child_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        val inflater: LayoutInflater = layoutInflater
        val view = inflater.inflate(R.layout.child_add_dialog, null)
        builder.setView(view)
        val child_id_edit = view.findViewById<EditText>(R.id.child_id_text)


        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->

                child_id = child_id_edit.text.toString()
                okay = 0
                mDatabase.child("users").child("자녀").addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) { // Get user value
                            for (messageData in dataSnapshot.getChildren()) {
                                var login_id = messageData.key.toString()
                                Log.d("sangmin", login_id)
                                login_id_list.add(login_id)
                            }
                            for (login_id in login_id_list) {
                                if (login_id == child_id) {
                                    okay = 1
                                    id_correct_dialog()
                                    break
                                }
                            }
                            if (okay == 0) {
                                id_wrong_dialog()
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {}

                    })
            })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()

    }

    fun id_wrong_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("경고")
        builder.setMessage("자녀의 아이디가 일치하지 않습니다.");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

    fun id_correct_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("알림")
        builder.setMessage(child_id+"님을 자녀로 추가하시겠습니까?");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->

                mDatabase.child("users").child("자녀").child(child_id).child("nameText").addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) { // Get user value
                            //firebase에서 user-pw 가져온다
                            var name = dataSnapshot.value.toString()

                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            })

        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_child, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                add_child_dialog()

            }
            R.id.delete -> {


            }
            R.id.modify -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}

package com.example.kimali.Parent_first_view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem

import android.widget.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kimali.BridgeActivity
import com.example.kimali.Login.Child
import com.example.kimali.Login.User
import com.example.kimali.Parent_missionList
import com.example.kimali.R
import com.example.kimali.compensation.compensation_firstActivity
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class ParentFirstViewActivity : AppCompatActivity() {
    lateinit var array: ArrayList<String>
    private lateinit var mDatabase: DatabaseReference
    lateinit var who : String
    var okay: Int = 0
    lateinit var login_id_list: ArrayList<String>
    lateinit var name_list: ArrayList<String>
    lateinit var child_id: String
    lateinit var name: String
    lateinit var loginId: String
    lateinit var topic: String
    lateinit var adapter: ArrayAdapter<String>
    lateinit var button: Button
    lateinit var selectItem : String
     var menu_check_position : Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_first_view)
        mDatabase = FirebaseDatabase.getInstance().reference
        val intent: Intent = getIntent()
        val who = intent.getStringExtra("who")
        loginId = intent.getStringExtra("id")
        login_id_list = ArrayList()
        name_list = ArrayList()
        array = ArrayList()
        // 수정완료 버튼
        button = findViewById(R.id.fix_button) as Button
        button.setEnabled(false);
        button.setVisibility(Button.INVISIBLE);

        button.setOnClickListener {
            menu_check_position = 0
            button.setEnabled(false);
            button.setVisibility(Button.INVISIBLE);
        }



        val listview = findViewById(R.id.child_list) as ListView
        adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<String>())

        mDatabase.child("users").child(who).child(loginId).child("children").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // Get user value
                    //firebase에서 user-id 전부 가져온다
                    adapter.clear()
                    for (messageData in dataSnapshot.getChildren()){
                        var child = messageData.key.toString()
                        Log.d("sangmin", child)
                        name_list.add(child)
                        adapter.add(child)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })

        listview.adapter = adapter

        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            selectItem = parent.getItemAtPosition(position) as String
            if(menu_check_position == 0) {
                val intent = Intent(this, BridgeActivity::class.java)
                intent.putExtra("selectedString", selectItem)
                this.startActivity(intent)
            }
            else if(menu_check_position == 1) {
                child_remove()
            }
        }




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
                            topic = dataSnapshot.child(child_id).child("topic").value.toString()
                            name = dataSnapshot.child(child_id).child("nameText").value.toString()
                            Log.d("sangmee", name)
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
        builder.setMessage(name+"님을 추가하시겠습니까?");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->

                writeChild()

                adapter.add(name)
                adapter.notifyDataSetChanged()
            })

        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }

    // 자식 지울지 물어보는 알림창
    fun child_remove() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("알림")
        builder.setMessage(selectItem+"님을 삭제하시겠습니까?");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->

                /// 이부분에 데이터베이스에서도 지워야함
                adapter.remove(selectItem)
                adapter.notifyDataSetChanged()
            })

        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        builder.show()
    }


    // 자녀를 삭제하기 위한 다이얼로그 창
    fun child_delete_dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        //tilte 부분 xml
        builder.setTitle("알림")
        builder.setMessage("삭제할 자녀의 이름을 눌러주세요.");

        //확인버튼
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which ->
                menu_check_position = 1;
                button.setEnabled(true);
                button.setVisibility(Button.VISIBLE);
            })
        builder.show()
    }

    //메뉴
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
                child_delete_dialog()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun writeChild() {
        val post = ChildTopic(topic)
        val postValues = post.toMap()
        val childUpdates = HashMap<String, Any>()
        childUpdates["/users/보호자/$loginId/children/$name"] = postValues

        mDatabase.updateChildren(childUpdates)
    }
}

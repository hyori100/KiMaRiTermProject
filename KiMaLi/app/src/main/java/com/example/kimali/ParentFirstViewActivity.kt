package com.example.kimali

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ParentFirstViewActivity : AppCompatActivity() {
    val array: Array<String> = arrayOf("아들1","아들2","아들3","딸1","딸2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_first_view)

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
            DialogInterface.OnClickListener { dialog, which -> })
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

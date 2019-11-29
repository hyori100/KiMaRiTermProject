package com.example.kimali

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_child, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {


            }
            R.id.delete -> {


            }
            R.id.modify -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}

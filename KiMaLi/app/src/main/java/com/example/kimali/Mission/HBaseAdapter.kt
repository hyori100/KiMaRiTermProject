package com.example.kimali.Mission

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kimali.R

/**
 * Created by hong on 18. 1. 22.
 */

class HBaseAdapter(context: Context,item : ArrayList<String>, item2 : ArrayList<Int>) : BaseAdapter(){
    private val mContext = context
    private val mItem = item
    private val mItem2 = item2
    //test
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var viewHolder : ViewHolder
        var view = convertView
        if (view == null){
            viewHolder = ViewHolder()
            view = LayoutInflater.from(mContext).inflate(R.layout.mission,parent,false)
            viewHolder.textView2 = view.findViewById(R.id.textView2)
            viewHolder.textView = view.findViewById(R.id.textView)
            view.tag = viewHolder
            viewHolder.textView.text = mItem.get(position)
            viewHolder.textView2.text = mItem2.get(position).toString()
            return view
        }else{
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.textView.text = mItem[position]
        return  view
    }


    override fun getItem(position: Int) = mItem[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = mItem.size

    inner class ViewHolder{
        lateinit var textView : TextView
        lateinit var textView2 : TextView
    }
}
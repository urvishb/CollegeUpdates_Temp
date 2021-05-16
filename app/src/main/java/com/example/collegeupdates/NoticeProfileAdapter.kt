package com.example.collegeupdates

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeupdates.models.Notice
import kotlinx.android.synthetic.main.single_row_design_events.view.*


class NoticeProfileAdapter (val context: Context, val notice: List<Notice>) :
    RecyclerView.Adapter<NoticeProfileAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_row_design_news, parent, false)
        return ViewHolder(view)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notice[position])
    }

    override fun getItemCount() = notice.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(notice: Notice)
        {
            itemView.myheader.text = notice.location
            Glide.with(context).load(notice.imageUrl).into(itemView.rvimage)
            itemView.mydesc.text = DateUtils.getRelativeTimeSpanString(notice.creationTimeMs)
        }
    }


}
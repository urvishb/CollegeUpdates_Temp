package com.example.collegeupdates

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeupdates.models.Notice
import kotlinx.android.synthetic.main.single_row_design_events.view.*
import kotlinx.android.synthetic.main.single_row_design_news.view.*
import kotlinx.android.synthetic.main.single_row_design_news.view.mydesc
import kotlinx.android.synthetic.main.single_row_design_news.view.myheader
import kotlinx.android.synthetic.main.single_row_design_news.view.rvimage
import java.util.ArrayList

class newsadapter(val context : Context, val notices : List<Notice>) : RecyclerView.Adapter<newsadapter.Myviewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_row_design_news, parent, false)
        return Myviewholder(view)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.bind(notices[position])
    }

    override fun getItemCount() = notices.size

    inner class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(notice: Notice) {
            itemView.myheader.text = notice.location
            Glide.with(context).load(notice.imageUrl).into(itemView.rvimage)
            itemView.mydesc.text = DateUtils.getRelativeTimeSpanString(notice.creationTimeMs) // this gives us the format of time like "42 min ago"

        }


    }
}
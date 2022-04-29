package com.example.collegeupdates

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeupdates.models.Post
import kotlinx.android.synthetic.main.single_row_design_events.view.*

class PostsProfileAdapter (val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<PostsProfileAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_row_design_events_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }



    override fun getItemCount() = posts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(post: Post)
        {
            itemView.myheader.text = post.location
            Glide.with(context).load(post.imageUrl).into(itemView.rvimage)
            itemView.mydesc.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)
        }
    }


}
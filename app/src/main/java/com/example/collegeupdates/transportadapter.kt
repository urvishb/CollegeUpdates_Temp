package com.example.collegeupdates

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeupdates.models.Buses
import com.example.collegeupdates.models.Notice
import kotlinx.android.synthetic.main.fragment_transport.*
import kotlinx.android.synthetic.main.fragment_transport.view.*
import kotlinx.android.synthetic.main.single_row_design_transport.*
import kotlinx.android.synthetic.main.single_row_design_transport.view.*
import java.util.ArrayList

class transportadapter(val context : Context, private val busList : List<Buses>) : RecyclerView.Adapter<transportadapter.Myviewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row_design_transport, parent, false)
        return Myviewholder(view)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.img.setImageResource(R.drawable.buslogo)
        holder.header.text = busList[position].busName
        holder.desc.text = busList[position].route
        holder.expandableLayout.mydescExpanded.text = busList[position].route



        val isExpandable : Boolean = busList[position].expandable
        holder.expandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.desc.visibility = if (isExpandable) View.GONE else View.VISIBLE

        holder.desc.setSingleLine()
        holder.desc.isSelected = true


        holder.itemView.setOnClickListener {
            val fullDescription = busList[position]
            fullDescription.expandable = !fullDescription.expandable
            notifyItemChanged(position)
        }



    }

    override fun getItemCount(): Int {
    return busList.size
    }

    inner class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.rvimage)
        val header: TextView = itemView.findViewById(R.id.myheader)
        val desc: TextView = itemView.findViewById(R.id.mydesc)
        val expandableLayout : RelativeLayout = itemView.findViewById(R.id.expandable_layout)

    }





}
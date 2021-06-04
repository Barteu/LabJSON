package com.example.labjson.commentInfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labjson.R


class commentInfoAdapter(
    private val comments: MutableList<commentInfo>
) : RecyclerView.Adapter<commentInfoAdapter.commentViewHolder>() {

    class commentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val user = itemView.findViewById<TextView>(R.id.textAuthorC)
        val title = itemView.findViewById<TextView>(R.id.textTitleC)
        val body = itemView.findViewById<TextView>(R.id.textBodyC)

        fun bind(curComment: commentInfo){
            user.text = curComment.email
            title.text = curComment.name
            body.text =curComment.body

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): commentViewHolder {
        return commentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_info, parent, false)
        )
    }


    override fun onBindViewHolder(holder: commentViewHolder, position: Int) {
        val curComment = comments[position]
        holder.bind(curComment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }



}

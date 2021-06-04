package com.example.labjson.postInfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labjson.R
import com.example.labjson.userInfo.userInfoAdapter


class postInfoAdapter(
    private val posts: MutableList<postInfo>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<postInfoAdapter.postViewHolder>() {

    inner class postViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        val title = itemView.findViewById<TextView>(R.id.textPostTitle)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }

        }


        fun bind(curPost: postInfo){
            title.text = curPost.title

            if(curPost.id.toInt()%2==0)
            {
                itemView.setBackgroundColor(Color.parseColor("#fbf2ff"))
            }
            else
            {
                itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postViewHolder {
        return postViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_info, parent, false)
        )
    }

    fun addResult(info: postInfo) {
        posts.add(info)
        notifyItemInserted(posts.size - 1)
    }

    override fun onBindViewHolder(holder: postViewHolder, position: Int) {
        val curPost = posts[position]
        holder.bind(curPost)

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }




}

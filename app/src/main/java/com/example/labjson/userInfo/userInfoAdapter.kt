package com.example.labjson.userInfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labjson.R
import com.example.labjson.userInfo.userInfoAdapter


class userInfoAdapter(
    private val users: MutableList<userInfo>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<userInfoAdapter.userViewHolder>() {

    inner class userViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        val name = itemView.findViewById<TextView>(R.id.textName)
        val surname = itemView.findViewById<TextView>(R.id.textSurname)
        val email = itemView.findViewById<TextView>(R.id.textEmail)
        val todos = itemView.findViewById<TextView>(R.id.textToDoNum)
        val posts = itemView.findViewById<TextView>(R.id.textPostsNum)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBarUser)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
                progressBar.setVisibility(View.VISIBLE)

            }

        }


        fun bind(curUser: userInfo){
            name.text = curUser.name.split(" ")[0]
            surname.text = curUser.name.split(" ")[1]
            email.text = curUser.email
            todos.text = curUser.todos.toString() + " / " +curUser.all_todos.toString()
            posts.text = curUser.posts.toString()

            if(curUser.load==0)
            {
                progressBar.setVisibility(View.GONE)
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE)
            }

            if(curUser.id%2==0)
            {
                itemView.setBackgroundColor(Color.parseColor("#fbf2ff"))
            }
            else
            {
                itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            }


        }

        fun hide(curUser:userInfo)
        {
            progressBar.setVisibility(View.GONE)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        return userViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_info, parent, false)
        )
    }

    fun addResult(info: userInfo) {
        users.add(info)
        notifyItemInserted(users.size - 1)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val curUser = users[position]
        holder.bind(curUser)

    }

    override fun getItemCount(): Int {
        return users.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun doIt(pos : Int,holder: userViewHolder)
    {
        val curUser = users[pos]
        holder.hide(curUser)

    }


}

package com.example.labjson.todoInfo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labjson.R


class toDoInfoAdapter(
    private val todos: MutableList<toDoInfo>
) : RecyclerView.Adapter<toDoInfoAdapter.todoViewHolder>() {

    class todoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = itemView.findViewById<TextView>(R.id.textToDoID)
        val title = itemView.findViewById<TextView>(R.id.textToDoTitle)
        val completed = itemView.findViewById<TextView>(R.id.textCompleted)

        fun bind(curToDo: toDoInfo){
            id.text = curToDo.id
            title.text = curToDo.title
            if(curToDo.completed=="true")
            {
                completed.text = "TAK"
                completed.setTextColor(Color.parseColor("#00c75a"))
            }
            else
            {
                completed.text = "NIE"
                completed.setTextColor(Color.parseColor("#c72400"))
            }




            if(curToDo.id.toInt()%2==0)
            {
                this.itemView.setBackgroundColor(Color.parseColor("#fbf2ff"))
            }
            else
            {
                this.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): todoViewHolder {
        return todoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo_info, parent, false)
        )
    }


    override fun onBindViewHolder(holder: todoViewHolder, position: Int) {
        val curToDo = todos[position]
        holder.bind(curToDo)

    }

    override fun getItemCount(): Int {
        return todos.size
    }



}

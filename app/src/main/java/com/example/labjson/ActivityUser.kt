package com.example.labjson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labjson.postInfo.postInfo
import com.example.labjson.postInfo.postInfoAdapter
import com.example.labjson.todoInfo.toDoInfo
import com.example.labjson.todoInfo.toDoInfoAdapter
import com.example.labjson.userInfo.userInfo
import com.example.labjson.userInfo.userInfoAdapter
import info.androidhive.fontawesome.FontTextView
import org.json.JSONArray
import java.net.URL

private lateinit var todoInfoAdapter: toDoInfoAdapter
private lateinit var postInfoAdapter: postInfoAdapter

class ActivityUser : AppCompatActivity(), postInfoAdapter.OnItemClickListener {

    lateinit var postsJS : String
    lateinit var todosJS: String
    lateinit var listToDos : MutableList<toDoInfo>
    lateinit var listPosts : MutableList<postInfo>
    lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        supportActionBar?.hide()

        val textUser = findViewById<TextView>(R.id.textFullName)

        userName = intent.getStringExtra("EXTRA_USER_NAME").toString()
        textUser.text = userName
        postsJS = intent.getStringExtra("EXTRA_POSTS").toString()
        todosJS = intent.getStringExtra("EXTRA_TODOS").toString()



        var listToDosTmp = ArrayList<toDoInfo>()
        var listPostsTmp = ArrayList<postInfo>()

        val arrow = findViewById<FontTextView>(R.id.txtLeftArrow) as TextView

        arrow.setOnClickListener{
            this.finish()
        }


        var postsSet : Boolean = false
        var todosSet : Boolean = false

        Thread(){
            run{
                if(postsJS!="") {
                    val posts = JSONArray(postsJS)


                    for (i in 0 until posts.length()) {
                        var post = posts.getJSONObject(i)

                        val title = post.getString("title")
                        val id = post.getInt("id")
                        val body = post.getString("body")

                        val postObj = postInfo()

                        postObj.id = id.toString()
                        postObj.title = title
                        postObj.body = body

                        listPostsTmp.add(postObj)

                    }
                }
                listPosts = listPostsTmp
                postsSet=true

            }
        }.start()

        Thread(){
                run{

                    if(todosJS!="") {
                        val todos = JSONArray(todosJS)

                        for (i in 0 until todos.length()) {
                            var todo = todos.getJSONObject(i)

                            val title = todo.getString("title")
                            val completed = todo.getBoolean("completed")

                            val todoObj = toDoInfo()
                            todoObj.id = (i+1).toString()
                            todoObj.title = title
                            todoObj.completed =completed.toString()
                            listToDosTmp.add(todoObj)
                        }
                    }
                    listToDos= listToDosTmp
                    todosSet=true
                }
        }.start()


        while(!todosSet or !postsSet)
        {
            Thread.sleep(10)
        }



        if(listToDos.size>0)
        {
            todoInfoAdapter = toDoInfoAdapter(listToDos)
        }
        else
        {
            todoInfoAdapter = toDoInfoAdapter(mutableListOf())
        }

        if(listPosts.size>0)
        {
             postInfoAdapter = postInfoAdapter(listPosts,this)
        }
        else
        {
            postInfoAdapter = postInfoAdapter(mutableListOf(),this)
        }


        val todoList = findViewById<RecyclerView>(R.id.rvTodos)  // zmienna z RecyclerView
        todoList.adapter = todoInfoAdapter   // nadpisujemy adapter z Recyclera
        todoList.layoutManager = LinearLayoutManager(this)

        val postList = findViewById<RecyclerView>(R.id.rvPosts)  // zmienna z RecyclerView
        postList.adapter = postInfoAdapter   // nadpisujemy adapter z Recyclera
        postList.layoutManager = LinearLayoutManager(this)


    }


    override fun onItemClick(position: Int) {


        if(listPosts.size>position)
        {

            val clickedItem : postInfo = listPosts[position]

            val id = clickedItem.id
            val title = clickedItem.title
            val body = clickedItem.body

            val thread = Thread(){
                run{

                    val intent = Intent(this, PostActivity::class.java)
                    intent.putExtra("EXTRA_USER_NAME",  userName)
                    intent.putExtra("EXTRA_POST_ID",id)
                    intent.putExtra("EXTRA_TITLE",title)
                    intent.putExtra("EXTRA_BODY",body)

                    startActivity(intent)
                }
                runOnUiThread(){

                    this.onPause()
                }
            }
            thread.start()

        }

    }

}
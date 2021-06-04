package com.example.labjson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labjson.DB.DBHelper
import com.example.labjson.userInfo.userInfo
import com.example.labjson.userInfo.userInfoAdapter
import org.json.JSONArray
import java.net.URL

private lateinit var userInfoAdapter: userInfoAdapter

class MainActivity : AppCompatActivity(), userInfoAdapter.OnItemClickListener {

    internal lateinit var db : DBHelper
    lateinit var listUsers : MutableList<userInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()


        db = DBHelper(this)
        listUsers = db.allUsers

        if(listUsers.size>0)
        {
            userInfoAdapter = userInfoAdapter(listUsers,this)
        }
        else
        {
            userInfoAdapter = userInfoAdapter(mutableListOf(),this)
        }

        val userList = findViewById<RecyclerView>(R.id.RVuserInfo)  // zmienna z RecyclerView
        userList.adapter = userInfoAdapter   // nadpisujemy adapter z Recyclera
        userList.layoutManager = LinearLayoutManager(this)
        db.close()


    }

    override fun onResume() {
        super.onResume()
        updateNumbers()

        }

     fun updateNumbers()
     {
         var postsUpdated = false
         var todosUpdated = false


         Thread() {
             run {

                 for( user in listUsers )
                 {
                     val url="https://jsonplaceholder.typicode.com/users/"+ user.id.toString()+ "/posts"
                     val body = URL(url).readText()

                     if(body!="") {
                         val posts =  JSONArray(body)
                         user.posts = posts.length()
                     }

                 }
                 postsUpdated= true
             }
         }.start()

         Thread(){
             run{

                 for( user in listUsers )
                 {
                     val url="https://jsonplaceholder.typicode.com/users/"+ user.id.toString()+ "/todos"
                     val body = URL(url).readText()
                     var completed = 0

                     if(body!="") {
                         val todos =  JSONArray(body)
                         user.all_todos = todos.length()

                         for(i in 0 until todos.length())
                         {
                             var todo = todos.getJSONObject(i)
                             if(todo.getString("completed")=="true")
                             {
                                 completed+=1
                             }
                         }
                         user.todos = completed
                     }
                 }
                 todosUpdated = true
             }
         }.start()

         while(!todosUpdated or !postsUpdated)
         {
             Thread.sleep(10)
         }


     }

    override fun onItemClick(position: Int) {


        if(listUsers.size>position)
        {

            val clickedItem : userInfo = listUsers[position]
            listUsers[position].load=1
            val id = clickedItem.id.toString()

            Thread(){
            run{

                val posts = URL("https://jsonplaceholder.typicode.com/users/"+id+"/posts").readText()
                val todos = URL("https://jsonplaceholder.typicode.com/users/"+id+"/todos").readText()

                val intent = Intent(this, ActivityUser::class.java)
                intent.putExtra("EXTRA_USER_ID",id)
                intent.putExtra("EXTRA_USER_NAME",clickedItem.name.toString())
                intent.putExtra("EXTRA_POSTS",posts)
                intent.putExtra("EXTRA_TODOS",todos)


                startActivity(intent)
            }
            runOnUiThread(){
                listUsers[position].load=0
                userInfoAdapter.notifyItemChanged(position)
                this.onPause()
            }
        }.start()



    }



    }
}

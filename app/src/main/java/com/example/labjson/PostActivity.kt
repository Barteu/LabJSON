package com.example.labjson

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labjson.commentInfo.commentInfo
import com.example.labjson.commentInfo.commentInfoAdapter
import com.example.labjson.postInfo.postInfo
import com.example.labjson.postInfo.postInfoAdapter
import info.androidhive.fontawesome.FontTextView
import org.json.JSONArray
import java.net.URL
import kotlin.concurrent.thread


private lateinit var commentInfoAdapter: commentInfoAdapter

class PostActivity : AppCompatActivity() {

    lateinit var postId: String
    lateinit var listComments : MutableList<commentInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        supportActionBar?.hide()

        val arrow = findViewById<FontTextView>(R.id.txtArrow2) as TextView
        val userName = findViewById<TextView>(R.id.textAuthor)
        val title = findViewById<TextView>(R.id.textPostTitle2)
        val postBody = findViewById<TextView>(R.id.textBody)
        val users = findViewById<FontTextView>(R.id.txtUsers) as TextView

        arrow.setOnClickListener{
            this.finish()
        }

        users.setOnClickListener{
            val thread = Thread(){
                run{
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                runOnUiThread(){
                    this.finish()
                }

            }
            thread.start()
        }

        userName.text = intent.getStringExtra("EXTRA_USER_NAME").toString()
        title.text = intent.getStringExtra("EXTRA_TITLE").toString()
        postBody.text = intent.getStringExtra("EXTRA_BODY").toString()
        postId=intent.getStringExtra("EXTRA_POST_ID").toString()


    }

    override fun onStart() {
        super.onStart()
        val progressBar = findViewById<ProgressBar>(R.id.progressBarLC)
        progressBar.setVisibility(View.VISIBLE)

        var listCommentsTmp = ArrayList<commentInfo>()
        val thread = Thread(){
            run{
                val url="https://jsonplaceholder.typicode.com/posts/"+postId+"/comments"
                val body = URL(url).readText()

                if(body!="")
                {
                    val comments =  JSONArray(body)


                    for(i in 0 until comments.length())
                    {
                        var com = comments.getJSONObject(i)

                        val comObj = commentInfo()
                        comObj.email= com.getString("email")
                        comObj.name= com.getString("name")
                        comObj.body= com.getString("body")

                        listCommentsTmp.add(comObj)
                    }
                }

            }
            runOnUiThread()
            {
                listComments =  listCommentsTmp

                if(listComments.size>0)
                {
                    commentInfoAdapter = commentInfoAdapter(listComments)
                }
                else
                {
                    commentInfoAdapter = commentInfoAdapter(mutableListOf())
                }
                progressBar.setVisibility(View.GONE)
                val commentList = findViewById<RecyclerView>(R.id.rvComments)  // zmienna z RecyclerView
                commentList.adapter = commentInfoAdapter   // nadpisujemy adapter z Recyclera
                commentList.layoutManager = LinearLayoutManager(this)
            }
        }
        thread.start()

    }



}


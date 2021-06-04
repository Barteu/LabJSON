package com.example.labjson.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.example.labjson.userInfo.userInfo

//import com.example.gralos.Modal.Message

class DBHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 8
        private val DATABASE_NAME = "EDMTDB2.db"
        //Table users
        private val TABLE_NAME_U = "Users"
        private val COL_ID_U = "id"
        private val COL_NAME_U = "name"
        private val COL_EMAIL_U = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {


        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME_U ($COL_ID_U INTEGER, $COL_NAME_U TEXT, $COL_EMAIL_U TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_U")


        onCreate(db!!)
    }

    //CRUD
    val allUsers:MutableList<userInfo>
        get(){
            val listUser = ArrayList<userInfo>()
            val selectQuery = "SELECT * FROM $TABLE_NAME_U"
            val db = this.writableDatabase
            val cursor =  db.rawQuery(selectQuery, null)
            if(cursor.moveToFirst()){
                do {
                    val user = userInfo()
                    user.id = cursor.getInt(cursor.getColumnIndex(COL_ID_U))
                    user.name = cursor.getString(cursor.getColumnIndex(COL_NAME_U))
                    user.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL_U))
                    listUser.add(user)

                } while (cursor.moveToNext())
            }
            db.close()
            return listUser
        }

    fun addUserToDB(id: Int, name: String, email:String){
        val db= this.writableDatabase
        val values = ContentValues()
        if(testOne(id.toString()))
        {
            values.put(COL_ID_U, id)
            values.put(COL_NAME_U, name)
            values.put(COL_EMAIL_U, email)
            db.insert(TABLE_NAME_U, null, values)
        }
        db.close()
    }


    fun testOne(key:String): Boolean {
        val selectQuery = "SELECT * FROM $TABLE_NAME_U WHERE $COL_ID_U = '$key';"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()){
            return false
        }

        return true
    }



}
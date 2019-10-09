package com.example.moal_alba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var arrayAdapter: ArrayAdapter<String>? = null

    var arrayIndex = ArrayList<String?>()
    var arrayData = ArrayList<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val myRef = FirebaseDatabase.getInstance()
        val database = myRef.getReference()

        var i: Int = 0

        fun writeNewUser(userId: String, name: String, email: String) {
            val user = User(name, email)
            database.child("users").child(userId).setValue(user)
            //database.child("users").child(userId).child("username").setValue(name)

        }

        btn_complete.setOnClickListener { view ->
            val input1 = id_text.text.toString()
            val input2 = pw_text.text.toString()


            writeNewUser("user" + i, input1, input2)
            i++
        }

        //수행하고 필드도 변화시키기 ->reload
        id_text.text.clear()  //변화
        pw_text.text.clear()



        button2.setOnClickListener {

            getFirebaseDatabase();
        }
    }



    fun getFirebaseDatabase() {
        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //Log.d("debug", "loadPost:onCancelled", databaseError.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                for (snapshot in dataSnapshot.children) {
                    //if (snapshot.key.equals("user0")) {

                    val key = snapshot.key
                    val get : User? = snapshot.getValue(User::class.java)
                    //val info = arrayOf<String?>(get?.ID, get?.EMAIL)

                    Log.d("jini", "user0 : " + get?.ID)
                    arrayIndex.add(key)
                }
                arrayAdapter?.notifyDataSetChanged()

            }
        }
        val sortbyAge = FirebaseDatabase.getInstance().reference.child("users").orderByChild("id")
        sortbyAge.addListenerForSingleValueEvent(postListener)
    }}

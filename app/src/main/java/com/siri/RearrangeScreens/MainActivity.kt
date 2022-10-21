package com.siri.RearrangeScreens

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

   private fun getFromDb() {
       db.collection("ScreenOrder")
           .document("order2")
           .get()
           .addOnSuccessListener { documentReference ->
               Log.d("documentReference data5", documentReference.data?.get("arr").toString())
               val screenPaths = documentReference.data?.get("arr") as ArrayList<Map<String,Int>>
               Global2.addListOfAllScreens(
                   arrayOf(
                       FirstActivity::class.java,
                       SecondActivity::class.java,
                       ThirdActivity::class.java,
                       FourthActivity::class.java
                   )
               )
               Global2.addOrder2(applicationContext, screenPaths)
               Global2.pageChanger(MainActivity@ this)
           }
           .addOnFailureListener { e ->
               Global2.addListOfAllScreens(
                   arrayOf(
                       FirstActivity::class.java,
                       SecondActivity::class.java,
                       ThirdActivity::class.java,
                       FourthActivity::class.java
                   )
               )
               Global2.pageChanger(MainActivity@ this)
               Toast.makeText(applicationContext, "Failed " + e.message, Toast.LENGTH_SHORT).show()
           }
    }

    override fun onResume() {
        super.onResume()
        db = FirebaseFirestore.getInstance()
        getFromDb()

    }


}

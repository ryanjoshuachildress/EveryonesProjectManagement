package com.ryanjoshuachildress.everyonesprojectmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView
    private lateinit var ivProfilePic: ImageView

    val auth = FirebaseAuth.getInstance().currentUser
    private val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        tvName = findViewById(R.id.tvName)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        ivProfilePic = findViewById(R.id.ivProfilePic)


        if(auth == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            fCreateUI()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        if(auth == null){
//            startActivity(Intent(this, LoginActivity::class.java))
//        }else{
//            fCreateUI()
//        }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       // return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mi_Logout -> {
                fLogout()
                return true
           }
            R.id.mi_Exit -> {
                fExit()
                return true
            }
            R.id.mi_show_login_logout_logs -> {
                startActivity(Intent(this, ViewLoginLogoutLogActivity::class.java))
                return true
            }
       }
        return super.onOptionsItemSelected(item)
    }

    private fun fLogout(){
        fWriteLogEntryToFirebase(getString(R.string.toast_sucessful_logout) + auth?.displayName)
        AuthUI.getInstance().signOut(this).addOnSuccessListener {
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, getString(R.string.toast_sucessful_logout), Toast.LENGTH_SHORT).show()
        }
    }

    private fun fExit(){
        fLogout()
        finishAffinity()
        this@MainActivity.finish()
        exitProcess(0)
    }

    private fun fCreateUI()
    {
        Toast.makeText(this, getString(R.string.toast_sucessful_login) + auth?.displayName, Toast.LENGTH_LONG).show()
        fWriteLogEntryToFirebase(getString(R.string.toast_sucessful_login) + auth?.displayName)
        auth?.let{
            tvName.text = auth.displayName
            tvEmail.text = auth.email
            tvPhone.text = auth.phoneNumber
            ivProfilePic.setImageURI(auth.photoUrl)
        }
    }

    private fun fWriteLogEntryToFirebase(sLogEntry: String){
        Toast.makeText(this,"Write into DB", Toast.LENGTH_LONG).show()
        val data = hashMapOf(
            "Time" to Calendar.getInstance().time,
            "User" to auth?.uid.toString(),
            "Entry" to sLogEntry
        )
        db.collection(getString(R.string.collection_login_logout_log)).document(System.currentTimeMillis().toString())
            .set(data, SetOptions.merge())
            .addOnSuccessListener{  }
            .addOnFailureListener{ Log.e(TAG,"Error Writing to database") }
    }

}
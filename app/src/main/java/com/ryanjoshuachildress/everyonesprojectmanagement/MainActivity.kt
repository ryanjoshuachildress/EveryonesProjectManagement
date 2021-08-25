package com.ryanjoshuachildress.everyonesprojectmanagement

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var btnLogout: Button

    val auth = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        tvName = findViewById(R.id.tvName)
        btnLogout = findViewById(R.id.btnLogout)


        if(auth == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            tvName.text = auth.displayName
        }

        btnLogout.setOnClickListener(){
            fLogout()
        }
    }

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
       }
        return super.onOptionsItemSelected(item)
    }

    private fun fLogout(){
        AuthUI.getInstance().signOut(this).addOnSuccessListener {
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, "Suscessful Logged Out", Toast.LENGTH_SHORT).show()
        }
    }

}
package com.ryanjoshuachildress.everyonesprojectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class UserProfileActivity : AppCompatActivity() {

    private lateinit var etUserID: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        etUserID = findViewById(R.id.etUserID)

        var userID: String? = null

        userID = intent.getSerializableExtra("UserID") as String

        etUserID.text = userID
    }
}
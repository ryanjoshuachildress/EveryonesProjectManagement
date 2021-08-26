package com.ryanjoshuachildress.everyonesprojectmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class ViewLoginLogoutLogActivity : AppCompatActivity() {

    private lateinit var rvViewLoginLogoutLog: RecyclerView
    private lateinit var logArrayList: ArrayList<Login_Logout_log>
    private lateinit var loginLogoutLogAdapter: LoginLogoutLogAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_login_logout_log)

        rvViewLoginLogoutLog = findViewById(R.id.rvViewLoginLogoutLog)
        rvViewLoginLogoutLog.layoutManager = LinearLayoutManager(this)
        rvViewLoginLogoutLog.setHasFixedSize(true)

        logArrayList = arrayListOf()

        loginLogoutLogAdapter = LoginLogoutLogAdapter(logArrayList)

        rvViewLoginLogoutLog.adapter = loginLogoutLogAdapter

        EventChangeListener()

    }

    private fun EventChangeListener() {

        db = FirebaseFirestore.getInstance()
        db.collection("login_logout_log").orderBy("Time",Query.Direction.DESCENDING).
                addSnapshotListener(object : EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error != null){
                            Log.e("Firestore Error", error.message.toString())
                            return
                        }
                        for(dc : DocumentChange in value?.documentChanges!!){

                            if(dc.type == DocumentChange.Type.ADDED){

                                logArrayList.add(dc.document.toObject(Login_Logout_log::class.java))
                            }
                        }

                        loginLogoutLogAdapter.notifyDataSetChanged()
                    }

                })
    }
}
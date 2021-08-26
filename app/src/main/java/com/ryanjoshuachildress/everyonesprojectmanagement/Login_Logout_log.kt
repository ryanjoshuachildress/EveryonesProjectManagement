package com.ryanjoshuachildress.everyonesprojectmanagement

import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import java.sql.Timestamp
import java.util.*

data class Login_Logout_log(var Entry: String ? = null, var Time: com.google.firebase.Timestamp ? = null,var User: String? = null)

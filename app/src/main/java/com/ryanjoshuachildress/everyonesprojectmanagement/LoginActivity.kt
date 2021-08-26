package com.ryanjoshuachildress.everyonesprojectmanagement


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createSignInIntent()
    }

    private fun createSignInIntent() {
        //Sets login providers for AuthUI
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        //Starts login UI
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.LoginUIStyle)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Gets result from AuthUI and starts Main ACtivity
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            var response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Log.d(TAG,user?.displayName.toString())
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }

            else{

                if(response == null){
                    finish()
                }
                if (response?.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, getString(R.string.toast_error_no_network)+ response.error?.errorCode.toString(), Toast.LENGTH_LONG).show()
                    return
                }

                if(response?.error?.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, getString(R.string.toast_error_unknown_error)+ response.error?.errorCode.toString(), Toast.LENGTH_LONG)
                        .show()
                    Log.d(TAG, response.error?.errorCode.toString())
                    return
                }
            }
        }
    }
}

package com.webdoc.Activities.LoginAndRegistration

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.webdoc.Activities.LoginAndRegistration.ViewModels.LoginViewModel
import com.webdoc.Activities.LoginAndRegistration.ViewModels.RegistrationViewModel
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
import com.webdoc.Essentials.PreferencesNew
import com.webdoc.ModelClasses.UserModel
import com.webdoc.theforum.R
import com.webdoc.theforum.databinding.ActivityLoginOptionsBinding
import java.util.*


class LoginOptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOptionsBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private var uid: String = ""
    private var email: String = ""
    private var name: String = ""
    private var profileImage: Uri? = null
    private var storageReference: StorageReference? = null
    private var firebaseStorage: FirebaseStorage? = null
    private var databaseReference: DatabaseReference? = null
    lateinit var prefs: SharedPreferences
    lateinit var edit: SharedPreferences.Editor
    var prefrenceKey: String = "abc"
    private lateinit var userPhoto: String
    private var phoneNo: String? = null
    private var loginViewModel: LoginViewModel? = null
    private var registrationViewModel: RegistrationViewModel? = null
    private var deviceToken: String = ""

    private companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observers()
        clickListerners()
    }

    private fun observers() {
        registrationViewModel!!.LDRegistration().observe(this) { response ->
            if (response != null) {
                if (response.result!!.responseCode.equals("0000")) {
                    Toast.makeText(this@LoginOptionsActivity, "Customer Added", Toast.LENGTH_SHORT)
                        .show()

                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.putString(PreferencesNew.KEY_USER_NAME, name)
                    edit.putString(PreferencesNew.KEY_USER_PHONE, phoneNo)
                    edit.putString(PreferencesNew.KEY_USER_EMAIL, email)
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, profileImage.toString())
                    edit.commit()
                    edit.apply()

                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@LoginOptionsActivity, GetNumberActivity::class.java))
                    finishAffinity()
                } else {
                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.putString(PreferencesNew.KEY_USER_NAME, name)
                    edit.putString(PreferencesNew.KEY_USER_PHONE, phoneNo)
                    edit.putString(PreferencesNew.KEY_USER_EMAIL, email)
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, profileImage.toString())
                    edit.putBoolean(PreferencesNew.KEY_IS_LOGIN, true)
                    edit.commit()
                    edit.apply()
                    Global.utils!!.hideCustomLoadingDialog()
                    Toast.makeText(this, "Welcome Back\t" + "\t" + name, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginOptionsActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }
        }
        loginViewModel!!.LDLogin().observe(this) { response ->
            if (response != null) {

                val userName: String = response.result!!.customerDetails!!.name!!
                val userBal: String = response.result!!.customerDetails!!.balance!!
                val userMail: String = response.result!!.customerDetails!!.email!!
                val userNumber: String = response.result!!.customerDetails!!.mobileNumber!!
                if (response.result!!.customerDetails!!.profilePictureUrl != null) {
                    userPhoto = response.result!!.customerDetails!!.profilePictureUrl!!
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, userPhoto)
                }

                val userID: String = response.result!!.customerDetails!!.userId!!
                edit.putBoolean(PreferencesNew.KEY_IS_Register, true)
                edit.putString(PreferencesNew.KEY_ApplicationUserId, userID)
                edit.putString(PreferencesNew.KEY_USER_NAME, userName)
                edit.putString(PreferencesNew.KEY_USER_PHONE, userNumber)
                edit.putString(PreferencesNew.KEY_USER_EMAIL, userMail)


                edit.putString(PreferencesNew.KEY_USER_Balance, userBal)
                edit.commit()
                edit.apply()
                if (response.result!!.responseCode.equals("0000")) {
                    edit.putBoolean(PreferencesNew.KEY_IS_LOGIN, true)
                    edit.commit()
                    edit.apply()

                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@LoginOptionsActivity, MainActivity::class.java))
                    Toast.makeText(this, "Welcome\t" + userName, Toast.LENGTH_SHORT).show()
                    finishAffinity()

                } else {
                    onSNACK(binding.root)
                    Global.utils!!.hideCustomLoadingDialog()
                }
            }
        }
    }

    private fun onSNACK(view: View) {
        //Snackbar(view)
        val snackbar = Snackbar.make(
            view, "Pin Or Number Incorrect!!",
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLACK)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.WHITE)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.RED)
        textView.textSize = 20f
        textView.setGravity(Gravity.CENTER)
        snackbar.show()
    }

    private fun clickListerners() {
        binding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
        binding.btnContGoogle.setOnClickListener {
            if (isOnline(this@LoginOptionsActivity)) {
                signIn()
                Global.utils!!.showCustomLoadingDialog(this@LoginOptionsActivity)
            } else {
                Toast.makeText(
                    this@LoginOptionsActivity, "Check Internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.clLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        binding = ActivityLoginOptionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        //firebaseAuth = Firebase.auth
        prefs = getSharedPreferences(prefrenceKey, Context.MODE_PRIVATE)
        edit = prefs.edit()
        //  checkUser()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                deviceToken = task.result
            } else {
                Toast.makeText(this, "Token Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthwithGoogleAccount: begin firebase auth with google account")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "updateUI: LoggedIn")

                val fireBaseUser = firebaseAuth.currentUser
                uid = fireBaseUser!!.uid
                email = fireBaseUser.email!!
                name = fireBaseUser.displayName!!
                profileImage = fireBaseUser.photoUrl!!
//                Log.d(TAG, "updateUI: Uid: $uid")
//                Log.d(TAG, "updateUI: Email: $email")
//                Log.d(TAG, "updateUI: Name: $name")

                if (authResult.additionalUserInfo!!.isNewUser) {
                    //   Log.d(TAG, "updateUI: Account created... \n$name")
                    Toast.makeText(
                        this@LoginOptionsActivity, "Account created... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();

                    val type = "Email"
                    val pin = "forum@1234"
//                    registrationViewModel!!.calRegisterUserGoogle(
//                        name, email, phoneNo, type, pin,
//                        profileImage!!.toString()
//                    )
                    edit.putString(PreferencesNew.KEY_ApplicationUserId, uid)
                    edit.putString(PreferencesNew.KEY_USER_NAME, name)
                    //  edit.putString(PreferencesNew.KEY_USER_PHONE, phoneNo)
                    edit.putString(PreferencesNew.KEY_USER_EMAIL, email)
                    edit.putString(PreferencesNew.KEY_USER_IMAGE, profileImage.toString())
                    edit.commit()
                    edit.apply()
                    Global.utils!!.hideCustomLoadingDialog()
                    startActivity(Intent(this@LoginOptionsActivity, GetNumberActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(
                        this@LoginOptionsActivity, "Logged in... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();
                    val os = "Android"
                    val type = "Email"
                    val pin = "forum@1234"
                    loginViewModel!!.calLogin(email, type, pin, os, deviceToken)
                }


            }
            .addOnFailureListener { e ->
                Log.d(TAG, "updateUI: Logged Failed due to ${e.message}")
                Toast.makeText(
                    this@LoginOptionsActivity, "Logged Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show();
            }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                updateUI(account)
            } catch (e: Exception) {
                Log.d(TAG, "OnActivityResult: ${e.message}")
            }
            //  handleSignInResult(task)
        }
    }

//    private fun getFileExtension(uri: Uri): String? {
//        val contentResolver: ContentResolver = this@LoginOptionsActivity.getContentResolver()
//        val mimeTypeMap = MimeTypeMap.getSingleton()
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
//    }

//    private fun checkUser() {
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }


//    private fun uploadData() {
//        val userModel = UserModel(email, uid, name)
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(uid)
//
//        databaseReference!!.setValue(userModel).addOnSuccessListener {
//            Toast.makeText(
//                this@LoginOptionsActivity,
//                "dataAddedSuccesfully",
//                Toast.LENGTH_SHORT
//            ).show()
//
//        }
//            .addOnFailureListener { e ->
//                Log.i(
//                    "sdfdf",
//                    e.message!!
//                )
//            }
//    }
}

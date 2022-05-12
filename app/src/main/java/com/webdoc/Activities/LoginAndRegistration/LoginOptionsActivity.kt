package com.webdoc.Activities.LoginAndRegistration

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.webdoc.Activities.MainActivity
import com.webdoc.Essentials.Global
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

    private companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        clickListerners()
    }

    private fun clickListerners() {
        binding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
        binding.btnContGoogle.setOnClickListener {
            signIn();

            Global.utils!!.showCustomLoadingDialog(this@LoginOptionsActivity)

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
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("user")
        //firebaseAuth = Firebase.auth
        prefs = getSharedPreferences(prefrenceKey, Context.MODE_PRIVATE)
        edit = prefs.edit()
        checkUser()

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

                    uploadData()

                    edit.putString("id", uid)
                    edit.commit()
                    edit.apply()
                    val intent = Intent(this, GetNumberActivity::class.java)
                    startActivity(intent)
                    Global.utils!!.hideCustomLoadingDialog()
                } else {
                    Log.d(TAG, "updateUI: Existing User... \n$name")


                    Toast.makeText(
                        this@LoginOptionsActivity, "Logged in... \n$name",
                        Toast.LENGTH_SHORT
                    ).show();
                    //    Preferences.getInstance(applicationContext).setKEY_ApplicationUserId(uid)

                    //  uploadDatawithImage()

                    edit.putString("id", uid)
                    edit.commit()
                    edit.apply()
                    val intent = Intent(this, GetNumberActivity::class.java)
                    startActivity(intent)
                    Global.utils!!.hideCustomLoadingDialog()
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

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = this@LoginOptionsActivity.getContentResolver()
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //    private fun uploadDatawithImage() {
//        // storageReference = FirebaseStorage.getInstance().getReference("uploads")
//
////            String extension = getFileExtension(imageUriforStorage);
//
////            String extension = getFileExtension(imageUriforStorage);
//        if (profileImage != null) {
//
////            String extension = getFileExtension(imageUriforStorage);
//            val fileReference = storageReference!!.child(
//                System.currentTimeMillis().toString() + getFileExtension(profileImage!!)
//            )
//            fileReference.putFile(profileImage!!).addOnSuccessListener { taskSnapshot ->
//                val result = taskSnapshot.metadata!!
//                    .reference!!.downloadUrl
//                result.addOnSuccessListener { uri ->
//                    val employee = UserModel(
//                        email, uid, name, profileImage!!
//                    )
//                    val reference =
//                        FirebaseDatabase.getInstance().reference.child("UserData")
//                    reference.push().setValue(employee).addOnSuccessListener {
//                        Toast.makeText(
//                            this@LoginOptionsActivity,
//                            "dataAddedSuccesfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                        .addOnFailureListener { e -> Log.i("sdfdf", e.message!!) }
//                    // pd.dismiss();
//                }
//            }.addOnFailureListener { e ->
////                pd!!.dismiss()
////                Toast.makeText(getActivity(), e.message, Toast.LENGTH_SHORT).show()
//            }.addOnProgressListener { snapshot ->
////                val progressPercent = 100.00 * snapshot.bytesTransferred / snapshot.totalByteCount
////                pd!!.setMessage("Progress" + progressPercent.toInt() + "%")
//            }
//
//
//        }
//    }
    private fun uploadData() {
        val userModel = UserModel(email, uid, name)

        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(uid)

        databaseReference!!.setValue(userModel).addOnSuccessListener {
            Toast.makeText(
                this@LoginOptionsActivity,
                "dataAddedSuccesfully",
                Toast.LENGTH_SHORT
            ).show()

        }
            .addOnFailureListener { e ->
                Log.i(
                    "sdfdf",
                    e.message!!
                )
            }
    }
}

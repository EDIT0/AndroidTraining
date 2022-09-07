package com.example.sociallogin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sociallogin.databinding.ActivityGoogleLoginBinding
import com.example.sociallogin.databinding.ActivityKakaoLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleLoginActivity : AppCompatActivity() {

    private val TAG = GoogleLoginActivity::class.java.simpleName

    private var _binding : ActivityGoogleLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var mGoogleSignInClient : GoogleSignInClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGoogleLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnLogin.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 9090)
            Log.i(TAG, "구글 로그인")
        }

        binding.btnLogout.setOnClickListener {
            try {
                mGoogleSignInClient.signOut()
                    .addOnCompleteListener { task: Task<Void> ->
                        if (task.isComplete) {
                            Log.i(TAG, "구글 로그아웃")
                        } else {
                            Log.i(TAG, "구글 로그아웃 실패")
                        }
                    }
            } catch (e : Exception) {
                Log.i(TAG, "${e.message.toString()}")
            }
        }

        binding.btnDeleteAccount.setOnClickListener {
            try {
                mGoogleSignInClient.revokeAccess()
                    .addOnCompleteListener { task : Task<Void> ->
                        if(task.isComplete) {
                            Log.i(TAG, "구글 계정 해제")
                        } else {
                            Log.i(TAG, "구글 계정 해제 실패")
                        }
                    }
            } catch (e : Exception) {
                Log.i(TAG, "${e.message.toString()}")
            }
        }

        binding.btnUserInfo.setOnClickListener {
            val acct = GoogleSignIn.getLastSignedInAccount(binding.root.context)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl

                Log.i(TAG, "email : ${personEmail} / name : ${personName} / familyName : ${personFamilyName}")
            } else {
                Log.i(TAG, "로그인 정보 없으므로 사용자 정보 없음")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 9090) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
//            updateUI(account)

            Log.i(TAG, "idToken : " + account.idToken + " / isExpired : " + completedTask.result.isExpired + "\n ${completedTask.result.account}")

            account.idToken.toString()

            val acct = GoogleSignIn.getLastSignedInAccount(binding.root.context)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl

                Log.i(TAG, "email : ${personEmail} / name : ${personName} / familyName : ${personFamilyName}")
            } else {
                Log.i(TAG, "로그인 정보 없으므로 사용자 정보 없음")
            }

            Log.i(TAG, "로그인 완료 ${account.email} / token : ${account.idToken} / isExpired : ${account.isExpired}")

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
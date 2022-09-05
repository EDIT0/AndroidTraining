package com.example.sociallogin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sociallogin.databinding.ActivityKakaoLoginBinding
import com.example.sociallogin.databinding.ActivityNaverLoginBinding
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class NaverLoginActivity : AppCompatActivity() {

    private val TAG = NaverLoginActivity::class.java.simpleName

    private var _binding : ActivityNaverLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var oAuthLoginModule : OAuthLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNaverLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        oAuthLoginModule = OAuthLogin.getInstance()

        binding.btnLogin.setOnClickListener {
            oAuthLoginModule.init(
                binding.root.context,
                getString(R.string.naver_client_id),
                getString(R.string.naver_client_secret),
                getString(R.string.naver_client_name)
            )

            val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak") object : OAuthLoginHandler() {
                override fun run(success: Boolean) {
                    if (success) {
                        Log.i(TAG, "AccessToken : ${oAuthLoginModule.getAccessToken(binding.root.context)}")
                        Log.i(TAG, "RefreshToken : ${oAuthLoginModule.getRefreshToken(binding.root.context)}")
                        Log.i(TAG, "ExpiresAt : ${oAuthLoginModule.getExpiresAt(binding.root.context)}")
                        Log.i(TAG, "TokenType : ${oAuthLoginModule.getTokenType(binding.root.context)}")

                        // 사용자 정보 가져오기
                        val url = "https://openapi.naver.com/v1/nid/me"

                        CoroutineScope(Dispatchers.IO).launch {
                            val result = oAuthLoginModule.requestApi(
                                binding.root.context,
                                oAuthLoginModule.getAccessToken(binding.root.context),
                                url
                            )

                            Log.i(TAG, "Result: ${result}")

                            try {
                                val loginResult = JSONObject(result)
                                if (loginResult.getString("resultcode") == "00") {
                                    val response: JSONObject = loginResult.getJSONObject("response")
                                    val id = response.getString("id")
                                    val email = response.getString("email")

                                    runOnUiThread {
                                        Log.i(TAG, "email : ${email}")
                                    }
                                } else {
                                    runOnUiThread {
                                        Log.e(TAG, "유저 정보 없음")
                                    }
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "${e.message.toString()}")
                            }
                            Log.i(TAG, "로그인 성공")
                        }
                    } else {
                        val errorCode: String = oAuthLoginModule.getLastErrorCode(binding.root.context).getCode()
                        val errorDesc: String = oAuthLoginModule.getLastErrorDesc(binding.root.context)

                        Log.e(TAG, "실패 " + errorCode + " / " + errorDesc)
                    }
                }
            }

            oAuthLoginModule.enableWebViewLoginOnly()
            oAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler)
        }

        binding.btnLogout.setOnClickListener {

//            CoroutineScope(Dispatchers.IO).launch {
            oAuthLoginModule.logout(binding.root.context)


            runOnUiThread {
                Log.i(TAG, "로그아웃 성공")
            }


//            }
        }

        binding.btnDeleteAccount.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isDelete = oAuthLoginModule.logoutAndDeleteToken(binding.root.context)

                if(isDelete) {
                    runOnUiThread {
                        Log.i(TAG, "계정 삭제 성공")
                    }
                } else {
                    Log.i(TAG, "계정 삭제 실패")
            }
        }

        binding.btnUserInfo.setOnClickListener {
            val url = "https://openapi.naver.com/v1/nid/me"

            CoroutineScope(Dispatchers.IO).launch {
                var result = oAuthLoginModule.requestApi(binding.root.context, oAuthLoginModule.getAccessToken(binding.root.context), url)
                try {
                    val loginResult = JSONObject(result)
                    if (loginResult.getString("resultcode") == "00") {
                        val response: JSONObject = loginResult.getJSONObject("response")
                        val id = response.getString("id")
                        val email = response.getString("email")

                        runOnUiThread {
                            Log.i(TAG, "email : ${email} / id : ${id}")
                        }

                    } else {
                        runOnUiThread {
                            Log.e(TAG, "유저 정보 없음")
                        }
                    }
                } catch (e: Exception) {
//                    e.printStackTrace()

                }
            }
        }

        binding.btnRefreshToken.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    oAuthLoginModule.refreshAccessToken(binding.root.context)
                    runOnUiThread {
                        Log.i(TAG, "AccessToken: ${oAuthLoginModule.getAccessToken(binding.root.context)}")
                        Log.i(TAG, "RefreshToken: ${oAuthLoginModule.getRefreshToken(binding.root.context)}")
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Log.e(TAG,"갱신 에러 ${e.message.toString()}")
                    }
                }
            }
        }
    }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
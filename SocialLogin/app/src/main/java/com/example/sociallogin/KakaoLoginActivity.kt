package com.example.sociallogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sociallogin.databinding.ActivityKakaoLoginBinding
import com.example.sociallogin.databinding.ActivityMainBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class KakaoLoginActivity : AppCompatActivity() {
    
    private val TAG = KakaoLoginActivity::class.java.simpleName

    private var _binding : ActivityKakaoLoginBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityKakaoLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val keyHash = Utility.getKeyHash(this)
        Log.i(TAG, "카카오 릴리즈 키: ${keyHash}")
        
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.i(TAG, "${token.toString()}\nAccessToken : ${token?.accessToken}" +
                    "\nRefreshToken : ${token?.refreshToken}" +
                    "\nAccessTokenExpiresAt : ${token?.accessTokenExpiresAt}" +
                    "\nRefreshTokenExpiresAt : ${token?.refreshTokenExpiresAt}")
            if (error != null) {
                Log.i(TAG, "로그인 실패")
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Log.i(TAG, "로그인 성공")
                
                // 사용자 정보 가져오기
                UserApiClient.instance.me { user: User?, meError: Throwable? ->
                    if (meError != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", meError)
                    } else {
                        Log.i(TAG, """사용자 정보 요청 성공회원번호: ${user?.id} 닉네임: ${user?.kakaoAccount?.profileNicknameNeedsAgreement}""".trimIndent())
                        Log.i(TAG, "userConnectedAt : ${user?.connectedAt.toString()}")
                    }
                }
                

                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin.setOnClickListener {
            // 로그인 공통 callback 구성
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(binding.root.context)) {
                UserApiClient.instance.loginWithKakaoTalk(binding.root.context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(binding.root.context, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            }else {
                UserApiClient.instance.loginWithKakaoAccount(binding.root.context, callback = callback)
            }
        }

        binding.btnLogout.setOnClickListener {
            UserApiClient.instance.logout { error: Throwable? ->
                if(error != null) {
                    Log.e(TAG, "로그아웃 실패")
                } else {
                    Log.i(TAG, "로그아웃 성공")
                }
            }
        }

        binding.btnDeleteAccount.setOnClickListener {
            UserApiClient.instance.unlink { error: Throwable? ->
                if(error != null) {
                    Log.e(TAG, "연결 끊기 실패")
                } else {
                    Log.i(TAG, "연결 끊기 성공")
                }
            }
        }

        binding.btnUserInfo.setOnClickListener {
            UserApiClient.instance.me { user: User?, meError: Throwable? ->
                if (meError != null) {
                    Log.e(TAG, "사용자 정보 요청 실패", meError)
                } else {
                    Log.i(TAG, """사용자 정보 요청 성공회원번호: ${user?.id} 닉네임: ${user?.kakaoAccount?.profileNicknameNeedsAgreement}""".trimIndent())
                    Log.i(TAG, "userConnectedAt : ${user?.connectedAt.toString()}")
                }
            }
        }

        binding.btnRefreshToken.setOnClickListener {
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { value, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            //로그인 필요
                        }
                        else {
                            //기타 에러
                        }
                    } else {
                        //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                        Log.i(TAG, "value : ${value.toString()}")
                        Log.i(TAG, "AccessToken: ${AuthApiClient.instance.tokenManagerProvider.manager.getToken()?.accessToken.toString()}")
                        Log.i(TAG, "RefreshToken: ${AuthApiClient.instance.tokenManagerProvider.manager.getToken()?.refreshToken.toString()}")
                        Log.i(TAG, "클라이언트 get token : " + AuthApiClient.instance.tokenManagerProvider.manager.getToken())

                    }
                }
            } else {
                //로그인 필요
                Log.e(TAG, "로그인이 필요합니다.")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
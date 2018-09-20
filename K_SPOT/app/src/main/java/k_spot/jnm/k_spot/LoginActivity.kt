package k_spot.jnm.k_spot

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.kakao.auth.ISessionCallback
import com.kakao.auth.KakaoSDK
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Network.NetworkService
import k_spot.jnm.k_spot.Post.PostKakaoResponse
import k_spot.jnm.k_spot.activity.MainActivity
import k_spot.jnm.k_spot.db.SharedPreferenceController
import k_spot.jnm.k_spot.kakao.KakaoSDKAdapter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var callback: SessionCallback

    private lateinit var networkService: NetworkService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setStatusBarTransparent()
        if (KakaoSDK.getAdapter() == null) {
            KakaoSDK.init(KakaoSDKAdapter(ctx = this))
        }
        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()

        setClickListener()

        Log.e("hashkey", getHashKey(applicationContext))

    }

    private fun setClickListener() {
        kakao_custom_login_btn.setOnClickListener {
            kakao_login_btn.performClick()
            Log.v("xx", "Xxxxxxx")
        }
        login_act_freepass_btn.setOnClickListener {
            SharedPreferenceController.clearSPC(context = applicationContext)
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    inner class SessionCallback : ISessionCallback {
        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Log.e("카톡 로긴 콜백 실패", exception.toString())
            }
        }

        override fun onSessionOpened() {

            val access_token: String = Session.getCurrentSession().tokenInfo.accessToken

            Log.v("access_token",access_token)
            requestLoginToServer(access_token)
        }
    }

    private fun requestLoginToServer(access_token: String) {
        networkService = ApplicationController.instance.networkService
        val authorization: String = SharedPreferenceController.getAuthorization(context = applicationContext)
        var postKakaoLoginResponse: Call<PostKakaoResponse>

        if (authorization.length != 0) {
            postKakaoLoginResponse = networkService.postKakaoLogin(0, authorization, access_token)
            Log.v("A","A")
        } else {
            postKakaoLoginResponse = networkService.postKakaoLogin(null, null, access_token)
            Log.v("B","B")
        }
        postKakaoLoginResponse.enqueue(object : Callback<PostKakaoResponse> {
            override fun onFailure(call: Call<PostKakaoResponse>?, t: Throwable?) {
                Log.e("로긴 통신 실패", t.toString())
            }
            override fun onResponse(call: Call<PostKakaoResponse>?, response: Response<PostKakaoResponse>?) {
                if (response!!.isSuccessful){

                    val id : Int = response.body()!!.data.id
                    val auth: String= response.body()!!.data.authorization
                    SharedPreferenceController.setAuthorization(context = applicationContext, authorization = auth)
                    SharedPreferenceController.setMyId(context = applicationContext,id = id)

                    startActivity<MainActivity>()
                    finish()
                }
            }
        })
//        postKakaoLoginResponse.enqueue(object : Callback<PostKakaoResponse> {
//            override fun onFailure(call: Call<PostKakaoResponse>?, t: Throwable?) {
//                Log.e("로긴 통신 실패", t.toString())
//            }
//
//            override fun onResponse(call: Call<PostKakaoResponse>?, response: Response<PostKakaoResponse>?) {
//                if (response!!.isSuccessful) {
//                    val id: Int = response.body()!!.data.id
//                    val auth: String = response.body()!!.data.authorization
//                    SharedPreferenceController.setAuthorization(context = applicationContext, authorization = auth)
//                    SharedPreferenceController.setMyId(context = applicationContext, id = id)
//                    Log.v("id", id.toString())
//                    Log.v("auth", auth)
//                    startActivity<MainActivity>()
//                    finish()
//                }
//            }
//        })
    }

    // 상태바 투명하게 하는 함수
    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
//            DrawableCompat.setTint(, "#757575")
        }
        // 밑에 두줄 아이콘 회색으로 바꾸는 코드
        val view: View? = window.decorView
        view!!.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    // 상태바 투명하게 하는 함수
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    // 상단바 밑으로 뷰 보이게하는 코드
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val window = window
        val decorView = window.decorView
        if (Configuration.ORIENTATION_LANDSCAPE === newConfig.orientation) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.parseColor("#55000000") // set dark color, the icon will auto change light
            }
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.parseColor("#fffafafa")
            }
        }
    }


    // 프로젝트의 해시키를 반환
    fun getHashKey(context: Context): String? {
        val TAG = "KeyHash"
        var keyHash: String? = null
        try {
            val info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                keyHash = String(Base64.encode(md.digest(), 0))
                Log.d(TAG, keyHash)
            }
        } catch (e: Exception) {
            Log.e("name not found", e.toString())
        }
        return if (keyHash != null) {
            keyHash
        } else {
            null
        }
    }
}

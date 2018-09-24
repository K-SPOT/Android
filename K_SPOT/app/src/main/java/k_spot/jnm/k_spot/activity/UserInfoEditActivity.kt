package k_spot.jnm.k_spot.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import k_spot.jnm.k_spot.Network.ApplicationController
import k_spot.jnm.k_spot.Post.PostUserInfoResponse
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.db.SharedPreferenceController
import k_spot.jnm.k_spot.dialog.PictureSelectedDialog
import kotlinx.android.synthetic.main.activity_user_info_edit.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class UserInfoEditActivity : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_READ_CONTACTS : Int = 1001
    val REQUEST_CODE_SELECT_IMAGE : Int = 1004
    private var mImage : MultipartBody.Part? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_user_info_edit)
        setStatusBarTransparent()
        setClickListener()

        setUserInfoView(intent.getStringExtra("name"), intent.getStringExtra("image"))

        requestPermissionDenial()
    }

    private fun setUserInfoView(name : String, image_url: String){
        et_user_info_edit_user_name.setText(name)
        Glide.with(this).load(image_url).into(iv_user_info_edit_user_image)
        tv_user_info_edit_user_name_length_count.text = "${name.length}/20"

        et_user_info_edit_user_name.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                tv_user_info_edit_user_name_length_count.text = "${s!!.length}/20"
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

    fun setDefaultUserImage(){
        iv_user_info_edit_user_image.setImageResource(R.drawable.mypage_default_profile_img)
    }

    fun setSeletedPictureOption(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }

    private fun setClickListener(){
        btn_user_info_edit_selected_picture_option.setOnClickListener {
            val dialog : Dialog = PictureSelectedDialog(this)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        btn_user_info_edit_act_back.setOnClickListener {
            finish()
        }

        btn_user_info_edit_complete.setOnClickListener {
            resquestUserInfoChange()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE){
            if (resultCode == Activity.RESULT_OK){
                data?.let {
                    var  seletedPictureUri = it.data

                    val options = BitmapFactory.Options()

                    val inputStream : InputStream = contentResolver.openInputStream(seletedPictureUri)

                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())

                    mImage = MultipartBody.Part.createFormData("image", File(seletedPictureUri.toString()).name, photoBody)
                    Log.e("사진", mImage.toString())


                    Glide.with(this@UserInfoEditActivity).load(seletedPictureUri).thumbnail(0.1f).into(iv_user_info_edit_user_image)
                }
            }
        }

    }

    private fun resquestUserInfoChange(){
        mImage?.let {
            val networkService = ApplicationController.instance.networkService
            val postUserInfoResponse = networkService.postUserInfoResponse(0, SharedPreferenceController.getAuthorization(this),
                    et_user_info_edit_user_name.text.toString(), mImage!!)
            postUserInfoResponse.enqueue(object : Callback<PostUserInfoResponse>{
                override fun onFailure(call: Call<PostUserInfoResponse>?, t: Throwable?) {
                    Log.e("사진 전송 에러", t.toString())
                }

                override fun onResponse(call: Call<PostUserInfoResponse>?, response: Response<PostUserInfoResponse>?) {
                    response?.let {
                        if (response.isSuccessful){
                            toast("변경 완료")
                            finish()
                        }
                    }
                }
            })
        }
        Log.e("사진 전송 에러", "아예 비어있다")

    }

    fun requestPermissionDenial(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.e("여긴?", "1")
            } else {
                Log.e("여긴?", "2")
            }
        }
    }



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
        }

        val view: View? = window.decorView
        view!!.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
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
}

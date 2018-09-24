package k_spot.jnm.k_spot.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.activity.UserInfoEditActivity
import kotlinx.android.synthetic.main.dialog_edit_select_picture_option_message.*

class PictureSelectedDialog(val ctx: Context) : Dialog(ctx){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_select_picture_option_message)

        setOptionClickListener()
    }


    private fun setOptionClickListener(){
        btn_edit_picture_option_dialog_album.setOnClickListener {
            (ctx as UserInfoEditActivity).setSeletedPictureOption()
            dismiss()
        }

        btn_edit_picture_option_dialog_default.setOnClickListener {
            (ctx as UserInfoEditActivity).setDefaultUserImage()
            dismiss()
        }

        btn_edit_picture_option_dialog_cancel.setOnClickListener {
            dismiss()
        }
    }
}
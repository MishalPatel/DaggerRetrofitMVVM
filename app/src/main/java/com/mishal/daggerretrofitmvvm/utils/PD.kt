package com.mishal.daggerretrofitmvvm.utils


import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.databinding.DataBindingUtil
import com.mishal.daggerretrofitmvvm.R
import com.mishal.daggerretrofitmvvm.databinding.DialogProgressBinding


class PD(private val context: Activity) {
    private var progressDialog: Dialog? = null
    private var binding: DialogProgressBinding? = null

    fun createDialog() {
        binding =
            DataBindingUtil.inflate(context.layoutInflater, R.layout.dialog_progress, null, false)
        progressDialog = Dialog(context)
        progressDialog!!.setContentView(binding!!.root)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        progressDialog!!.window!!.setGravity(Gravity.CENTER)
    }

    fun show() {
        if (progressDialog != null) {
            progressDialog!!.show()
        }
    }

    fun show(msg: String) {
        if (progressDialog != null) {
            progressDialog!!.show()
        }
    }

    fun hide() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }
}

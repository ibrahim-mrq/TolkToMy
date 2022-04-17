package com.tolk_to_my.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.hawk.Hawk
import com.tolk_to_my.R
import com.tapadoo.alerter.Alerter

open class BaseActivity : AppCompatActivity() {

    private var dialog: Dialog? = null

    open fun showCustomProgress(onCancelListener: DialogInterface.OnCancelListener?) {
        if (dialog == null) {
            dialog = Dialog(
                this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen
            )
            dialog!!.setContentView(R.layout.include_loading_screen)
            dialog!!.setCancelable(true)
            dialog!!.findViewById<View>(R.id.root_view).setOnClickListener {
                dialog!!.dismiss()
                onCancelListener?.onCancel(dialog)
            }
            dialog!!.show()
        } else {
            dialog!!.show()
        }
    }

    open fun showCustomProgress(setCancelable: Boolean) {
        if (dialog == null) {
            dialog = Dialog(
                this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen
            )
            dialog!!.setContentView(R.layout.include_loading_screen)
            dialog!!.setCancelable(setCancelable)
            dialog!!.show()
        } else {
            dialog!!.show()
        }
    }

    open fun dismissCustomProgress() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    open fun showErrorAlert(
        activity: Activity?,
        title: String?,
        text: String?,
        @DrawableRes icon: Int
    ) {
        Alerter.create(activity!!)
            .setTitle(title!!)
            .setText(text!!)
            .setBackgroundColorRes(R.color.red)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .setIcon(icon)
            .show()
    }

    open fun showOfflineAlert(
        activity: Activity?,
        title: String?,
        text: String?
    ) {
        Alerter.create(activity!!)
            .setTitle(title!!)
            .setText(text!!)
            .setBackgroundColorRes(R.color.orange)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .hideIcon()
            .show()
    }

    open fun showErrorAlert(
        activity: Activity?,
        title: String?,
        text: String?
    ) {
        Alerter.create(activity!!)
            .setTitle(title!!)
            .setText(text!!)
            .setBackgroundColorRes(R.color.red)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .hideIcon()
            .show()
    }

    open fun showAlert(
        activity: Activity?,
        title: String?,
        text: String?,
        @DrawableRes icon: Int
    ) {
        Alerter.create(activity!!)
            .setTitle(title!!)
            .setText(text!!)
            .setBackgroundColorRes(R.color.colorPrimary)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .setIcon(icon)
            .show()
    }

    open fun showAlert(
        activity: Activity?,
        title: String?,
        text: String?
    ) {
        Alerter.create(activity!!)
            .setTitle(title!!)
            .setText(text!!)
            .setBackgroundColorRes(R.color.colorPrimary)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .hideIcon()
            .show()
    }

    fun isNotEmpty(editText: TextInputEditText, textInputLayout: TextInputLayout): Boolean {
        return if (editText.text.toString().isBlank()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.empty_field)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    fun isNotEmpty(editText: AutoCompleteTextView, textInputLayout: TextInputLayout): Boolean {
        return if (editText.text.toString().isBlank()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = getString(R.string.empty_field)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    fun isValidEmail(editText: TextInputEditText, textInputLayout: TextInputLayout): Boolean {
        return if (Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
            textInputLayout.isErrorEnabled = false
            true
        } else {
            textInputLayout.error = getString(R.string.invalid_email)
            textInputLayout.isErrorEnabled = true
            false
        }
    }

    fun isPasswordLess(pass: TextInputEditText, text: TextInputLayout): Boolean {
        return if (pass.text.toString().trim().length >= 6) {
            text.isErrorEnabled = false
            true
        } else {
            text.error = getString(R.string.password_less_6_char)
            text.isErrorEnabled = true
            false
        }
    }

    fun isPasswordMatch(
        pass: TextInputEditText,
        text: TextInputLayout,
        re_pass: TextInputEditText,
        re_text: TextInputLayout
    ): Boolean {
        return if (pass.text.toString().trim() == re_pass.text.toString().trim()) {
            text.isErrorEnabled = false
            re_text.isErrorEnabled = false
            true
        } else {
            text.error = getString(R.string.password_not_match)
            text.isErrorEnabled = true
            re_text.error = getString(R.string.password_not_match)
            re_text.isErrorEnabled = true
            false
        }
    }

    fun getText(editText: TextInputEditText): String {
        return editText.text.toString().trim()
    }
    fun getText(editText: AutoCompleteTextView): String {
        return editText.text.toString().trim()
    }

    @SuppressLint("SetTextI18n")
    fun setUserType(context: Context, type: String, textView: TextInputEditText) {
        if (type == Constants.TYPE_VENDOR) {
            textView.setText(context.getString(R.string.vendor))
        } else {
            textView.setText(context.getString(R.string.customer))
        }
    }

}
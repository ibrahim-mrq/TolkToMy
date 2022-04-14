package com.tolk_to_my.helpers

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tapadoo.alerter.Alerter
import com.tolk_to_my.R

open class BaseFragment : Fragment() {

    var dialog: Dialog? = null

    open fun showCustomProgress(onCancelListener: DialogInterface.OnCancelListener?) {
        if (dialog == null) {
            dialog = Dialog(
                requireActivity(),
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
                requireActivity(),
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
        title: String?,
        text: String?,
        @DrawableRes icon: Int
    ) {
        Alerter.create(requireActivity())
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
        title: String?,
        text: String?
    ) {
        Alerter.create(requireActivity())
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
        title: String?,
        text: String?
    ) {
        Alerter.create(requireActivity())
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
        title: String?,
        text: String?,
        @DrawableRes icon: Int
    ) {
        Alerter.create(requireActivity())
            .setTitle(title!!)
            .setText(text!!)
            .setBackgroundColorRes(R.color.green_success)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .setIcon(icon)
            .show()
    }

    open fun showAlert(
        title: String?,
        text: String?
    ) {
        Alerter.create(requireActivity())
            .setTitle(title!!)
            .setText(text!!)
            .setBackgroundColorRes(R.color.green_success)
            .setContentGravity(Gravity.CENTER)
            .enableSwipeToDismiss()
            .setDuration(2000)
            .hideIcon()
            .show()
    }

    fun getText(editText: TextInputEditText): String {
        return editText.text.toString().trim()
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
}
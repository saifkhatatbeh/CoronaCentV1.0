package com.v1.Tammeni.Fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.v1.Tammeni.Activities.DashboardActivity
import com.v1.Tammeni.Activities.LoginActivity
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.Helpers.MyPreference
import com.v1.Tammeni.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.lang.Exception
import java.util.*

class Settings_Fragment : Fragment() {

    lateinit var myPreference: MyPreference
    private lateinit var mAuth: FirebaseAuth

    companion object {
        fun newInstance() = Settings_Fragment()
    }

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val grey = context?.let { ContextCompat.getColor(it, R.color.colorWhite2) }

        val green = context?.let { ContextCompat.getColor(it, R.color.colorPrimary) }
        val grey2 = context?.let { ContextCompat.getColor(it, R.color.colorSecondaryText) }

        val current = resources.configuration.locale.toString()

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        val customToast_positive: View =
            layoutInflater.inflate(R.layout.custom_toast_positive, fl_wrapper_fill_all_fields_toast)


//-----------------------------------------------------------------------------------------------------------------
        if (user != null) {
            if (user.isAnonymous) {
                if (grey != null) {

                    change_username_image.setImageResource(R.drawable.icon_password)
                    cardview_change_username.setCardBackgroundColor(grey)

                }
            } else {
                cardview_change_username?.setOnClickListener() {

                    val dialog_change_username = activity?.let { it1 -> Dialog(it1) }

                    dialog_change_username?.setContentView(R.layout.change_username_dialogue)

                    val closeBtn_change_username =
                        dialog_change_username?.findViewById(R.id.closePositive_change_username) as ImageView
                    val change_username_Btn =
                        dialog_change_username?.findViewById(R.id.button_change_username) as Button


                    closeBtn_change_username.setOnClickListener {
                        dialog_change_username.dismiss()
                    }

                    dialog_change_username.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog_change_username.show()

                    val nConnection = NetworkConnection(requireActivity().applicationContext)

                    nConnection.observe(
                        requireActivity(),
                        androidx.lifecycle.Observer { isConnected ->
                            if (isConnected) {
                                change_username_Btn.setOnClickListener {

                                    val change_username_id =
                                        dialog_change_username.findViewById(R.id.textInput_NewUsername_editText) as TextInputEditText
                                    val change_username = change_username_id.editableText.toString()

                                    if (change_username.isEmpty()) {

                                        Toast(activity).apply {
                                            customToast_negative.toast_text.text =
                                                getString(R.string.Field_cannot_be_empty)
                                            view = customToast_negative
                                        }.show()


                                    } else {
                                        val user =
                                            FirebaseAuth.getInstance().currentUser

                                        val profileUpdates =
                                            UserProfileChangeRequest.Builder()
                                                .setDisplayName(change_username).build()

                                        user!!.updateProfile(profileUpdates).addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                dialog_change_username.dismiss()
                                                val dialog_username_success =
                                                    activity?.let { it1 -> Dialog(it1) }
                                                dialog_username_success?.setContentView(R.layout.positive_custom_dialogue_change_username)

                                                val success_btn_go_to_dashboard =
                                                    dialog_username_success?.findViewById(R.id.button_usernameChange_go_to_dashboard) as Button

                                                dialog_username_success.window?.setBackgroundDrawable(
                                                    ColorDrawable(
                                                        Color.TRANSPARENT
                                                    )
                                                )
                                                dialog_username_success.show()

                                                dialog_username_success.setCancelable(false)

                                                success_btn_go_to_dashboard.setOnClickListener {
                                                    val intent = Intent(
                                                        activity,
                                                        DashboardActivity::class.java
                                                    )
                                                    startActivity(intent.putExtra("dash", "dash"))
                                                    activity?.finish()
                                                }
                                            } else {


                                                Toast(activity).apply {
                                                    customToast_negative.toast_text.text =
                                                        getString(R.string.Something_went_wrong_please_try_again_later_)
                                                    view = customToast_negative
                                                }.show()


                                            }

                                        }
                                    }

                                }

                            } else {

                                val customToast_negative: View =
                                    layoutInflater.inflate(
                                        R.layout.custom_toast_negative,
                                        fl_wrapper_fill_all_fields_toast
                                    )
                                Toast(activity).apply {
                                    customToast_negative.toast_text.text =
                                        getString(R.string.No_Internet_Connection)
                                    view = customToast_negative
                                }.show()

                            }
                        })


                }

            }
        }


//-----------------------------------------------------------------------------------------------------------------
        if (user!!.isAnonymous) {
            change_password_image.setImageResource(R.drawable.icon_password)
            cardview_change_password.setCardBackgroundColor(grey!!)

        } else {
            cardview_change_password?.setOnClickListener() {

                val dialog_change_password = activity?.let { it1 -> Dialog(it1) }

                dialog_change_password?.setContentView(R.layout.change_password_dialogue)

                val closeBtn_change_password =
                    dialog_change_password?.findViewById(R.id.closePositive_change_password) as ImageView

                val change_password_Btn =
                    dialog_change_password?.findViewById(R.id.button_change_password) as Button

                closeBtn_change_password.setOnClickListener {
                    dialog_change_password.dismiss()
                }
                dialog_change_password.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_change_password.show()


                val nConnection = NetworkConnection(requireActivity().applicationContext)

                nConnection.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->
                    if (isConnected) {
                        change_password_Btn.setOnClickListener {
                            val current_pass_id =
                                dialog_change_password.findViewById(R.id.textInput_CurrentPassword_editText) as TextInputEditText
                            val pass_id =
                                dialog_change_password.findViewById(R.id.textInput_NewPassword_editText) as TextInputEditText
                            val confirm_pass_id =
                                dialog_change_password.findViewById(R.id.textInput_VerifyNewPassword_editText) as TextInputEditText

                            val current_pass = current_pass_id.editableText.toString()
                            val pass = pass_id.editableText.toString()
                            val confirm_pass = confirm_pass_id.editableText.toString()
                            current_pass.trim()
                            pass.trim()
                            confirm_pass.trim()

                            if (pass.isEmpty() || confirm_pass.isEmpty() || current_pass.isEmpty()) {

                                Toast(activity).apply {
                                    customToast_negative.toast_text.text =
                                        getString(R.string.All_fields_are_required)
                                    view = customToast_negative
                                }.show()


                            } else {
                                if (pass == confirm_pass) {
                                    val user = mAuth.currentUser
                                    if (user != null && user.email != null) {
                                        val credential = EmailAuthProvider
                                            .getCredential(user.email!!, current_pass)

                                        user.reauthenticate(credential)
                                            ?.addOnCompleteListener {
                                                if (it.isSuccessful) {

                                                    user?.updatePassword(pass)
                                                        ?.addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                dialog_change_password.dismiss()
                                                                val dialog_pass_success =
                                                                    activity?.let { it1 ->
                                                                        Dialog(
                                                                            it1
                                                                        )
                                                                    }
                                                                dialog_pass_success?.setContentView(
                                                                    R.layout.positive_custom_dialogue_change_password
                                                                )
                                                                dialog_pass_success?.setCancelable(
                                                                    false
                                                                )

                                                                val success_btn_go_to_dashboard =
                                                                    dialog_pass_success?.findViewById(
                                                                        R.id.button_passwordChange_go_to_login
                                                                    ) as Button

                                                                dialog_pass_success.window?.setBackgroundDrawable(
                                                                    ColorDrawable(Color.TRANSPARENT)
                                                                )
                                                                dialog_pass_success.show()

                                                                success_btn_go_to_dashboard.setOnClickListener {
                                                                    mAuth.signOut()
                                                                    dialog_pass_success.dismiss()

                                                                    startActivity(
                                                                        Intent(
                                                                            activity,
                                                                            LoginActivity::class.java
                                                                        )
                                                                    )
                                                                    activity?.finish()
                                                                }


                                                            } else {

                                                                Toast(activity).apply {

                                                                    customToast_negative.toast_text.text =
                                                                        getString(R.string.Please_make_sure_that_your_password_characters_are_6_or_above)

                                                                    view = customToast_negative


                                                                }.show()

                                                            }
                                                        }

                                                } else {

                                                    Toast(activity).apply {

                                                        customToast_negative.toast_text.text =
                                                            getString(R.string.Wrong_Password)
                                                        view = customToast_negative


                                                    }.show()
                                                }
                                            }
                                    } else {
                                        dialog_change_password.dismiss()
                                    }
                                } else {


                                    Toast(activity).apply {
                                        customToast_negative.toast_text.text =
                                            getString(R.string.Make_sure_both_passwords_match)
                                        view = customToast_negative
                                    }.show()

                                }
                            }

                        }


                    } else {

                        val customToast_negative: View =
                            layoutInflater.inflate(
                                R.layout.custom_toast_negative,
                                fl_wrapper_fill_all_fields_toast
                            )
                        Toast(activity).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.No_Internet_Connection)
                            view = customToast_negative
                        }.show()

                    }
                })


            }
        }

//-----------------------------------------------------------------------------------------------------------------

        if (Build.VERSION.SDK_INT >= 26) {
            cardview_change_language?.setOnClickListener() {

                val dialog_change_language = activity?.let { it1 -> Dialog(it1) }

                dialog_change_language?.setContentView(R.layout.change_language_dialogue)

                val noBtn =
                    dialog_change_language?.findViewById(R.id.closePositive_change_language) as ImageView
                val btnArabic =
                    dialog_change_language?.findViewById(R.id.button_change_language_arabic) as Button
                val btnEnglish =
                    dialog_change_language?.findViewById(R.id.button_change_language_english) as Button

                val English = "en"
                val Arabic = "ar"
                myPreference = MyPreference(requireActivity().applicationContext)


                btnArabic.setOnClickListener {
                    myPreference.setLoginCount(Arabic)
                    val langArabic: Locale = Locale.Builder().setLanguage("ar").build()
                    Locale.setDefault(langArabic)

                    val dashboard = fragmentManager?.findFragmentByTag("1")

                    dashboard?.indicator_1?.visibility = View.GONE
                    dashboard?.indicator_2?.setTextColor(green!!)

//                    requireActivity().supportFragmentManager.beginTransaction().detach(this)
//                        .attach(this).commit()
//                    activity?.let { it1 -> ActivityCompat.recreate(it1) }


                    val intent = Intent(activity, DashboardActivity::class.java)
                    startActivity(intent.putExtra("dash", "dash"))
                    activity?.finish()

                    dialog_change_language.dismiss()
                }

                btnEnglish.setOnClickListener {
                    myPreference.setLoginCount(English)

//                    requireActivity().supportFragmentManager.beginTransaction().detach(this)
//                        .attach(this).commit()
//                    activity?.let { it1 -> ActivityCompat.recreate(it1) }

                    val intent = Intent(activity, DashboardActivity::class.java)
                    startActivity(intent.putExtra("dash", "dash"))
//                    startActivity(intent)
                    activity?.finish()
                    dialog_change_language.dismiss()

                }

                noBtn.setOnClickListener {
                    dialog_change_language.dismiss()
                }
//            val button_verify_email =
//                dialog_change_email.findViewById(R.id.button_emailsend_go_to_dashboard) as Button

                dialog_change_language.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_change_language.show()
            }

        } else {
            cardview_change_language.visibility = View.GONE
        }


//-----------------------------------------------------------------------------------------------------------------
        if (user.isAnonymous) {
            change_email_image.setImageResource(R.drawable.icon_password)
            cardview_change_email.setCardBackgroundColor(grey!!)

        } else {
            cardview_change_email?.setOnClickListener() {

                val dialog_change_email = activity?.let { it1 -> Dialog(it1) }

                dialog_change_email?.setContentView(R.layout.change_email_dialogue)

                val noBtn =
                    dialog_change_email?.findViewById(R.id.closePositive_change_email) as ImageView
                val change_email_btn =
                    dialog_change_email?.findViewById(R.id.button_change_email) as Button

                noBtn.setOnClickListener {
                    dialog_change_email.dismiss()
                }
                dialog_change_email.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_change_email.show()


                val nConnection = NetworkConnection(requireActivity().applicationContext)

                nConnection.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->
                    if (isConnected) {
                        change_email_btn.setOnClickListener {
                            val current_pass_id =
                                dialog_change_email.findViewById(R.id.textInput_CurrentEmailPassword_editText) as TextInputEditText
                            val email_id =
                                dialog_change_email.findViewById(R.id.textInput_NewEmail_editText) as TextInputEditText
                            val confirm_email_id =
                                dialog_change_email.findViewById(R.id.textInput_VerifyNewEmail_editText) as TextInputEditText

                            val current_pass = current_pass_id.editableText.toString()
                            val email = email_id.editableText.toString()
                            val confirm_email = confirm_email_id.editableText.toString()

                            current_pass.trim()
                            email.trim()
                            confirm_email.trim()

                            if (current_pass.isEmpty() || email.isEmpty() || confirm_email.isEmpty()) {

                                Toast(activity).apply {
                                    customToast_negative.toast_text.text =
                                        getString(R.string.All_fields_are_required)
                                    view = customToast_negative
                                }.show()


                            } else {
                                if (email == confirm_email) {
                                    val user = mAuth.currentUser
                                    if (user != null && user.email != null) {
                                        val credential = EmailAuthProvider
                                            .getCredential(user.email!!, current_pass)

                                        user?.reauthenticate(credential)
                                            ?.addOnCompleteListener {
                                                if (it.isSuccessful) {

                                                    user?.updateEmail(email)
                                                        ?.addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                dialog_change_email.dismiss()
                                                                val user = mAuth.currentUser
                                                                user?.sendEmailVerification()
                                                                val dialog_pass_success =
                                                                    activity?.let { it1 ->
                                                                        Dialog(
                                                                            it1
                                                                        )
                                                                    }
                                                                dialog_pass_success?.setContentView(
                                                                    R.layout.positive_custom_dialogue_change_email
                                                                )

                                                                val success_btn =
                                                                    dialog_pass_success?.findViewById(
                                                                        R.id.button_emailChange_go_to_login
                                                                    ) as Button
                                                                dialog_pass_success?.setCancelable(
                                                                    false
                                                                )

                                                                dialog_pass_success.window?.setBackgroundDrawable(
                                                                    ColorDrawable(Color.TRANSPARENT)
                                                                )
                                                                dialog_pass_success.show()

                                                                success_btn.setOnClickListener {
                                                                    mAuth.signOut()
                                                                    dialog_pass_success.dismiss()
                                                                    startActivity(
                                                                        Intent(
                                                                            activity,
                                                                            LoginActivity::class.java
                                                                        )
                                                                    )
                                                                    activity?.finish()
                                                                }


                                                            } else {
                                                                mAuth.fetchSignInMethodsForEmail(
                                                                    email
                                                                )
                                                                    .addOnCompleteListener {
                                                                        try {
                                                                            val isNewUser: Boolean? =
                                                                                it.getResult()
                                                                                    ?.getSignInMethods()
                                                                                    ?.isEmpty()
                                                                            if (!isNewUser!!) {

                                                                                Toast(activity).apply {
                                                                                    customToast_negative.toast_text.text =
                                                                                        getString(R.string.Email_already_used_please_try_using_a_different_email)
                                                                                    view =
                                                                                        customToast_negative
                                                                                }.show()


                                                                            }
                                                                        } catch (e: Exception) {

                                                                            Toast(activity).apply {
                                                                                customToast_negative.toast_text.text =
                                                                                    getString(R.string.Check_your_email_format)
                                                                                view =
                                                                                    customToast_negative
                                                                            }.show()


                                                                        }

                                                                    }
                                                            }
                                                        }

                                                } else {
                                                    Toast(activity).apply {

                                                        customToast_negative.toast_text.text =
                                                            getString(R.string.Wrong_Password)
                                                        view = customToast_negative


                                                    }.show()
                                                }
                                            }
                                    } else {
                                        dialog_change_email.dismiss()
                                    }
                                } else {

                                    Toast(activity).apply {
                                        customToast_negative.toast_text.text =
                                            getString(R.string.Make_sure_both_emails_match)
                                        view = customToast_negative
                                    }.show()

                                }
                            }
                        }


                    } else {

                        val customToast_negative: View =
                            layoutInflater.inflate(
                                R.layout.custom_toast_negative,
                                fl_wrapper_fill_all_fields_toast
                            )
                        Toast(activity).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.No_Internet_Connection)
                            view = customToast_negative
                        }.show()

                    }
                })
            }
        }
    }
}
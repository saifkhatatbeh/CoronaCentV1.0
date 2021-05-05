package com.v1.Tammeni.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.R
import com.v1.Tammeni.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import java.lang.Exception
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference

        back_to_login_button.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }

        register_submit_button.setOnClickListener() {
            dialog = Dialog(this)
            dialog.setContentView(R.layout.full_screen_progress_bar)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            onRegister()

//            val dialog_verify_account = Dialog(this)
//
//            dialog_verify_account.setContentView(R.layout.positive_custom_dialogue_register_verify_email)
//            val noBtn =
//                dialog_verify_account.findViewById(R.id.closePositive_successRegisterAccount) as ImageView
//
//            val button_dismiss = findViewById<Button>(R.id.button_register_verify_account_dismiss)
//
//            button_dismiss.setOnClickListener(){
//                dialog_verify_account.dismiss()
//            }
//
//            noBtn.setOnClickListener {
//                dialog_verify_account.dismiss()
//            }
//
//            dialog_verify_account.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog_verify_account.show()

        }
    }

    private fun onRegister() {

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        val customToast_positive: View =
            layoutInflater.inflate(R.layout.custom_toast_positive, fl_wrapper_fill_all_fields_toast)

        val username = textInputUsername_edit_text.text.toString()
        val password = textInputPassword_edit_text.text.toString()
        val password2 = textInputConfirmPassword_edit_text.text.toString()
        val email = textInputEmail_edit_text.text.toString()

        validateUsername()
        validateEmail()
        validatePassword()
        validateConfirmPassword()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {

            Toast(this).apply {
                customToast_negative.toast_text.text =
                    getString(R.string.All_fields_are_required)
                view = customToast_negative
            }.show()

            dialog.dismiss()

        } else {
            val nConnection = NetworkConnection(this)
            nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
                if (isConnected) {
                    if (password == password2) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { it ->
                                if (it.isSuccessful) {
                                    val user = mkUser(username, email)
                                    var reference =
                                        mDatabase.child("users").child(it.result!!.user!!.uid)
                                    reference.setValue(user)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                val user = mAuth.currentUser
                                                user?.sendEmailVerification()
                                                    ?.addOnCompleteListener { task ->
                                                        if (task.isSuccessful) {
                                                            val user =
                                                                FirebaseAuth.getInstance().currentUser

                                                            val profileUpdates =
                                                                UserProfileChangeRequest.Builder()
                                                                    .setDisplayName(username)
                                                                    .build()

                                                            user!!.updateProfile(profileUpdates)
                                                            dialog.dismiss()

//                                                            Toast(this).apply {
//                                                                customToast_positive.toast_text.text =
//                                                                    getString(R.string.Successfully_Registered)
//                                                                view = customToast_positive
//                                                            }.show()

                                                            val dialog_register_success =
                                                                this?.let { it1 -> Dialog(it1) }

                                                            dialog_register_success
                                                                .setContentView(R.layout.positive_register_success_email_dialogue)

                                                            val done_button =
                                                                dialog_register_success?.findViewById(
                                                                    R.id.button_register_success_done
                                                                ) as Button

                                                            dialog_register_success.window?.setBackgroundDrawable(
                                                                ColorDrawable(Color.TRANSPARENT)
                                                            )
                                                            dialog_register_success.show()

                                                            done_button.setOnClickListener {
                                                                dialog_register_success.dismiss()
                                                                mAuth.signOut()
                                                                startActivity(
                                                                    Intent(
                                                                        this,
                                                                        LoginActivity::class.java
                                                                    )
                                                                )
                                                                finish()
                                                            }

                                                        }
                                                    }

                                            } else {

                                                dialog.dismiss()

                                                Toast(this).apply {
                                                    customToast_negative.toast_text.text =
                                                        getString(R.string.Something_went_wrong_please_try_again_later_)

                                                    view = customToast_negative
                                                }.show()


                                            }
                                        }

                                } else {
                                    mAuth.fetchSignInMethodsForEmail(email)
                                        .addOnCompleteListener {
                                            try {
                                                val isNewUser: Boolean? =
                                                    it.result?.signInMethods?.isEmpty()
                                                if (!isNewUser!!) {
                                                    dialog.dismiss()

                                                    Toast(this).apply {
                                                        customToast_negative.toast_text.text =
                                                            getString(R.string.Email_already_used_please_try_using_a_different_email)
                                                        view = customToast_negative
                                                    }.show()

                                                }
                                            } catch (e: Exception) {
                                                dialog.dismiss()

                                                Toast(this).apply {
                                                    customToast_negative.toast_text.text =
                                                        getString(R.string.Check_your_email_format)
                                                    view = customToast_negative
                                                }.show()

                                            }

                                        }
                                    if (password.length < 6) {
                                        dialog.dismiss()

                                        Toast(this).apply {

                                            customToast_negative.toast_text.text =
                                                getString(R.string.Please_make_sure_that_your_password_characters_are_6_or_above)
                                            view = customToast_negative

                                        }.show()
                                    }

                                }
                            }
                    } else {
                        dialog.dismiss()

                        Toast(this).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.Make_sure_both_passwords_match)
                            view = customToast_negative
                        }.show()

                    }

                } else {
                    dialog.dismiss()
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.negative_custom_dialogue)
                    val yesBtn = dialog.findViewById(R.id.buttonNegative) as Button
                    yesBtn.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()


                }
            })


        }
    }


    private fun validateUsername() {
        val current = resources.configuration.locale.toString()

        val usernameInput = textInputUsername.editText!!.text.toString().trim()

        if (usernameInput.isEmpty()) {

            textInputUsername.error = getString(R.string.Field_cannot_be_empty)


        } else {
            textInputUsername.error = null
        }

        textInputUsername_edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textInputUsername.error = null
            }

        })

    }

    private fun mkUser(fullName: String, email: String): User {
        val username = mkUsername(fullName)
        return User(name = fullName, username = username, email = email)
    }

    private fun mkUsername(fullName: String) =
        fullName.toLowerCase(Locale.ROOT).replace(" ", ".")


    private fun validateEmail() {

        val emailInput = textInputEmail.editText!!.text.toString().trim()
        if (emailInput.isEmpty()) {

            textInputEmail.error = getString(R.string.Field_cannot_be_empty)


        } else {
            textInputEmail.error = null

        }

        textInputEmail_edit_text.addTextChangedListener(
            object : TextWatcher {

                override fun afterTextChanged(p0: Editable?) {


                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    textInputEmail.error = null
                }

            })


    }

    private fun validatePassword() {

        val passwordInput = textInputPassword.editText!!.text.toString().trim()
        if (passwordInput.isEmpty()) {

            textInputPassword.error = getString(R.string.Field_cannot_be_empty)

        } else {
            textInputPassword.error = null

        }

        textInputPassword_edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textInputPassword.error = null
            }

        })
    }

    private fun validateConfirmPassword() {

        val confirm_passwordInput = textInputConfirmPassword.editText!!.text.toString().trim()
        if (confirm_passwordInput.isEmpty()) {

            textInputConfirmPassword.error = getString(R.string.Field_cannot_be_empty)


        } else {
            textInputConfirmPassword.error = null

        }

        textInputConfirmPassword_edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textInputConfirmPassword.error = null
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

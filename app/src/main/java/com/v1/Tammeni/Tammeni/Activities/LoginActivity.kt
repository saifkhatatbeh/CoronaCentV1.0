package com.v1.Tammeni.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    lateinit var dialog: Dialog
//    lateinit val custom_toast_negative : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val current = resources.configuration.locale.toString()

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        val customToast_positive: View =
            layoutInflater.inflate(R.layout.custom_toast_positive, fl_wrapper_fill_all_fields_toast)

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        login_submit_button.setOnClickListener() {
            dialog = this?.let { Dialog(it) }!!
            dialog.setContentView(R.layout.full_screen_progress_bar)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val email = txtEmail_login.text.toString()
            val password = txtpass_login.text.toString()
            val nConnection = NetworkConnection(this)
            nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
                if (isConnected) {

                    if (validate(email, password)) {
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val user = mAuth.currentUser
                                updateUI(user)
                                dialog.dismiss()
                            } else if (!it.isSuccessful) {

                                Toast(this).apply {
                                    customToast_negative.toast_text.text =
                                        getString(R.string.Wrong_Email_or_Password)
                                    view = customToast_negative
                                }.show()



                                updateUI(null)
                                dialog.dismiss()


                            }
                        }
                    } else {
//                var enter_email_password = getString(R.string.EnterEP)

                        Toast(this).apply {
                            customToast_negative.toast_text.text = getString(R.string.EnterEP)
                            view = customToast_negative

                        }.show()

                        dialog.dismiss()
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

        button_register.setOnClickListener() {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }
        button_skip.setOnClickListener() {
            dialog = this?.let { Dialog(it) }!!
            dialog.setContentView(R.layout.full_screen_progress_bar)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val nConnection = NetworkConnection(this)
            nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
                if (isConnected) {

                    mAuth.signInAnonymously().addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            dialog.dismiss()

                            startActivity(Intent(this, DashboardActivity::class.java))
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast(this).apply {
                                customToast_negative.toast_text.text =
                                    getString(R.string.Email_authentication_failed)
                                view = customToast_negative
                            }.show()

                            dialog.dismiss()

                        }
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
        forgot_password_txt.setOnClickListener {
            val dialog_forgot_password = this?.let { it1 -> Dialog(it1) }

            dialog_forgot_password?.setContentView(R.layout.forgot_password_dialog)

            val noBtn =
                dialog_forgot_password?.findViewById(R.id.closePositive_forgot_password) as ImageView
            val reset_password_button =
                dialog_forgot_password?.findViewById(R.id.button_reset_password) as Button


            noBtn.setOnClickListener {
                dialog_forgot_password.dismiss()
            }
            dialog_forgot_password.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog_forgot_password.show()

            reset_password_button.setOnClickListener {
                val nConnection = NetworkConnection(this)
                nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
                    if (isConnected) {
                        val reset_pass_id =
                            dialog_forgot_password.findViewById(R.id.textInput_Email_forgotP_editText) as TextInputEditText

                        val reset_pass_email = reset_pass_id.editableText.toString()

                        if (reset_pass_email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(
                                reset_pass_email
                            )
                                .matches()
                        ) {

                            Toast(this).apply {
                                customToast_negative.toast_text.text =
                                    getString(R.string.Make_sure_email_is_correct)
                                view = customToast_negative
                            }.show()


                        } else {

                            mAuth.sendPasswordResetEmail(reset_pass_email)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {

                                        Toast(this).apply {
                                            customToast_positive.toast_text.text =
                                                getString(R.string.Email_sent_successfully)
                                            view = customToast_positive
                                        }.show()

                                        dialog_forgot_password.dismiss()

                                    } else {

                                        Toast(this).apply {
                                            customToast_negative.toast_text.text =
                                                getString(R.string.Email_Address_doesnt_exist)
                                            view = customToast_negative
                                        }.show()


                                    }
                                }
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
//        updateUI(currentUser)
    }

    private fun validate(email: String, password: String) =
        email.isNotEmpty() && password.isNotEmpty()

    private fun updateUI(currentUser: FirebaseUser?) {
        val current = resources.configuration.locale.toString()

        val customToast_positive: View =
            layoutInflater.inflate(R.layout.custom_toast_positive, fl_wrapper_fill_all_fields_toast)

        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {

                Toast(this).apply {
                    customToast_positive.toast_text.text =
                        getString(R.string.Please_verify_your_email_address)
                    view = customToast_positive
                }.show()

            }
        }

    }
}

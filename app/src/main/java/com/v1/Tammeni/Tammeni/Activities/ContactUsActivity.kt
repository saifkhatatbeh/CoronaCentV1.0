package com.v1.Tammeni.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.R
import com.v1.Tammeni.data.EmailFormat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_contact_us.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*

class ContactUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        var mFirestore = FirebaseFirestore.getInstance()
        var collectionReference = mFirestore.collection("emails")

        backArrow.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }


        val nConnection = NetworkConnection(this)

        contact_us_button_send.setOnClickListener() {

            nConnection.observe(this, androidx.lifecycle.Observer { isConnected ->
                if (isConnected) {

                    val email = txtEmail.text.toString()
                    val msg = txtMsg.text.toString()
                    val subject = txtSubject.text.toString()
                    val send = EmailFormat(email, subject, msg)

                    if (email.isEmpty() || msg.isEmpty() || subject.isEmpty()) {

                        Toast(this).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.All_fields_are_required)
                            view = customToast_negative
                        }.show()


                    } else {
                        if (isValidEmail(email)) {
                            collectionReference.add(send).addOnSuccessListener {
                                val dialog_contect_us = Dialog(this)

                                dialog_contect_us.setContentView(R.layout.positive_custom_dialogue_sent_email)
                                val noBtn =
                                    dialog_contect_us.findViewById(R.id.closePositive_successSentEmail) as ImageView

                                noBtn.setOnClickListener {
                                    dialog_contect_us.dismiss()
                                }

                                val button_return_dashbaord =
                                    dialog_contect_us.findViewById(R.id.button_emailsentgo_to_dashboard) as Button

                                button_return_dashbaord.setOnClickListener() {
                                    startActivity(
                                        Intent(
                                            this,
                                            DashboardActivity::class.java
                                        )
                                    )
                                    finish()
                                }

                                dialog_contect_us.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                dialog_contect_us.show()

                            }

                        } else {

                            Toast(this).apply {
                                customToast_negative.toast_text.text =
                                    getString(R.string.Check_your_email_format)
                                view = customToast_negative
                            }.show()


                        }

                    }


                } else {
                    Toast(this).apply {
                        customToast_negative.toast_text.text =
                            getString(R.string.No_Internet_Connection)
                        view = customToast_negative
                    }.show()

                }
            })
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

}
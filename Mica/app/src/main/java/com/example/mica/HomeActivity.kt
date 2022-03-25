package com.example.mica

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bt_chat.setOnClickListener(this)
        bt_appointment.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            bt_chat.id -> {
                val nextActivityIntent = Intent(applicationContext, ChatActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            bt_appointment.id -> {
                val nextActivityIntent = Intent(applicationContext, ClinicActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }
}
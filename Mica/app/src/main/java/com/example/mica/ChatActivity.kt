package com.example.mica

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mica.Constants.BOT
import com.example.mica.Constants.USER
import com.example.mica.adapter.ChatAdapter
import com.example.mica.api.ChatApiClient
import com.example.mica.model.Chat
import com.example.mica.model.SendMessageRequest
import com.example.mica.model.SendMessageResponse
import kotlinx.android.synthetic.main.activity_chat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()

        tv_back.setOnClickListener(this)
        bt_send.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            tv_back.id -> {
                val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            bt_send.id -> {
                if (et_message.text.isNotBlank() || et_message.text.isNotEmpty()) {
                    sendMessage(et_message.text.toString())
                }
            }
        }
    }

    override fun onBackPressed() {
        val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
        finish()
        startActivity(nextActivityIntent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(text: String) {
        et_message.text.clear()
        val timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        val request = SendMessageRequest("user", text)
        adapter.addChat(Chat(text, timestamp, USER))

        if (adapter.itemCount < 9) { // temp handler for redirection
        val call: Call<SendMessageResponse> = ChatApiClient.post.sendMessage(request)

        call.enqueue(object : Callback<SendMessageResponse> {
            override fun onResponse(call: Call<SendMessageResponse>, response: Response<SendMessageResponse>) {
                when (val statusCode = response.code()) {
                    200 -> {
                        val responseBody: SendMessageResponse = response.body()!!
                        adapter.addChat(Chat(responseBody.message, responseBody.msgRsp, BOT))
                        rv_messages.scrollToPosition(adapter.itemCount-1)
                    }
                    else -> Toast.makeText(applicationContext, "Error: $statusCode: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SendMessageResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to connect to server", Toast.LENGTH_SHORT).show()
            }
        })
    }
        if (adapter.itemCount > 8 && text.equals("yes", true)) {
            Handler().postDelayed({
                val intent = Intent(this, ClinicActivity::class.java)
                finish()
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)}, 1500)
        }
    }

    private fun init() {
        adapter = ChatAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }
}
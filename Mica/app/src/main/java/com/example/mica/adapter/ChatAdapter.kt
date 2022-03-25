package com.example.mica.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mica.Constants.BOT
import com.example.mica.Constants.USER
import com.example.mica.R
import com.example.mica.model.Chat
import kotlinx.android.synthetic.main.item_message_box.view.*

class ChatAdapter: RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    var chats = mutableListOf<Chat>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message_box, parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentChat = chats[position]

        when (currentChat.from) {
            USER -> {
                holder.itemView.tv_message_send.apply {
                    text = currentChat.text
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message_receive.visibility = View.GONE
            }

            BOT -> {
                holder.itemView.tv_message_receive.apply {
                    text = currentChat.text
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message_send.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun addChat(chat: Chat) {
        this.chats.add(chat)
        notifyItemInserted(chats.size)
    }
}
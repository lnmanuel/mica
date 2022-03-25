package com.example.mica.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mica.R
import com.example.mica.model.Clinic
import kotlinx.android.synthetic.main.item_clinic_card.view.*

class ClinicAdapter(val listener: onItemClickListener) : RecyclerView.Adapter<ClinicAdapter.ClinicViewHolder>() {

    var clinics = mutableListOf<Clinic>()

    inner class ClinicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        return ClinicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_clinic_card, parent, false))
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        val currentClinic = clinics[position]

        holder.itemView.tv_clinic_name.apply {
            text = currentClinic.clinicName
        }
        holder.itemView.tv_address.apply {
            text = currentClinic.address
        }
        holder.itemView.tv_doctor.apply {
            text = currentClinic.doctor
        }
    }

    override fun getItemCount(): Int {
        return clinics.size
    }

    fun addClinic(clinic: Clinic) {
        this.clinics.add(clinic)
        notifyItemInserted(clinics.size)
    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
}
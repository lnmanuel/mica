package com.example.mica

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mica.adapter.ClinicAdapter
import com.example.mica.api.ClinicApiClient
import com.example.mica.fragments.MapFragment
import com.example.mica.model.*
import kotlinx.android.synthetic.main.activity_clinic.*
import kotlinx.android.synthetic.main.activity_clinic.tv_back
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClinicActivity : AppCompatActivity(), View.OnClickListener, ClinicAdapter.onItemClickListener {

    private val adapter = ClinicAdapter(this)
    private val clinics = adapter.clinics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clinic)
        init()

        tv_back.setOnClickListener(this)
        bt_book.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            tv_back.id -> {
                val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            bt_book.id -> {
                val nextActivityIntent = Intent(applicationContext, ConfirmActivity::class.java)
                finish()
                startActivity(nextActivityIntent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun onBackPressed() {
        val nextActivityIntent = Intent(applicationContext, HomeActivity::class.java)
        finish()
        startActivity(nextActivityIntent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun init() {
        rv_clinics.adapter = adapter
        rv_clinics.layoutManager = LinearLayoutManager(applicationContext)

        getClinics()
    }

    private fun getClinics() {
        val request = GetClinicsRequest("GENERAL_PHY")

        val call: Call<List<Clinic>> = ClinicApiClient.post.getClinics(request)

        call.enqueue(object : Callback<List<Clinic>> {
            override fun onResponse(call: Call<List<Clinic>>, response: Response<List<Clinic>>) {
                when (val statusCode = response.code()) {
                    200 -> {
                        val responseBody: List<Clinic> = response.body()!!
                        for (clinic in responseBody) {
                            adapter.addClinic(
                                Clinic(clinic.uid, clinic.clinicName, clinic.address, clinic.doctor, clinic.longLat)
                            )
                        }
                    }
                    else -> Toast.makeText(applicationContext, "Error: $statusCode: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Clinic>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to connect to server: $t", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onItemClick(position: Int) {
        val clickedItem: Clinic = clinics[position]
        showSearchResult(clickedItem.clinicName, clickedItem.longLat)
    }

    private fun showSearchResult(name: String, longLat: String) {
        MapFragment.newInstance(name, longLat).show(supportFragmentManager, MapFragment.TAG)
    }
}
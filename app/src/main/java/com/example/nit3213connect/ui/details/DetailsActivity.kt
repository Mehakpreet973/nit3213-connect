package com.example.nit3213connect.ui.details

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nit3213connect.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvSubtitle = findViewById<TextView>(R.id.tvSubtitle)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)

        tvTitle.text = intent.getStringExtra("title") ?: ""
        tvSubtitle.text = intent.getStringExtra("subtitle") ?: ""
        tvDescription.text = intent.getStringExtra("description") ?: ""
    }
}

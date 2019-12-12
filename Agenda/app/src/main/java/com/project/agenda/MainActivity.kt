package com.project.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnRRHH.setOnClickListener()
        {
            val intent: Intent = Intent(this, Recycler::class.java)
            startActivity(intent)
        }

        btnAgregar.setOnClickListener()
        {
            val intent: Intent = Intent(this, AgregarEmpleado::class.java)
            startActivity(intent)
        }

    }
}

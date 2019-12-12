package com.project.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_empleado.*

class AgregarEmpleado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_empleado)


        btnGuardar.setOnClickListener()
        {
            if(editEmpleado.text.isEmpty())
            {
                Toast.makeText(this,"Ingrese Empleado", Toast.LENGTH_LONG).show()
                editEmpleado.requestFocus()
            }
            else
            {
                val customer = Customer()
                customer.customerName = editEmpleado.text.toString()
                if(editSueldo.text.isEmpty())
                    customer.maxCredit = 0.0 else
                    customer.maxCredit = editSueldo.text.toString().toDouble()
                Recycler.dbHandler.addCustomer(this, customer)
                ClearEdits()
                editEmpleado.requestFocus()

            }
        }


        btnCancelar.setOnClickListener()
        {
            ClearEdits()
            finish()
        }

    }



    private fun ClearEdits()
    {
        editEmpleado.text.clear()
        editSueldo.text.clear()
    }


}

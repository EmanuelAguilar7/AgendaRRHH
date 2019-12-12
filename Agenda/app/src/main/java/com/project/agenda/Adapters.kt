package com.project.agenda

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_actualizar_empleado.view.*
import kotlinx.android.synthetic.main.activity_agregar_empleado.view.*
import kotlinx.android.synthetic.main.activity_lo_customers.view.*

class CustomerAdapter(mCtx : Context, val customers: ArrayList<Customer>) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>()
{

    /*

    val mCtx = mCtx


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val txtEmple = itemView.txtEmple
        val txtsueldo = itemView.txtSuel
        val btnActualizar = itemView.btnActualizar
        val btnEliminar = itemView.btnEliminar
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_lo_customers, parent, false)

        return  ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  customers.size
    }

    override fun onBindViewHolder(holder: CustomerAdapter.ViewHolder, position: Int) {

        val customer: Customer = customers[getItemViewType(1)]
        parent




    }



     */



    val mCtx = mCtx


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val txtEmple = itemView.txtEmple
        val txtsueldo = itemView.txtSuel
        val btnActualizar = itemView.btnActualizar
        val btnEliminar = itemView.btnEliminar
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomerAdapter.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.activity_lo_customers, p0, false)

        return  ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return  customers.size
    }

    override fun onBindViewHolder(p0: CustomerAdapter.ViewHolder, p1: Int) {

        val customer : Customer = customers[p1]
        p0.txtEmple.text = customer.customerName
        p0.txtsueldo.text = customer.maxCredit.toString()


        p0.btnEliminar.setOnClickListener()
        {
            val customerName = customer.customerName

            var alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Peligro")
                .setMessage("¿Estas Seguro De Eliminar Ah: $customerName ?")
                .setPositiveButton("Si", DialogInterface.OnClickListener { dialog, which ->
                    if (Recycler.dbHandler.deleteCustomer(customer.customerID))
                    {
                        customers.removeAt(p1)
                        notifyItemRemoved(p1)
                        notifyItemRangeChanged(p1,customers.size)
                        Toast.makeText(mCtx, "Empleado(a) $customerName Borrado",Toast.LENGTH_LONG).show()

                        }else{

                        Toast.makeText(mCtx,"Error Al Borrar",Toast.LENGTH_LONG).show()
                    }
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
                .show()




            p0.btnActualizar.setOnClickListener()
            {
                val inflater = LayoutInflater.from(mCtx)
                val view = inflater.inflate(R.layout.activity_actualizar_empleado, null)

                val txtCustName : TextView = view.findViewById(R.id.editUPEmpleado)
                val txtMaxCredit : TextView = view.findViewById(R.id.editUPSueldo)

                txtCustName.text = customer.customerName
                txtMaxCredit.text = customer.maxCredit.toString()


                val builder = AlertDialog.Builder(mCtx)
                    .setTitle("Empleado(a) Actualizado")
                    .setView(view)
                    .setPositiveButton("Actualizar", DialogInterface.OnClickListener { dialog, which ->

                        val isUpdate : Boolean = Recycler.dbHandler.updateCustomer(
                            customer.customerID.toString(),
                            view.editUPEmpleado.text.toString(),
                            view.editUPSueldo.text.toString()
                        )

                        if(isUpdate == true)
                        {
                            customers[p1].customerName = view.editUPEmpleado.text.toString()
                            customers[p1].maxCredit = view.editUPSueldo.text.toString().toDouble()
                            notifyDataSetChanged()
                            Toast.makeText(mCtx,"Actualizacion Exitosa",Toast.LENGTH_LONG).show()

                        } else
                        {
                            Toast.makeText(mCtx,"Error Al Actualizar",Toast.LENGTH_LONG).show()
                        }




                    }).setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->

                    })

                val alert = builder.create()
                alert.show()

            }

        }

    }

}
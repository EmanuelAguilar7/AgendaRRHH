package com.project.agenda

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class DBHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    companion object {
        private val DATABASE_NAME = "MyData.db"
        private val DATABASE_VERSION = 1

        val CUSTOMER_TABLE_NAME = "customers"
        val COLUMN_CUSTOMERID = "customerid"
        val COLUMN_CUSTOMERNAME = "customername"
        val COLUMN_MAXCREDIT = "maxcredit"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CUSTOMER_TABLE = ("CREATE TABLE $CUSTOMER_TABLE_NAME ("
                + "$COLUMN_CUSTOMERID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_CUSTOMERNAME TEXT," +
                "$COLUMN_MAXCREDIT DOUBLE DEFAULT 0)"
                )

        db?.execSQL(CREATE_CUSTOMER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    fun getCustomers(mCtx: Context): ArrayList<Customer> {
        val qry = "Select * from $CUSTOMER_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val customers = ArrayList<Customer>()


        if (cursor.count == 0)
            Toast.makeText(mCtx, "Recursos No Encontrados", Toast.LENGTH_LONG).show() else {

            cursor.moveToFirst()

            while (!cursor.isAfterLast()) {
                val customer = Customer()
                customer.customerID = cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMERID))
                customer.customerName = cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMERNAME))
                customer.maxCredit = cursor.getDouble(cursor.getColumnIndex(COLUMN_MAXCREDIT))
                customers.add(customer)

                cursor.moveToNext()

            }

            Toast.makeText(mCtx, "${cursor.count.toString()} Records Found", Toast.LENGTH_LONG)
                .show()

        }

        cursor.close()
        db.close()
        return customers
    }


    fun addCustomer(mCtx: Context, customer: Customer)
    {
        val values = ContentValues()
        values.put(COLUMN_CUSTOMERNAME, customer.customerName)
        values.put(COLUMN_MAXCREDIT, customer.maxCredit)
        val db = this.writableDatabase

        try {
            db.insert(CUSTOMER_TABLE_NAME, null,values)
            //db.rawQuery("Insert Into $CUSTOMER_TABLE_NAME ($COLUMN_CUSTOMERNAME, $COLUMN_MAXCREDIT) Values(?,?)")
            Toast.makeText(mCtx,"Empleado Agregado", Toast.LENGTH_LONG).show()
        }catch (e:Exception)
        {
            Toast.makeText(mCtx, e.message, Toast.LENGTH_LONG).show()
        }

        db.close()

    }



    fun deleteCustomer(customerId: Int) : Boolean
    {
        val qry = "Delete From $CUSTOMER_TABLE_NAME Where $COLUMN_CUSTOMERID = $customerId"
        val db= this.writableDatabase
        var result : Boolean = false
        try {
          //  val cursor= db.delete(CUSTOMER_TABLE_NAME, "$COLUMN_CUSTOMERID = ?", arrayOf(customerId.toString()))
            val cursor = db.execSQL(qry)
            result = true

        }catch (e: Exception)
        {
            Log.e(ContentValues.TAG, "Error Al Borrar")

        }

        db.close()
        return result
    }



    fun updateCustomer(id: String, customerName: String, maxCredit: String) : Boolean
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        var  result : Boolean = false
        contentValues.put(COLUMN_CUSTOMERNAME, customerName)
        contentValues.put(COLUMN_MAXCREDIT, maxCredit.toDouble())

        try {
            db.update(CUSTOMER_TABLE_NAME, contentValues, "$COLUMN_CUSTOMERID = ?", arrayOf(id))
            result = true
        }catch (e : Exception)
        {
            Log.e(ContentValues.TAG, "Error Al Actualizar")
            result = false
        }
        return result
    }

}

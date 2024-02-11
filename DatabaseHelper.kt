package com.example.jose_cavero_semana6

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "InfraccionesDB"
        private const val TABLE_INFRACCIONES = "Infracciones"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FOLIO = "folio"
        private const val COLUMN_RUT = "rut"
        private const val COLUMN_COMERCIO_NAME = "comercio_name"
        private const val COLUMN_COMERCIO_ADDRESS = "comercio_address"
        private const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_INFRACCIONES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_FOLIO TEXT UNIQUE, " +
                "$COLUMN_RUT TEXT, " +
                "$COLUMN_COMERCIO_NAME TEXT, " +
                "$COLUMN_COMERCIO_ADDRESS TEXT, " +
                "$COLUMN_DESCRIPCION TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INFRACCIONES")
        onCreate(db)
    }

    fun addInfraccion(rut: String, comercioName: String, comercioAddress: String, descripcion: String) {
        val folio = generateFolio()
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FOLIO, folio)
            put(COLUMN_RUT, rut)
            put(COLUMN_COMERCIO_NAME, comercioName)
            put(COLUMN_COMERCIO_ADDRESS, comercioAddress)
            put(COLUMN_DESCRIPCION, descripcion)
        }
        val newRowId = db.insert(TABLE_INFRACCIONES, null, values)
        if (newRowId > 0) {
            // Insertar exitoso
        } else {
            // Insertar fallido
        }
        db.close()
    }

    fun getLastFolio(): String? {
        val db = this.readableDatabase
        var folio: String? = null
        val query = "SELECT $COLUMN_FOLIO FROM $TABLE_INFRACCIONES ORDER BY $COLUMN_ID DESC LIMIT 1"
        val cursor = db.rawQuery(query, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                folio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOLIO))
            }
        }
        cursor?.close()
        db.close()
        return folio
    }

    fun updateInfraccion(folio: String, rut: String, comercioName: String, comercioAddress: String, descripcion: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_RUT, rut)
            put(COLUMN_COMERCIO_NAME, comercioName)
            put(COLUMN_COMERCIO_ADDRESS, comercioAddress)
            put(COLUMN_DESCRIPCION, descripcion)
        }
        db.update(TABLE_INFRACCIONES, contentValues, "$COLUMN_FOLIO = ?", arrayOf(folio))
        db.close()
    }

    fun getAllInfracciones(): List<Infraccion> {
        val infracciones = mutableListOf<Infraccion>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_INFRACCIONES"
        val cursor = db.rawQuery(query, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val folio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOLIO))
                    val rut = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUT))
                    val comercioName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMERCIO_NAME))
                    val comercioAddress = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMERCIO_ADDRESS))
                    val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))

                    val infraccion = Infraccion(folio, rut, comercioName, comercioAddress, descripcion)
                    infracciones.add(infraccion)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        db.close()
        return infracciones
    }

    private fun generateFolio(): String {
        val letras = ('A'..'Z').toList()
        val numeros = (0..9).toList()

        val folio = buildString {
            repeat(2) {
                append(letras.random())
            }
            repeat(6) {
                append(numeros.random())
            }
        }
        return folio
    }
}


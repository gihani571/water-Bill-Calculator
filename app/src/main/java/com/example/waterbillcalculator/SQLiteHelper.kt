package com.example.waterbillcalculator


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "water.db"
        private const val TBL_WATER = "tbl_water"
        private const val ID  = "id"
        private const val NO_DAYS = "nd"
        private const val UNIT = "un"
        private const val TOTAL = "tot"


    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTblWATER = ("CREATE TABLE " + TBL_WATER + "("
                + ID + " INTEGER PRIMARY KEY," + NO_DAYS + " INTEGER," + UNIT + " INTEGER," + TOTAL + " DOUBLE" + ")")

        p0?.execSQL(createTblWATER)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TBL_WATER")
        onCreate(p0)
    }

    fun insertBill(wat: WaterModel): Long{
        val p0 = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, wat.id)
        contentValues.put(NO_DAYS, wat.nd)
        contentValues.put(UNIT, wat.un)
        contentValues.put(TOTAL, wat.tot)

        val success = p0.insert(TBL_WATER, null, contentValues)
        p0.close()
        return success
    }


    @SuppressLint("Range")
    fun getBill(): ArrayList<WaterModel>{
        val watList: ArrayList<WaterModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_WATER"
        val p0 = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = p0.rawQuery(selectQuery, null)
        } catch(e: Exception){
            e.printStackTrace()
            p0.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var nd: Int
        var un: Int
        var tot: Double

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                nd = cursor.getInt(cursor.getColumnIndex("nd"))
                un = cursor.getInt(cursor.getColumnIndex("un"))
                tot = cursor.getDouble(cursor.getColumnIndex("tot"))

                val wat = WaterModel(id = id, nd = nd, un = un, tot = tot)
                watList.add(wat)
            } while (cursor.moveToNext())
        }

        return watList
    }

    fun updateBill(wat: WaterModel) : Int{
        val p0 = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, wat.id)
        contentValues.put(NO_DAYS, wat.nd)
        contentValues.put(UNIT, wat.un)
        contentValues.put(TOTAL, wat.tot)

        val success = p0.update(TBL_WATER, contentValues,"id=" + wat .id, null)
        p0.close()
        return success
    }

    fun deleteBillById(id:Int): Int{
        val p0 = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = p0.delete(TBL_WATER, "id=$id", null)
        p0.close()
        return success
    }
}


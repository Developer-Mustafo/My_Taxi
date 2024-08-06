package uz.coder.mytaxi.location.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.coder.mytaxi.models.TaxiDbModel

@Database(entities = [TaxiDbModel::class], version = 1, exportSchema = false)
abstract class MyTaxiDatabase:RoomDatabase() {
    abstract fun taxiDao(): TaxiDao
    companion object{
        private const val DATABASE_NAME = "taxi_db"
        private val LOCK = Any()
        private var db: MyTaxiDatabase? = null
        fun getInstance(context: Context): MyTaxiDatabase {
            db?.let {
                return it
            }
            synchronized(LOCK){
                db?.let {
                    return it
                }
            }
            val instance = Room.databaseBuilder(
                context,
                MyTaxiDatabase::class.java,
                DATABASE_NAME
            ).build()
            db = instance
            return instance
        }
    }
}
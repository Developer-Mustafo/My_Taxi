package uz.coder.mytaxi.data.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaxiDbModel::class], version = 1, exportSchema = false)
abstract class TaxiDatabase:RoomDatabase() {
    abstract fun taxiDao(): TaxiDao
    companion object{
        private const val DATABASE_NAME = "taxi_db"
        private val LOCK = Any()
        private var db: TaxiDatabase? = null
        fun getInstance(application: Application): TaxiDatabase {
            db?.let {
                return it
            }
            synchronized(LOCK){
                db?.let {
                    return it
                }
            }
            val instance = Room.databaseBuilder(
                application,
                TaxiDatabase::class.java,
                DATABASE_NAME
            ).build()
            db = instance
            return instance
        }
    }
}
package uz.coder.mytaxi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaxiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taxi: TaxiDbModel)

    @Query("SELECT max(id) as id, longitude, altitude, latitude FROM taxi")
    fun getLastLocation(): Flow<TaxiDbModel>
}
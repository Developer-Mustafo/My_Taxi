package uz.coder.mytaxi.location.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.coder.mytaxi.location.models.TaxiDbModel

@Dao
interface TaxiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taxi: TaxiDbModel)

    @Query("SELECT * FROM taxi order by id desc limit 1")
    fun getLastLocation(): Flow<TaxiDbModel>
}
package com.example.ceep3.database.dao

import androidx.room.*
import com.example.ceep3.model.Nota

@Dao
interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(nota: Nota)

    @Delete
    suspend fun delete(nota: Nota)

    @Update
    suspend fun edita(nota: Nota)

    @Query("SELECT * FROM nota")
    suspend fun todos(): List<Nota>
}
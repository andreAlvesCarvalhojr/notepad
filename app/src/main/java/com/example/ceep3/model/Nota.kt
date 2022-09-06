package com.example.ceep3.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Nota(
    val titulo: String,
    val descricao: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

}
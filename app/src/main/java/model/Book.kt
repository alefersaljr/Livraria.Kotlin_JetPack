package model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "book_table")
data class Book (@PrimaryKey @NonNull val name: String,
                 @NonNull val genero: String) {
}

//(@PrimaryKey(autoGenerate = true) val id: Int,
//@NonNull val name: String,
//@NonNull val genero: String,
//@NonNull val autor: String,
//@NonNull val preco: Int)
package model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "table_books")
data class Book (
//                 @PrimaryKey @NonNull val id: Int,
                 @PrimaryKey @NonNull val name: String,
                 @NonNull val genero: String,
                 @NonNull val autor: String,
                 @NonNull val preco: String
) {
}
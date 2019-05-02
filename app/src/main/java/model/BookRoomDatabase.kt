package model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database (entities = [Book::class], version = 1)
abstract class BookRoomDatabase : RoomDatabase() {
    abstract fun bookDao () : BookDao

    companion object{
        @Volatile
        var dbInstance: BookRoomDatabase? = null

        var dbName = "books_db"

        fun getInstance(context: Context): BookRoomDatabase {
            val tempInstance = dbInstance
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, BookRoomDatabase::class.java, dbName).build()
                dbInstance = instance
                return instance
            }
        }
    }
}
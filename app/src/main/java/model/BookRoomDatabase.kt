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
        var database: BookRoomDatabase? = null

        fun getInstance(context: Context): BookRoomDatabase? {
            if(database == null){
                synchronized(BookRoomDatabase::class.java){
                    if (database == null) {
                        database = Room.databaseBuilder(context.applicationContext, BookRoomDatabase::class.java, "book_database").build()
                    }
                }
            }
            return database
        }
    }
}
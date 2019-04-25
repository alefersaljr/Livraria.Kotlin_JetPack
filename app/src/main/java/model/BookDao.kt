package model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Query("DELETE FROM table_books")
    fun delereAllBooks()

    @Query("SELECT * FROM table_books ORDER BY id ASC")
    fun getAllBooks(): LiveData<List<Book>>

//    @Query("SELECT * FROM table_books WHERE name = :name")
//    fun getBookById(name: String): Book

    @Query("SELECT * FROM table_books WHERE id = :id")
    fun getBookById(id: Int): Book

    @Query("SELECT COUNT (*) FROM table_books")
    fun getNumOfBooks(): LiveData<Int>?
}
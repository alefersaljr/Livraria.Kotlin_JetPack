package model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Query("DELETE FROM book_table")
    fun delereAllBooks()

    @Query("SELECT * FROM book_table ORDER BY name ASC")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM book_table WHERE name = :name")
    fun getBookByName(name: String): Book

    @Query("SELECT COUNT (*) FROM book_table")
    fun getNumOfBooks(): LiveData<Int>
}
package model

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class BookRepository (application: Application) {
    private val bookDao:BookDao?
    private val allBooks: LiveData<List<Book>>?

//    private val numberRegisters: LiveData<Int>?

    init {
        val db = BookRoomDatabase.getInstance(application)
        bookDao = db.bookDao()
        allBooks = bookDao.getAllBooks()
//        numberRegisters = bookDao.getNumOfBooks()
    }

    fun insertBook(book: Book){
        InsertAsyncTask(bookDao!!).execute(book)
    }

    fun deleteBook(book: Book){
        DeleteAsyncTask(bookDao!!).execute(book)
    }

    fun delereAllBooks(){
        DeleteAllAsyncTask(bookDao!!).execute()
    }

    fun getAllBooks(): LiveData<List<Book>>{
        return allBooks!!
    }

    fun getBookById(id: String): Book? {
        val allBookList = allBooks?.value?.toList()

        allBookList?.iterator()?.forEach {
            if (it.id == id){
                return it
            }
        }
        return null
    }

    private class InsertAsyncTask(private val dao: BookDao) :AsyncTask<Book, Void, Void>() {
        override fun doInBackground(vararg params: Book): Void? {
            dao.insertBook(params[0])
            return null
        }
    }

    private class DeleteAsyncTask(private val dao: BookDao) :AsyncTask<Book, Void, Void>() {
        override fun doInBackground(vararg params: Book): Void? {
            dao.deleteBook(params[0])
            return null
        }
    }

    private class DeleteAllAsyncTask(private val dao: BookDao) :AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            dao.delereAllBooks()
            return null
        }
    }
}
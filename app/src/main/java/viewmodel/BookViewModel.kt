package viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import model.Book
import model.BookRepository

class BookViewModel(application: Application): AndroidViewModel(application) {
    private val bookRepository:BookRepository
    private val allBooks: LiveData<List<Book>>

    init {
        bookRepository = BookRepository(application)
        allBooks = bookRepository.getAllBooks()
    }

    fun insertBook(book: Book){
        bookRepository.insertBook(book)
    }

    fun deleteBook(book: Book){
        bookRepository.deleteBook(book)
    }

    fun delereAllBooks(){
        bookRepository.delereAllBooks()
    }

    fun getAllBooks() = allBooks

    fun getBookById(id: String): Book? {
        return bookRepository.getBookById(id)
    }

//    fun getNumOfBooks(): LiveData<Int>? {
//        return bookRepository.getNumOfBooks()
//    }
}
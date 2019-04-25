package viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import model.Book
import model.BookRepository

class BookViewModel(application: Application): AndroidViewModel(application) {
    private val bookRepository:BookRepository
    private val allBooks: LiveData<List<Book>>
//    private val numberRegisters: LiveData<Int>?

    init {
        bookRepository = BookRepository(application)
        allBooks = bookRepository.getAllBooks()
//        numberRegisters = bookRepository.getNumberRegisters()
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

    fun getBookByName(name: String): Book? {
        return bookRepository.getBookByName(name)
    }

//    fun getNumberRegisters(): LiveData<Int>? {
//        return bookRepository.getNumberRegisters()
//    }
}
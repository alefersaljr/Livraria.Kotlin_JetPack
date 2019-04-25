package view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.com.alexandre_salgueirinho.library_kotlin.R
import br.com.alexandre_salgueirinho.library_kotlin.utils.*

import kotlinx.android.synthetic.main.activity_main.*
import model.Book
import viewmodel.BookViewModel

class MainActivity : AppCompatActivity(), BookListAdapter.ItemClickListener {

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, NewBookActivity::class.java)
        intent.putExtra(EXTRA_KEY_BOOK, mAdapter.getBooks()[position].name)
        intent.putExtra(EXTRA_KEY_GENERO, mAdapter.getBooks()[position].genero)
        startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: BookListAdapter
    private var mBooks: List<Book> = mutableListOf<Book>()
    private lateinit var mBookViewModel: BookViewModel
//    private lateinit var mNumber: BookViewModel
//    private lateinit var mNumberRegisters: TextView
    private lateinit var mNumberRegisters: TextView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)





        mRecyclerView = findViewById(R.id.recyclerView)
//        mNumberRegisters = findViewById(R.id.number_content)
        mAdapter = BookListAdapter(this, this)
        mAdapter.setBooks(mBooks)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)
//        mNumber = ViewModelProviders.of(this).get(BookViewModel::class.java)

        mBookViewModel.getAllBooks().observe(this, Observer { books ->
            books?.let {
                mAdapter.setBooks(it)
            }
        })

//        mNumber.getNumberRegisters().toString()
//        mNumberRegisters.setText(mNumber.getNumberRegisters().toString())

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_SAVE){
            data?.let {
                val book = Book(it.getStringExtra(EXTRA_KEY_BOOK), it.getStringExtra(EXTRA_KEY_GENERO))
                mBookViewModel.insertBook(book)
//                mNumberRegisters.setText(mAdapter.getItemCounts())
            }
        }else if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_DELETE){
            data?.let {
                val book = mBookViewModel.getBookByName(it.getStringExtra(EXTRA_KEY_BOOK))
                mBookViewModel.deleteBook(book!!)
                Toast.makeText(this, getString(R.string.word_deleted), Toast.LENGTH_SHORT).show()
            }
        }else if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_ERROR){
            Toast.makeText(this, getString(R.string.empty_book_not_saved), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_list -> {
                clearListAction()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearListAction() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage(getString(R.string.confirmacao_excluir_lista))

        builder.setPositiveButton(getString(R.string.dialog_sim)) {
            dialog, _ ->
            mBookViewModel.delereAllBooks()
            Toast.makeText(this, getString(R.string.toast_lista_excluida), Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(getString(R.string.dialog_nao)) {
            dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}

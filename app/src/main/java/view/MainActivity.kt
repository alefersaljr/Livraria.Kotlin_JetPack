package view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
        val intent = Intent(this, UpdateBookActivity::class.java)
        intent.putExtra(EXTRA_KEY_ID, mAdapter.getBooks()[position].id)
        intent.putExtra(EXTRA_KEY_BOOK_NAME, mAdapter.getBooks()[position].name)
        intent.putExtra(EXTRA_KEY_GENERO, mAdapter.getBooks()[position].genero)
        intent.putExtra(EXTRA_KEY_AUTOR, mAdapter.getBooks()[position].autor)
        intent.putExtra(EXTRA_KEY_PRECO, mAdapter.getBooks()[position].preco)
        startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: BookListAdapter
    private var mBooks: List<Book> = mutableListOf<Book>()
    private lateinit var mBookViewModel: BookViewModel
    private lateinit var mTempo: TextView
    private lateinit var mNumberRegisters: TextView
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mTempo = findViewById(R.id.tempo_execucao_txt)
        mNumberRegisters = findViewById(R.id.total_txt)
        mRecyclerView = findViewById(R.id.recyclerView)
        mAdapter = BookListAdapter(this, this)
        mAdapter.setBooks(mBooks)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mBookViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)

        mBookViewModel.getAllBooks().observe(this, Observer { books ->
            books?.let {
                var tempoIni = System.currentTimeMillis()

                mAdapter.setBooks(it)
                mNumberRegisters.text = mAdapter.itemCount.toString()

                var tempoFim = System.currentTimeMillis()
                var tempoExecucao = (tempoFim - tempoIni).toString()
                mTempo.text = tempoExecucao
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_SAVE) {
            data?.let {
                val book = Book(
                    (it.getStringExtra(EXTRA_KEY_ID)),
                    it.getStringExtra(EXTRA_KEY_BOOK_NAME),
                    it.getStringExtra(EXTRA_KEY_GENERO),
                    it.getStringExtra(EXTRA_KEY_AUTOR),
                    it.getStringExtra(EXTRA_KEY_PRECO)
                )
                mBookViewModel.insertBook(book)
            }
        } else if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_DELETE) {
            data?.let {
                //                val book = mBookViewModel.getBookById(it.getStringExtra(EXTRA_KEY_BOOK_NAME))
                val book = mBookViewModel.getBookById(it.getStringExtra(EXTRA_KEY_ID))
                book?.let {
                    mBookViewModel.deleteBook(book)
                }
                Toast.makeText(this, getString(R.string.book_deleted_label), Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_ERROR) {
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
            R.id.action_add_book -> {
                val intent = Intent(this, NewBookActivity::class.java)
                startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearListAction() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage(getString(R.string.confirmacao_excluir_lista))

        builder.setPositiveButton(getString(R.string.dialog_sim)) { _, _ ->
            mBookViewModel.delereAllBooks()
            Toast.makeText(this, getString(R.string.toast_livros_excluidos), Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(getString(R.string.dialog_nao)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

//    suspend fun consultaRegistros() {
//        mBookViewModel.getAllBooks().observe(this, Observer { books ->
//            books?.let {
//                mAdapter.setBooks(it)
//                mNumberRegisters.text = mAdapter.itemCount.toString()
//            }
//        })
//    }
//
//    fun asyncConsultaRegistros () = async (CommonPool) {
//
//    }
//
//    fun consulta() {
//        doAsync {
//            var i = 0
//            while (i < Int.MAX_VALUE) {
//                var tempoIni = System.currentTimeMillis()
//                mBookViewModel.getAllBooks().observe(this@MainActivity, Observer { books ->
//                    books?.let {
//                        mAdapter.setBooks(it)
//                        mNumberRegisters.text = mAdapter.itemCount.toString()
//                    }
//                })
//                var tempoFim = System.currentTimeMillis()
//                var tempoExecucao = (tempoFim - tempoIni).toString()
//                uiThread {
//                    mTempo.text = tempoExecucao
//                    Thread.sleep(5000)
//                }
//                i++
//            }
//        }
//    }
//
//    fun consultaAssincrona() = runBlocking {
//        var i = 0
//        do {
//            var tempoIni = System.currentTimeMillis()
//            async { consulta() }
//
//            var tempoFim = System.currentTimeMillis()
//            var tempoExecucao = String.format("%.2f", (tempoFim.toDouble() - tempoIni.toDouble()))
//            mTempo.text = tempoExecucao
//
//            i++
//        } while (i < Int.MAX_VALUE)
//    }
//
//    fun consultaAssincrona2() = runBlocking {
//        val custonDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
//
//        launch(custonDispatcher) {
//            var i = 0
//            do {
//                var tempoIni = System.currentTimeMillis()
//
//                async { consulta() }
//
//                var tempoFim = System.currentTimeMillis()
//                var tempoExecucao = String.format("%.2f", (tempoFim.toDouble() - tempoIni.toDouble()))
//                mTempo.text = tempoExecucao
//
//                i++
//            } while (i < Int.MAX_VALUE)
//        }
//    }
//
//    internal inner class getConsulta : AsyncTask<Void, Void, Long?>() {
//        var tempoInicial: Long = 0
//        var tempoFinal: Long = 0
//        var tempoExecusao: Long = 0
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//            tempoInicial = System.currentTimeMillis()
//        }
//
//        override fun doInBackground(vararg p0: Void?): Long? {
//
//            this@MainActivity.runOnUiThread(java.lang.Runnable {
//                mBookViewModel.getAllBooks().observe(this@MainActivity, Observer { books ->
//                    books?.let {
//                        mAdapter.setBooks(it)
//                    }
//                })
//            })
//            return null
//        }
//
//        override fun onPostExecute(result: Long?) {
//            super.onPostExecute(result)
//            tempoFinal = System.currentTimeMillis()
//            tempoExecusao = tempoFinal - tempoInicial
//            mTempo.text = tempoExecusao.toString()
//        }
//    }
}

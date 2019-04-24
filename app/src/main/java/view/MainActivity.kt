package view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat
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
import model.Word
import viewmodel.WordViewModel

class MainActivity : AppCompatActivity(), WordListAdapter.ItemClickListener {

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(this, NewWordActivity::class.java)
        intent.putExtra(EXTRA_KEY_WORD, mAdapter.getWords()[position].name)
        intent.putExtra(EXTRA_KEY_MEANING, mAdapter.getWords()[position].meaning)
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: WordListAdapter
    private var mWords: List<Word> = mutableListOf<Word>()
    private lateinit var mWordViewModel: WordViewModel
//    private lateinit var mNumber: WordViewModel
//    private lateinit var mNumberRegisters: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mRecyclerView = findViewById(R.id.recyclerView)
//        mNumberRegisters = findViewById(R.id.number_content)
        mAdapter = WordListAdapter(this, this)
        mAdapter.setWords(mWords)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
//        mNumber = ViewModelProviders.of(this).get(WordViewModel::class.java)

        mWordViewModel.getAllWords().observe(this, Observer { words ->
            words?.let {
                mAdapter.setWords(it)
            }
        })

//        mNumber.getNumberRegisters().toString()
//        mNumberRegisters.setText(mNumber.getNumberRegisters().toString())

        fab.setOnClickListener { view ->
            val intent = Intent(this, NewWordActivity::class.java)
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_SAVE){
            data?.let {
                val word = Word(it.getStringExtra(EXTRA_KEY_WORD), it.getStringExtra(EXTRA_KEY_MEANING))
                mWordViewModel.insertWord(word)
//                mNumberRegisters.setText(mAdapter.getItemCounts())
            }
        }else if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_DELETE){
            data?.let {
                val word = mWordViewModel.getWordByName(it.getStringExtra(EXTRA_KEY_WORD))
                mWordViewModel.deleteWord(word!!)
                Toast.makeText(this, getString(R.string.word_deleted), Toast.LENGTH_SHORT).show()
            }
        }else if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_ERROR){
            Toast.makeText(this, getString(R.string.empty_word_not_saved), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
            mWordViewModel.delereAllWords()
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

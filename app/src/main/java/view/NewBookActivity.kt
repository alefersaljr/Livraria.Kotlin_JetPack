package view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import br.com.alexandre_salgueirinho.library_kotlin.R
import br.com.alexandre_salgueirinho.library_kotlin.utils.*
import java.text.SimpleDateFormat
import java.text.DateFormat
import java.util.*

class NewBookActivity : AppCompatActivity() {

    private lateinit var mBookEditText: EditText
    private lateinit var mGeneroEditText: EditText
    private lateinit var mAutorEditText: EditText
    private lateinit var mPrecoEditText: EditText
    private lateinit var mSaveBtn: Button
    private lateinit var mDeleteBtn: Button

    private lateinit var mBook: String
    private lateinit var mGenero: String
    private lateinit var mAutor: String
    private lateinit var mPreco: String
    private var isNewBook: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        mBookEditText = findViewById(R.id.word_text)
        mGeneroEditText = findViewById(R.id.meaning_text)
        mAutorEditText = findViewById(R.id.autor_text)
        mPrecoEditText = findViewById(R.id.preco_text)
        mSaveBtn = findViewById(R.id.btn_save)
        mDeleteBtn = findViewById(R.id.btn_delete)

        if (isNewBook) {
            mDeleteBtn.visibility = View.GONE
        } else {
            mDeleteBtn.visibility = View.VISIBLE
        }

        val extras = intent.extras
        extras?.let {
            mBook = extras.get(EXTRA_KEY_BOOK_NAME) as String
            mGenero = extras.get(EXTRA_KEY_GENERO) as String
            mAutor = extras.get(EXTRA_KEY_AUTOR) as String
            mPreco = extras.get(EXTRA_KEY_PRECO) as String
            mBookEditText.setText(mBook)
            mGeneroEditText.setText(mGenero)
            mAutorEditText.setText(mAutor)
            mPrecoEditText.setText(mPreco)
            isNewBook = false
        }


        mSaveBtn.setOnClickListener {
            val intent = Intent()
            if (TextUtils.isEmpty(mBookEditText.text)) {
                mBookEditText.setError(getString(R.string.obrigatorio_nome_livro))
                mBookEditText.requestFocus()
            } else if (TextUtils.isEmpty(mGeneroEditText.text)) {
                mGeneroEditText.setError(getString(R.string.obrigatorio_autor))
                mGeneroEditText.requestFocus()
            } else if (TextUtils.isEmpty(mAutorEditText.text)) {
                mAutorEditText.setError(getString(R.string.obrigatorio_genero))
                mAutorEditText.requestFocus()
            } else if (TextUtils.isEmpty(mPrecoEditText.text)) {
                mPrecoEditText.setError(getString(R.string.obrigatorio_preco))
                mPrecoEditText.requestFocus()
            }
//                setResult(RESULT_ERROR, intent)
            else {
                if (isNewBook) {
                    var current = (Calendar.getInstance()).getTime()
                    var format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                    var date = format.format(current)
                    date = date.replace(":", "").replace("/", "").replace(" ", "")
                    intent.putExtra(EXTRA_KEY_ID, date.toInt())
                }
                intent.putExtra(EXTRA_KEY_BOOK_NAME, mBookEditText.text.toString())
                intent.putExtra(EXTRA_KEY_GENERO, mGeneroEditText.text.toString())
                intent.putExtra(EXTRA_KEY_AUTOR, mAutorEditText.text.toString())
                intent.putExtra(EXTRA_KEY_PRECO, mPrecoEditText.text.toString())
                setResult(RESULT_SAVE, intent)
                finish()
            }
        }

        mDeleteBtn.setOnClickListener {
            intent.putExtra(EXTRA_KEY_BOOK_NAME, mBookEditText.text.toString())
            setResult(RESULT_DELETE, intent)
            finish()
        }
    }
}

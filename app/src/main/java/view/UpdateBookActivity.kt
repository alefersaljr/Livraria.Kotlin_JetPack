package view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import br.com.alexandre_salgueirinho.library_kotlin.R
import br.com.alexandre_salgueirinho.library_kotlin.utils.*

class UpdateBookActivity : AppCompatActivity() {

    private lateinit var mBookEditText: EditText
    private lateinit var mGeneroEditText: EditText
    private lateinit var mAutorEditText: EditText
    private lateinit var mPrecoEditText: EditText
    private lateinit var mSaveBtn: Button
    private lateinit var mDeleteBtn: Button

    private lateinit var mId: String
    private lateinit var mBook: String
    private lateinit var mGenero: String
    private lateinit var mAutor: String
    private lateinit var mPreco: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_book)

        mBookEditText = findViewById(R.id.book_txt_updt)
        mGeneroEditText = findViewById(R.id.genero_txt_updt)
        mAutorEditText = findViewById(R.id.autor_txt_updt)
        mPrecoEditText = findViewById(R.id.preco_text_updt)
        mSaveBtn = findViewById(R.id.btn_save_updt)
        mDeleteBtn = findViewById(R.id.btn_delete_updt)


        val extras = intent.extras
        extras?.let {
            mId = extras.get(EXTRA_KEY_ID) as String
            mBook = extras.get(EXTRA_KEY_BOOK_NAME) as String
            mGenero = extras.get(EXTRA_KEY_GENERO) as String
            mAutor = extras.get(EXTRA_KEY_AUTOR) as String
            mPreco = extras.get(EXTRA_KEY_PRECO) as String
            mBookEditText.setText(mBook)
            mGeneroEditText.setText(mGenero)
            mAutorEditText.setText(mAutor)
            mPrecoEditText.setText(mPreco)
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
            } else {
                intent.putExtra(EXTRA_KEY_ID, mId)
                intent.putExtra(EXTRA_KEY_BOOK_NAME, mBookEditText.text.toString())
                intent.putExtra(EXTRA_KEY_GENERO, mGeneroEditText.text.toString())
                intent.putExtra(EXTRA_KEY_AUTOR, mAutorEditText.text.toString())
                intent.putExtra(EXTRA_KEY_PRECO, mPrecoEditText.text.toString())
                setResult(RESULT_SAVE, intent)
                finish()
            }
        }

        mDeleteBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.app_name))
            builder.setMessage(getString(R.string.exclude_book))

            builder.setPositiveButton(getString(R.string.dialog_sim)) { _, _ ->
                intent.putExtra(EXTRA_KEY_BOOK_NAME, mId)
                setResult(RESULT_DELETE, intent)
                finish()
            }

            builder.setNegativeButton(getString(R.string.dialog_nao)) { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}

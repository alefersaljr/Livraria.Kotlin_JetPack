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

class NewBookActivity : AppCompatActivity() {

    private lateinit var mBookEditText: EditText
    private lateinit var mGeneroEditText: EditText
    private lateinit var mSaveBtn: Button
    private lateinit var mDeleteBtn: Button

    private lateinit var mBook: String
    private lateinit var mGenero: String
    private var isNewBook : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        mBookEditText = findViewById(R.id.word_text)
        mGeneroEditText = findViewById(R.id.meaning_text)
        mSaveBtn = findViewById(R.id.btn_save)
        mDeleteBtn = findViewById(R.id.btn_delete)

        var extras = intent.extras
        extras?.let {
            mBook = extras.get(EXTRA_KEY_BOOK) as String
            mGenero = extras.get(EXTRA_KEY_GENERO) as String
            mBookEditText.setText(mBook)
            mGeneroEditText.setText(mGenero)
            mBookEditText.isEnabled = false
            isNewBook = false
        }

        if(isNewBook){
            mDeleteBtn.visibility = View.GONE
        }else{
            mDeleteBtn.visibility = View.VISIBLE
        }

        mSaveBtn.setOnClickListener {
            val intent = Intent()
            if(TextUtils.isEmpty(mBookEditText.text) || TextUtils.isEmpty(mGeneroEditText.text)){
                setResult(RESULT_ERROR, intent)
            }else {
                intent.putExtra(EXTRA_KEY_BOOK, mBookEditText.text.toString())
                intent.putExtra(EXTRA_KEY_GENERO, mGeneroEditText.text.toString())
                setResult(RESULT_SAVE, intent)
            }
            finish()
        }

        mDeleteBtn.setOnClickListener {
            intent.putExtra(EXTRA_KEY_BOOK, mBookEditText.text.toString())
            setResult(RESULT_DELETE, intent)
            finish()
        }
    }
}

package view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.alexandre_salgueirinho.library_kotlin.R
import model.Book

class BookListAdapter(private val mContext: Context, private var mItemClickListener: ItemClickListener) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private lateinit var mBooks: List<Book>

    fun getBooks() = mBooks

    fun setBooks(books: List<Book>){
        mBooks = books
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mBooks.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        var currentBook = mBooks[position]
        holder.bookTextView.text = currentBook.name
        holder.bookAutorTextView.text = currentBook.autor

        holder.bookTextView.setOnClickListener {
            mItemClickListener.onItemClick(holder.bookTextView, position)
        }
        holder.bookAutorTextView.setOnClickListener {
            mItemClickListener.onItemClick(holder.bookAutorTextView, position)
        }
    }

    class BookViewHolder (itemView: View): RecyclerView.ViewHolder (itemView){
        var bookTextView: TextView = itemView.findViewById(R.id.textView)
        var bookAutorTextView: TextView = itemView.findViewById(R.id.textViewAutor)
    }
}
package viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import model.Word
import model.WordRepository

class WordViewModel(application: Application): AndroidViewModel(application) {
    private val wordRepository:WordRepository
    private val allWords: LiveData<List<Word>>
//    private val numberRegisters: LiveData<Int>?

    init {
        wordRepository = WordRepository(application)
        allWords = wordRepository.getAllWords()
//        numberRegisters = wordRepository.getNumberRegisters()
    }

    fun insertWord(word: Word){
        wordRepository.insertWord(word)
    }

    fun deleteWord(word: Word){
        wordRepository.deleteWord(word)
    }

    fun delereAllWords(){
        wordRepository.delereAllWords()
    }

    fun getAllWords() = allWords

    fun getWordByName(name: String): Word? {
        return wordRepository.getWordByName(name)
    }

//    fun getNumberRegisters(): LiveData<Int>? {
//        return wordRepository.getNumberRegisters()
//    }
}
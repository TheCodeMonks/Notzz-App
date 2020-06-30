package www.groomer.nottzapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import www.groomer.nottzapp.DB.NotesDatabase
import www.groomer.nottzapp.Notes.ViewModelProviders.NewsViewModelProviderFactory
import www.groomer.nottzapp.Notes.ViewModels.NotesViewModel
import www.groomer.nottzapp.Repo.NotesRepo

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init viewModelProvider
        val newsRepository = NotesRepo(NotesDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NotesViewModel::class.java)

    }
}
package www.groomer.nottzapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import www.groomer.nottzapp.DB.NotesDatabase
import www.groomer.nottzapp.Repo.NotesRepo
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModelProviders.NewsViewModelProviderFactory
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModels.NotesViewModel

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
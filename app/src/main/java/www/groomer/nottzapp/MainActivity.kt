package www.groomer.nottzapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import www.groomer.nottzapp.DB.NotesDatabase
import www.groomer.nottzapp.Repo.NotesRepo
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModelProviders.NewsViewModelProviderFactory
import www.groomer.nottzapp.UI.AddNotes.Notes.ViewModels.NotesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NotesViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init viewModelProvider
        val newsRepository = NotesRepo(NotesDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NotesViewModel::class.java)


        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}
package thecodemonks.org.nottzapp.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.UI.ui.Notes.ViewModelProviders.TodoViewModelProviderFactory
import thecodemonks.org.nottzapp.UI.ui.Notes.ViewModels.NotesViewModel
import thecodemonks.org.nottzapp.db.NotesDatabase
import thecodemonks.org.nottzapp.repo.NotesRepo

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NotesViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init viewModelProvider
        val newsRepository = NotesRepo(NotesDatabase(this))
        val viewModelProviderFactory = TodoViewModelProviderFactory(newsRepository)
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
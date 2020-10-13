package thecodemonks.org.nottzapp.app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import thecodemonks.org.nottzapp.R
import thecodemonks.org.nottzapp.db.NotesDatabase
import thecodemonks.org.nottzapp.repo.NotesRepo
import thecodemonks.org.nottzapp.ui.notes.NotesViewModel
import thecodemonks.org.nottzapp.utils.factory.viewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val newsRepository by lazy { NotesRepo(NotesDatabase(this)) }
    private val viewModel: NotesViewModel by viewModels {
        viewModelFactory { NotesViewModel(newsRepository) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Just so the viewModel doesn't get removed by the compiler, as it isn't used
         * anywhere here for now
         */
        viewModel

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}
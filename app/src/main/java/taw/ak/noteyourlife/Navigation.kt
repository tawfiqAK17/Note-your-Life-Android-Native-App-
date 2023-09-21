package taw.ak.noteyourlife

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import taw.ak.noteyourlife.Database.NotesDatabase.NoteViewModel
import taw.ak.noteyourlife.Database.ToDoDatabase.ToDo
import taw.ak.noteyourlife.Database.ToDoDatabase.ToDoViewModel
import taw.ak.noteyourlife.Notes.AddNote
import taw.ak.noteyourlife.Notes.SavedNotes
import taw.ak.noteyourlife.ToDo.SavedToDos

@Composable
fun Navigation() {
    val fontFamily = FontFamily(
        Font(R.font.patrickhand_regular)
    )
    val context = LocalContext.current
    val noteViewModel: NoteViewModel = viewModel<NoteViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(
                    application = context.applicationContext as Application
                ) as T
            }
        }
    )
    val notes = noteViewModel.readAllNotes.observeAsState(listOf()).value

    val toDoViewModel: ToDoViewModel = viewModel<ToDoViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ToDoViewModel(
                    application = context.applicationContext as Application
                ) as T
            }
        }
    )


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        //the home screen
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController, fontFamily = fontFamily)
        }

        // saved notes
        composable(route = Screens.SavedNotes.route) {
            SavedNotes(navController = navController,
                font = fontFamily,
                notes = notes,
                mViewModel = noteViewModel
            )
        }

        // saved todos
        composable(route = Screens.SavedToDos.route) {
            val toDos = toDoViewModel.readAllToDo.observeAsState(listOf()).value
           SavedToDos(navController = navController,
               toDos = toDos,
               font =fontFamily )
        }
        // add new note and edite
        composable(route = Screens.AddNote.route,){
            AddNote(
                navController = navController,
                font = fontFamily,
                viewModel = noteViewModel)
        }
        //add new to-do
        composable(route = Screens.AddToDo.route){

        }

    }
}
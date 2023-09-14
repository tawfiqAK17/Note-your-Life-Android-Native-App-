package taw.ak.noteyourlife

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
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
import taw.ak.noteyourlife.Database.NoteViewModel

@Composable
fun Navigation() {
    val fontFamily = FontFamily(
        Font(R.font.patrickhand_regular)
    )
    val context = LocalContext.current
    val mViewMoodel: NoteViewModel = viewModel<NoteViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(
                    application = context.applicationContext as Application
                ) as T
            }
        }
    )
    val notes = mViewMoodel.readAllNotes.observeAsState(listOf()).value

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        //the home screen
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController, fontFamily = fontFamily)
        }

        // saved notes
        composable(route = Screens.SavedNotes.route) {
            SavedNotesAndToDos(
                mNoteViewModel = mViewMoodel,
                notes = notes,
                font = fontFamily,
                navController = navController,
                text = "NO saned notes",
                logo = painterResource(id = R.drawable.no_seved_notes),
                route = Screens.AddNote.route,
                vpadding = true,
            )
        }

        // saved todos
        composable(route = Screens.SavedToDos.route) {
            SavedNotesAndToDos(
                mNoteViewModel = mViewMoodel,
                notes = notes,
                font = fontFamily,
                navController = navController,
                text = "NO saved notes",
                logo = painterResource(id = R.drawable.no_saved_to_do),
                route = Screens.AddToDo.route,
                vpadding = false
            )
        }
        // add new note
        composable(route = Screens.AddNote.route,
            /**/
        ) {
            AddNote(
                navController = navController,
                font = fontFamily,
                viewModel = mViewMoodel,
               //vtitle = it.arguments?.getString("title"),
                //vcontent = it.arguments?.getString("content"),
                //isEditing = it.arguments?.getBoolean("editing")
            )
        }
        composable(route = Screens.EditeNote.route + "/{title}/{content}/{isEditing}",
            arguments = listOf(
                navArgument(name = "title") {
                    type = NavType.StringType
                },
                navArgument(name = "content") {
                    type = NavType.StringType
                },
                navArgument(name = "isEditing") {
                    type = NavType.BoolType
                }
            ),

        ){
            AddNote(navController = navController,
                font = fontFamily,
                viewModel =mViewMoodel,
                vtitle = it.arguments?.getString("title"),
                vcontent = it.arguments?.getString("content"),
                isEditing = it.arguments?.getBoolean("isEditing")
                )
        }

    }

}
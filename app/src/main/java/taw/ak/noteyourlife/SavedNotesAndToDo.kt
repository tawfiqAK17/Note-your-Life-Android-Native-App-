package taw.ak.noteyourlife

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import taw.ak.noteyourlife.Database.Note
import taw.ak.noteyourlife.Database.NoteViewModel
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedNotesAndToDos(
    mNoteViewModel: NoteViewModel,
    notes: List<Note>,
    font: FontFamily,
    text:String,
    logo : Painter,
    route : String,
    vpadding : Boolean,
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(modifier = Modifier
            .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.background(colorResource(id = R.color.topAppBar)),
                    title = {
                        Text(text = "Notes", fontFamily = font, fontSize = 26.sp)
                    },
                    navigationIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                navController.navigate(Screens.MainScreen.route)
                            },
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back to main screen"
                        )
                    },

                    )
            }

        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                if (notes.isEmpty()) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp)) {
                    Column(modifier = Modifier
                        .size(350.dp)
                        .padding(start = if (vpadding) 45.dp else 0.dp, bottom = 150.dp),
                        ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            //.padding(bottom = 100.dp),
                            ) {
                            Image(painter = logo,
                                contentDescription =text,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 38.dp)
                                )
                            Text(text = text,
                                fontSize = 24.sp,
                                fontFamily = font,
                                color = Color.DarkGray,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(end = if (vpadding) 36.dp else 0.dp)
                                )
                        }
                        }
                        Box(modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = colorResource(id = R.color.addNew))
                            .align(Alignment.BottomCenter)
                            .clickable {

                                navController.navigate(route)
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Add,
                                contentDescription = "add new note",
                            modifier= Modifier
                                .fillMaxSize()
                                .align(Alignment.BottomCenter)
                                )

                        }
                    }
                } else {
                    // if the route is for the notes
                    if (vpadding) {
                        SavedNotes(
                            navController = navController,
                            font = font,
                            mViewModel = mNoteViewModel,
                            notes = notes
                        )
                    } else
                    { Box(modifier = Modifier.fillMaxSize())}
                }
            }

        }
    }
}
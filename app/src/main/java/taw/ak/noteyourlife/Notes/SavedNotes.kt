package taw.ak.noteyourlife.Notes

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import taw.ak.noteyourlife.Database.NotesDatabase.Note
import taw.ak.noteyourlife.Database.NotesDatabase.NoteViewModel
import taw.ak.noteyourlife.R
import taw.ak.noteyourlife.Screens
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedNotes(
    navController: NavController,
    mViewModel : NoteViewModel,
    font: FontFamily,
    notes: List<Note>
) {
    BackHandler() {
        navController.navigate(Screens.MainScreen.route)
    }
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
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 20.dp, end = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .size(350.dp)
                                .padding(start = 45.dp, bottom = 150.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.no_seved_notes),
                                    contentDescription = "no saved notes",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 38.dp)
                                )
                                Text(
                                    text = "No saved Notes",
                                    fontSize = 24.sp,
                                    fontFamily = font,
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(end = 36.dp)
                                )
                            }
                        }
                        Box(modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = colorResource(id = R.color.addNew))
                            .align(Alignment.BottomEnd)
                            .clickable {

                                navController.navigate(Screens.AddNote.route)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "add new note",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.BottomCenter)
                            )

                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(14.dp)
                                .verticalScroll(state = rememberScrollState(0)),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            for (note in notes) {
                                Box(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.fillMaxSize()) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                                .padding(start = 10.dp, end = 10.dp)
                                        ) {
                                            Text(
                                                text = note.date!!,
                                                fontFamily = font,
                                                modifier = Modifier.weight(1.8f)
                                            )
                                            Spacer(modifier = Modifier.weight(4f))
                                            Image(painter = painterResource(id = R.drawable.copy),
                                                contentDescription = "copy note",
                                                modifier = Modifier
                                                    .clickable {
                                                        val vNote = note.copy(
                                                            date = LocalDate
                                                                .now()
                                                                .format(
                                                                    DateTimeFormatter.ofPattern(
                                                                        "dd/MM/yyyy"
                                                                    )
                                                                ),
                                                            id = 0
                                                        )
                                                        mViewModel.upsertNote(vNote)
                                                    }
                                                    .weight(1f)
                                            )
                                            Image(painter = painterResource(id = R.drawable.delete),
                                                contentDescription = "copy note",
                                                modifier = Modifier
                                                    .clickable {
                                                        mViewModel.deletNote(note)
                                                    }
                                                    .weight(1f)
                                            )
                                            Image(painter = painterResource(id = R.drawable.edit),
                                                contentDescription = "edite note",
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clickable {
                                                        mViewModel.copyNote(note)
                                                        navController.navigate(Screens.AddNote.route)
                                                            mViewModel.deletNote(note)
                                                    }

                                            )
                                        }
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Box(
                                            modifier = Modifier
                                                .weight(9f)
                                                .fillMaxWidth()
                                                .height(100.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .background(colorResource(id = R.color.noteBg))

                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(start = 10.dp, end = 10.dp)
                                            ) {
                                                Text(
                                                    text = note.title ?: "No title",
                                                    fontFamily = font,
                                                    fontSize = 28.sp,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier
                                                        .height(40.dp)
                                                        .padding(start = 20.dp)
                                                )
                                                Text(
                                                    text = ("   " + note.content)
                                                        ?: "   No content",
                                                    fontFamily = font,
                                                    fontSize = 18.sp,
                                                    textAlign = TextAlign.Justify,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier.height(140.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp)
                        ) {
                            Box(modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(color = colorResource(id = R.color.addNew))
                                .clickable {
                                    navController.navigate(Screens.AddNote.route)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "add new note",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.BottomCenter)
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

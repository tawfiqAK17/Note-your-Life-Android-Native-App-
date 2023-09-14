package taw.ak.noteyourlife.Notes

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import taw.ak.noteyourlife.Database.Note
import taw.ak.noteyourlife.Database.NoteViewModel
import taw.ak.noteyourlife.R
import taw.ak.noteyourlife.Screens
import taw.ak.noteyourlife.Screens.AddNote.route

@Composable
fun SavedNotes(
    navController: NavController,
    mViewModel: NoteViewModel,
    font: FontFamily,
    notes: List<Note>
) {
    Box(modifier = Modifier.fillMaxSize()) {
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
                        ) {
                            Text(
                                text = note.date,
                                fontFamily = font,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.weight(4f))
                            Icon(imageVector = Icons.Default.Delete,
                                contentDescription = "delete note",
                                modifier = Modifier
                                    .clickable {
                                        mViewModel.deletNote(note)
                                    }
                                    .weight(1f)
                            )
                            Icon(imageVector = Icons.Default.Edit,
                                contentDescription = "edite note",
                                modifier = Modifier
                                    .clickable {
                                        val isEditing = true
                                        navController.navigate(
                                            "${Screens.EditeNote.route}/${note.title}" +
                                                    "/${note.content}/$isEditing"
                                        )
                                        mViewModel.deletNote(note)
                                    }
                                    .weight(1f)

                            )
                        }
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
                                    text = ("   " + note.content) ?: "   No content",
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
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding( 20.dp)
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
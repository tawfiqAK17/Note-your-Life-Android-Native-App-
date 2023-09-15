package taw.ak.noteyourlife.Notes

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import taw.ak.noteyourlife.Database.Note
import taw.ak.noteyourlife.Database.NoteViewModel
import taw.ak.noteyourlife.R
import taw.ak.noteyourlife.Screens
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNote(
    navController: NavController,
    font: FontFamily,
    viewModel: NoteViewModel,
    vtitle: String? = null,
    vcontent: String? = null,
    isEditing: Boolean? = false
) {
    val context = LocalContext.current
    //a copy of the note which ganna be modified because it had deleted when the editing icon had pressed
    val mNote = Note(
        title = vtitle,
        content = vcontent,
        date = "",
        id = 0
    )
    var title by remember {
        mutableStateOf(vtitle ?: "")
    }

    var content by remember {
        mutableStateOf(vcontent ?: "")
    }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(9f)
                    .padding(top = 45.dp, start = 10.dp, end = 10.dp)
                    .verticalScroll(scrollState)
            ) {
                OutlinedTextField(
                    value = title, onValueChange = { value ->
                        title = value
                    },
                    textStyle = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = font
                    ),
                    label = {
                        Text(text = "titel", fontFamily = font, fontSize = 24.sp)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .height(38.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = content, onValueChange = { value ->
                        content = value
                    },
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = font
                    ),
                    label = {
                        Text(text = "content", fontFamily = font, fontSize = 18.sp)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(start = 28.dp, end = 28.dp, bottom = 10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        //when the changes applied on the note had ben ignored we save the copy above
                        if (isEditing!!) {
                            mNote.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            viewModel.upsertNote(mNote)
                        }
                        navController.navigate(Screens.SavedNotes.route)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.cancel),
                                contentDescription = "cancel the note",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "cancel", fontFamily = font, fontSize = 16.sp)

                }
                Spacer(modifier = Modifier.weight(4f))
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        if (title.isNotBlank() || content.isNotBlank()) {
                            val date =
                                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                            val note = Note(
                                title = title.ifBlank { "No title" },
                                content = content.ifBlank { "No content" },
                                date = date,
                                id = 0
                            )
                            viewModel.upsertNote(note)
                            navController.navigate(Screens.SavedNotes.route)
                        }else{
                            Toast.makeText(context,"you should pass a text and a title",Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.accept),
                                contentDescription = "save note",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "save", fontFamily = font, fontSize = 16.sp)

                }
            }
        }
    }
}
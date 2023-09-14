package taw.ak.noteyourlife

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import taw.ak.noteyourlife.Database.Note
import taw.ak.noteyourlife.Database.NoteViewModel
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNote(navController: NavController,
            font : FontFamily,
            viewModel: NoteViewModel,
            vtitle : String? = null,
            vcontent : String?=null,
            isEditing : Boolean? = false
            ) {
    var title by remember {
        mutableStateOf(vtitle?:"")
    }

    var content by remember{
        mutableStateOf(vcontent?:"")
    }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier
                .fillMaxSize()
                .weight(9f)
                .padding(top = 45.dp, start = 10.dp, end = 10.dp)
                .verticalScroll(scrollState)
            ) {
                OutlinedTextField(value = title?:"" , onValueChange ={ value ->
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
                Spacer(modifier = Modifier
                    .height(38.dp)
                    .fillMaxWidth())
                OutlinedTextField(value = content?:"" , onValueChange ={ value ->
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
            }
            Row(modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(start = 28.dp, end = 28.dp, bottom = 10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    navController.navigate(Screens.SavedNotes.route)
                }
                ) {
                    Box(modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.trash_bin),
                            contentDescription = "delete note",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "delet", fontFamily = font, fontSize = 16.sp)

                }
                Spacer(modifier = Modifier.weight(4f))
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    val note = Note(
                        title = title,
                        content = content,
                        date = date,
                        id = 0
                    )
                    viewModel.upsertNote(note)
                }
                    ) {
                    Box(modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.save),
                            contentDescription = "save note",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "save", fontFamily = font, fontSize = 16.sp)

                }
            }
        }
    }
    Log.i("taww",  "AddNote")
}
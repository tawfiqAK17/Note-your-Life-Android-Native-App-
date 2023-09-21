package taw.ak.noteyourlife.ToDo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import taw.ak.noteyourlife.Database.ToDoDatabase.ToDo
import taw.ak.noteyourlife.Database.ToDoDatabase.ToDoViewModel
import taw.ak.noteyourlife.R
import taw.ak.noteyourlife.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedToDos(
    navController: NavController,
    toDos : List<ToDo>,
    font: FontFamily
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
                        Text(text = "To-Do", fontFamily = font, fontSize = 26.sp)
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
                if (toDos.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 20.dp, end = 20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .size(350.dp)
                                .padding( bottom = 150.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.no_saved_to_do),
                                    contentDescription = "no saved to-do",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 38.dp)
                                )
                                Text(
                                    text = "No saved To-Do",
                                    fontSize = 24.sp,
                                    fontFamily = font,
                                    color = Color.DarkGray,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)

                                )
                            }
                        }
                        Box(modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = colorResource(id = R.color.addNew))
                            .align(Alignment.BottomEnd)
                            .clickable {

                                navController.navigate(Screens.AddToDo.route)
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
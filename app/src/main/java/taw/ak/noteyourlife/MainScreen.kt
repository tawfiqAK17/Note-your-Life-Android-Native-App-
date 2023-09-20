package taw.ak.noteyourlife

import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
                navController: NavController,
               fontFamily: FontFamily,
) {
    val items = listOf(
        BottomBarItems(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Default.Home
        ),
        BottomBarItems(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Default.Settings
        )
    )
    var selectedBottomIcon by rememberSaveable() {
        mutableIntStateOf(0)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.background(colorResource(id = R.color.topAppBar)),
                    title = {
                    Text(text = "Note your Life", fontFamily = fontFamily, fontSize = 32.sp)
                }
                )
            },
            bottomBar = {
                NavigationBar(modifier = Modifier
                    .graphicsLayer {
                        shape = RoundedCornerShape(20.dp)
                        clip = true
                    }
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedBottomIcon == index,
                            onClick = {
                                selectedBottomIcon = index
                            },
                            icon = {
                                Badge (containerColor = Color.Transparent){
                                    Icon(
                                        imageVector = if (selectedBottomIcon == index) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            })
                    }
                }

            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 18.dp, end = 18.dp, top = 50.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { navController.navigate(Screens.SavedNotes.route) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Card(
                                    modifier = Modifier
                                        .size(150.dp, 200.dp)
                                        .padding(top = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.note),
                                        contentDescription = "note logo",
                                        modifier = Modifier.fillMaxSize()
                                    )

                                }
                                Text(text = "   Notes", fontFamily = fontFamily, fontSize = 25.sp)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate(Screens.SavedToDos.route)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Card(
                                    modifier = Modifier
                                        .size(150.dp, 200.dp)
                                        .padding(top = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.to_do),
                                        contentDescription = "todo logo",
                                        modifier = Modifier.fillMaxSize().padding(5.dp)
                                    )

                                }
                                Text(text = "ToDo", fontFamily = fontFamily, fontSize = 25.sp)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Card(
                                    modifier = Modifier
                                        .size(150.dp, 200.dp)
                                        .padding(top = 5.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.pie_chart),
                                        contentDescription = "pieChart logo",
                                        modifier = Modifier.fillMaxSize().padding(5.dp)
                                    )

                                }
                                Text(
                                    text = "Time Manager",
                                    fontFamily = fontFamily,
                                    fontSize = 25.sp
                                )
                            }
                        }
                    }

                }

            }
        }
    }
}
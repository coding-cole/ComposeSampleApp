package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    ComposeAppTheme {
        content()
    }
}

@Composable
fun MyScreenContent(names: List<String> = List(101) { "Hello Android #$it" }) {
    val counterState = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        ) {
            Text(
                text = "Sample List",
                style = MaterialTheme.typography.h4
            )
        }
        NameList(names, Modifier.weight(1f))
//            Divider(color = Color.Transparent, thickness = 12.dp)
        Counter(
            count = counterState.value,
            updateCount = { newCount ->
                counterState.value = newCount
            }
        )
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .background(MaterialTheme.colors.primary)
            .padding(8.dp)
            .clickable(onClick = { /* Ignoring onClick */ })
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.surface)
            .padding(12.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            modifier = Modifier.size(50.dp)
        ) {
            // Images goes here
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text("Alex Cole", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)
    Text(
        text = "$name!",
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected })
    )
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        Button(
            onClick = { updateCount(count + 1) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (count > 5) Color.Green else Color.White
            )
        ) {
            Text(
                text = buildAnnotatedString {
                    append("I have been clicked ")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Red
                        )
                    ) {
                        append("$count")
                    }
                    append(" times")
                }
            )
        }
    }

}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting(name = name)
            if (name != names[names.lastIndex]) {
                Divider(
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}

@Composable
fun SimpleList() {
    val listSize = 100
// We save the scrolling position with this state
    val scrollState = rememberLazyListState()
// We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        ScrollButtons(
            listSize = listSize,
            scrollState = scrollState,
            coroutineScope = coroutineScope
        )
        Spacer(Modifier.width(18.dp))
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun ScrollButtons(listSize: Int, scrollState: LazyListState, coroutineScope: CoroutineScope) {
    Row(horizontalArrangement = Arrangement.SpaceAround) {
        Button(onClick = {
            coroutineScope.launch {
                // 0 is the first item index
                scrollState.animateScrollToItem(0)
            }
        }) {
            Text("Scroll to the top")
        }

        Button(onClick = {
            coroutineScope.launch {
                // listSize - 1 is the last index of the list
                scrollState.animateScrollToItem(listSize - 1)
            }
        }) {
            Text("Scroll to the end")
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            modifier = Modifier.size(50.dp)
        ) {
            CoilImage(
                data = "https://developer.android.com/images/brand/Android_Robot.png",
                contentDescription = "Android Logo",
                modifier = Modifier.size(50.dp)
            )
        }
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

@Preview(showBackground = true, name = "Screen Preview")
/* (showBackground = true, name = "Screen Preview") */
@Composable
fun DefaultPreview() {
    MyApp {
//        SimpleList()
        MyScreenContent()

    }
}







package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeapp.ui.theme.ComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {

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


@Preview(showBackground = true, name = "Screen Preview")
/* (showBackground = true, name = "Screen Preview") */
@Composable
fun DefaultPreview() {
    MyApp {

    }
}







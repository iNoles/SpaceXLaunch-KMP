package com.jonathansteele.spacexlaunch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jonathansteele.spacexlaunch.ui.SpaceXLaunchView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceXLaunchTheme {
                SpaceXLaunchView()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultScreen() {
    SpaceXLaunchTheme {
        SpaceXLaunchView()
    }
}
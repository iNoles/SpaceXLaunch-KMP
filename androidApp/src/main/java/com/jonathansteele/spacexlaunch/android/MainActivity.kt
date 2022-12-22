package com.jonathansteele.spacexlaunch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jonathansteele.spacexlaunch.SpaceXLaunchView
import com.jonathansteele.spacexlaunch.android.ui.SpaceXBackground
import com.jonathansteele.spacexlaunch.android.ui.theme.SpaceXLaunchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceXLaunchTheme {
                SpaceXBackground {
                    SpaceXLaunchView()
                }
            }
        }
    }
}

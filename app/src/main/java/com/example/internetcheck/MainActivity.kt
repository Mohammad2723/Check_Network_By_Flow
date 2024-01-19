package com.example.internetcheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.internetcheck.ui.theme.InternetCheckTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    //define checkConnection
    private lateinit var checkConnection: CheckConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // fill value checkConnection
        checkConnection = CheckConnectionImpl(context = applicationContext)

        setContent {
            val networkStatus = checkConnection.observe()
                .collectAsState(initial = CheckConnection.Status.Unavilable)



            InternetCheckTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(id = R.drawable.round_wifi_24),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(
                                when (networkStatus.value) {
                                    CheckConnection.Status.Availabe -> Color.Green
                                    CheckConnection.Status.Unavilable -> Color.Yellow
                                    CheckConnection.Status.Losing -> Color.Magenta
                                    CheckConnection.Status.Lost -> Color.Red
                                }
                            )
                        )


                        Text(
                            text = when (networkStatus.value) {
                                CheckConnection.Status.Availabe -> "Network is Available"
                                CheckConnection.Status.Unavilable -> "Network is Unavailable"
                                CheckConnection.Status.Losing -> "Network is Losing"
                                CheckConnection.Status.Lost -> "Network is Lost"
                            }, fontSize = 20.sp
                        )


                    }
                }
            }
        }
    }
}


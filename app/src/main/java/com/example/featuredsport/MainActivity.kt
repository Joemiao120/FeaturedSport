package com.example.featuredsport

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.featuredsport.data.model.Sport
import com.example.featuredsport.ui.theme.FeaturedSportTheme
import com.example.featuredsport.viewmodel.SportViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeaturedSportTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Scaffold(
        topBar = {
            RefreshTopBar()
        }
    ) { padding ->
        SportScreen(modifier = Modifier.padding(padding))
    }
}

@Composable
fun RefreshTopBar(viewModel: SportViewModel = viewModel()) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            RefreshButton {
                viewModel.refreshSport()
            }
        }
    )
}

@Composable
fun RefreshButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
    }
}

@Composable
fun SportScreen(
    modifier: Modifier = Modifier,
    viewModel: SportViewModel = viewModel()
) {
    val sportUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(modifier = modifier) {
        if (sportUiState.loading) {
            Text(text = "Loading...", fontSize = 20.sp, modifier= Modifier.fillMaxSize())
        } else {
            val currentSport = sportUiState.currentSport
            if (currentSport != null) {
                SportView(sport = currentSport)
            }
        }
    }
}

@Composable
fun SportView(modifier: Modifier = Modifier, sport: Sport) {
    Column(modifier) {
        Text(text = sport.name, fontSize = 24.sp)
        Text(
            text = sport.description,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
@Preview
fun previewSportView() {
    SportView(sport = Sport("1", "2"))
}
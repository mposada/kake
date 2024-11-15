package com.mposadar.kake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.mposadar.kake.cats.CatsViewModel
import com.mposadar.kake.cats.view.CatsScreen
import com.mposadar.kake.ui.theme.KakeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CatsViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KakeTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                Scaffold(
                    modifier = Modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Venu") },
                            actions = {
                                IconButton(
                                    onClick = { viewModel.handleEvent() },
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.List,
                                        contentDescription = "Change to Grid View",
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior,
                        )
                    },
                ) { innerPadding ->
                    CatsScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}

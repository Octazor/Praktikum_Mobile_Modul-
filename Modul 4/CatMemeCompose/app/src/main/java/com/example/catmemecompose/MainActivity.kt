package com.example.catmemecompose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catmemecompose.ui.theme.CatMemeComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatMemeComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    // Menggunakan ViewModelFactory dengan parameter String
                    val viewModel: CatViewModel = viewModel(
                        factory = CatViewModel.Factory("Cat Memes")
                    )

                    val uiEvent by viewModel.uiEvent.collectAsState()

                    // Menangani event dari ViewModel menggunakan StateFlow
                    LaunchedEffect(uiEvent) {
                        when (val event = uiEvent) {
                            is CatUiEvent.NavigateToDetail -> {
                                navController.navigate("detail/${event.cat.id}")
                                viewModel.resetEvent()
                            }
                            is CatUiEvent.OpenUrl -> {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
                                startActivity(intent)
                                viewModel.resetEvent()
                            }
                            CatUiEvent.Idle -> {}
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = "list",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("list") {
                            val cats by viewModel.catList.collectAsState()
                            CatListScreen(
                                cats = cats,
                                onDetailClick = { viewModel.onDetailClick(it) },
                                onInstagramClick = { viewModel.onExplicitIntentClick(it) }
                            )
                        }

                        composable("detail/{catId}") { backStackEntry ->
                            val catId = backStackEntry.arguments?.getString("catId")?.toIntOrNull()
                            val cats by viewModel.catList.collectAsState()
                            val selectedCat = cats.find { it.id == catId }

                            if (selectedCat != null) {
                                CatDetailScreen(cat = selectedCat)
                            }
                        }
                    }
                }
            }
        }
    }
}
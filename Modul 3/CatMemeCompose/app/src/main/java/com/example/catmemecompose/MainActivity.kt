package com.example.catmemecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
            CatMemeComposeTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    val viewModel: CatViewModel = viewModel()

                    NavHost(
                        navController = navController,
                        startDestination = "list",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("list") {
                            CatListScreen(
                                cat = viewModel.catList,
                                navController = navController
                            )
                        }

                        composable("detail/{catId}") { backStackEntry ->
                            val catId = backStackEntry.arguments?.getString("catId")?.toIntOrNull()
                            val selectedCat = viewModel.catList.find { it.id == catId }

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
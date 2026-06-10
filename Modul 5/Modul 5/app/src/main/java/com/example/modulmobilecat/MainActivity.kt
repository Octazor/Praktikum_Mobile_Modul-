package com.example.modulmobilecat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.modulmobilecat.data.local.CatDatabase
import com.example.modulmobilecat.data.local.UserPreferences
import com.example.modulmobilecat.data.remote.CatApiService
import com.example.modulmobilecat.data.repository.CatRepository
import com.example.modulmobilecat.ui.screen.CatApp
import com.example.modulmobilecat.ui.screen.openCatImageIntent
import com.example.modulmobilecat.ui.theme.ModulMobileCatTheme
import com.example.modulmobilecat.ui.viewmodel.CatViewModel
import com.example.modulmobilecat.ui.viewmodel.CatViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: CatViewModel by viewModels {
        val database = CatDatabase.getInstance(applicationContext)
        CatViewModelFactory(
            appTitle = "Galeri Kucing",
            repository = CatRepository(
                apiService = CatApiService(),
                catDao = database.catDao()
            ),
            userPreferences = UserPreferences(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.uiState.collectAsState()

            ModulMobileCatTheme(darkTheme = state.darkMode) {
                CatApp(
                    state = state,
                    onRefresh = viewModel::refreshCats,
                    onOpenDetail = viewModel::openDetail,
                    onCloseDetail = viewModel::closeDetail,
                    onExplicitIntent = { cat ->
                        viewModel.logExplicitIntent(cat)
                        openCatImageIntent(this, cat)
                    },
                    onToggleFavorite = viewModel::toggleFavorite,
                    onToggleDarkMode = viewModel::toggleDarkMode,
                    onToggleFavoriteFilter = viewModel::toggleFavoriteFilter,
                    onClearCache = viewModel::clearCache
                )
            }
        }
    }
}

package com.example.modulmobilecat.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import android.widget.Toast
import coil.compose.AsyncImage
import com.example.modulmobilecat.domain.Cat
import com.example.modulmobilecat.ui.viewmodel.CatUiState
import com.example.modulmobilecat.ui.viewmodel.visibleCats

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatApp(
    state: CatUiState,
    onRefresh: () -> Unit,
    onOpenDetail: (Cat) -> Unit,
    onCloseDetail: () -> Unit,
    onExplicitIntent: (Cat) -> Unit,
    onToggleFavorite: (Cat) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    onToggleFavoriteFilter: () -> Unit,
    onClearCache: () -> Unit
) {
    val selectedCat = state.selectedCat

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedCat == null) state.appTitle else selectedCat.breedName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    if (selectedCat != null) {
                        TextButton(onClick = onCloseDetail) {
                            Text("Kembali")
                        }
                    }
                },
                actions = {
                    TextButton(onClick = onRefresh) {
                        Text("Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (selectedCat == null) {
            CatListScreen(
                state = state,
                modifier = Modifier.padding(paddingValues),
                onOpenDetail = onOpenDetail,
                onExplicitIntent = onExplicitIntent,
                onToggleFavorite = onToggleFavorite,
                onToggleDarkMode = onToggleDarkMode,
                onToggleFavoriteFilter = onToggleFavoriteFilter,
                onClearCache = onClearCache
            )
        } else {
            CatDetailScreen(
                cat = selectedCat,
                modifier = Modifier.padding(paddingValues),
                onExplicitIntent = onExplicitIntent,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

@Composable
private fun CatListScreen(
    state: CatUiState,
    modifier: Modifier = Modifier,
    onOpenDetail: (Cat) -> Unit,
    onExplicitIntent: (Cat) -> Unit,
    onToggleFavorite: (Cat) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    onToggleFavoriteFilter: () -> Unit,
    onClearCache: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SettingsPanel(
                darkMode = state.darkMode,
                showOnlyFavorites = state.showOnlyFavorites,
                onToggleDarkMode = onToggleDarkMode,
                onToggleFavoriteFilter = onToggleFavoriteFilter,
                onClearCache = onClearCache
            )
        }

        if (state.isLoading) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        state.errorMessage?.let { message ->
            item {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (state.visibleCats.isNotEmpty()) {
            item {
                Text(
                    text = "List Horizontal",
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(state.visibleCats, key = { "horizontal-${it.id}" }) { cat ->
                        HorizontalCatCard(
                            cat = cat,
                            onOpenDetail = onOpenDetail,
                            onExplicitIntent = onExplicitIntent
                        )
                    }
                }
            }
            item {
                Text(
                    text = "List Vertical",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(state.visibleCats, key = { it.id }) { cat ->
            CatCard(
                cat = cat,
                onOpenDetail = onOpenDetail,
                onExplicitIntent = onExplicitIntent,
                onToggleFavorite = onToggleFavorite
            )
        }

        if (!state.isLoading && state.visibleCats.isEmpty()) {
            item {
                Text(
                    text = "Belum ada data kucing. Tekan Refresh untuk mengambil data.",
                    modifier = Modifier.padding(vertical = 32.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun HorizontalCatCard(
    cat: Cat,
    onOpenDetail: (Cat) -> Unit,
    onExplicitIntent: (Cat) -> Unit
) {
    Card(
        modifier = Modifier.width(340.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = cat.imageUrl,
                contentDescription = "Gambar ${cat.breedName}",
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = cat.breedName,
                        modifier = Modifier.weight(1f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = cat.origin,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
                    )
                }
                Text(
                    text = "Desc: ${cat.description}",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onExplicitIntent(cat) }) {
                        Text("Gambar")
                    }
                    ElevatedButton(onClick = { onOpenDetail(cat) }) {
                        Text("Detail")
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsPanel(
    darkMode: Boolean,
    showOnlyFavorites: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    onToggleFavoriteFilter: () -> Unit,
    onClearCache: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Pengaturan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Mode gelap")
                Switch(checked = darkMode, onCheckedChange = onToggleDarkMode)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = showOnlyFavorites,
                    onClick = onToggleFavoriteFilter,
                    label = { Text("Favorit saja") }
                )
                OutlinedButton(onClick = onClearCache) {
                    Text("Bersihkan cache")
                }
            }
        }
    }
}

@Composable
private fun CatCard(
    cat: Cat,
    onOpenDetail: (Cat) -> Unit,
    onExplicitIntent: (Cat) -> Unit,
    onToggleFavorite: (Cat) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            AsyncImage(
                model = cat.imageUrl,
                contentDescription = "Gambar ${cat.breedName}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = cat.breedName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = cat.origin,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
                        )
                    }
                    IconButton(onClick = { onToggleFavorite(cat) }) {
                        Text(if (cat.isFavorite) "Fav" else "+")
                    }
                }
                Text(
                    text = cat.temperament,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onOpenDetail(cat) }) {
                        Text("Detail")
                    }
                    ElevatedButton(onClick = { onExplicitIntent(cat) }) {
                        Text("Buka gambar")
                    }
                }
            }
        }
    }
}

@Composable
private fun CatDetailScreen(
    cat: Cat,
    modifier: Modifier = Modifier,
    onExplicitIntent: (Cat) -> Unit,
    onToggleFavorite: (Cat) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            AsyncImage(
                model = cat.imageUrl,
                contentDescription = "Gambar detail ${cat.breedName}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentScale = ContentScale.Crop
            )
        }
        item {
            Text(
                text = cat.breedName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Asal: ${cat.origin}")
            Text(text = "Ukuran gambar: ${cat.width} x ${cat.height}")
        }
        item {
            Text(
                text = "Karakter",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = cat.temperament)
        }
        item {
            Text(
                text = "Deskripsi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = cat.description)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onToggleFavorite(cat) }) {
                    Text(if (cat.isFavorite) "Hapus favorit" else "Tambah favorit")
                }
                OutlinedButton(onClick = { onExplicitIntent(cat) }) {
                    Text("Buka gambar")
                }
            }
        }
    }
}

fun openCatImageIntent(context: android.content.Context, cat: Cat) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cat.imageUrl))
    runCatching {
        context.startActivity(intent)
    }.onFailure {
        Toast.makeText(context, "Tidak ada aplikasi untuk membuka gambar.", Toast.LENGTH_SHORT).show()
    }
}

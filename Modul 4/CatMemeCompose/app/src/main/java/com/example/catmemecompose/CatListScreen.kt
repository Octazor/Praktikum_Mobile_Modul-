package com.example.catmemecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CatListScreen(
    cats: List<Cat>,
    onDetailClick: (Cat) -> Unit,
    onInstagramClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "List Horizontal (LazyRow)",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(cats) { cat ->
                Box(modifier = Modifier.width(320.dp)) {
                    CatItemCard(
                        cat = cat,
                        onDetailClick = onDetailClick,
                        onInstagramClick = onInstagramClick
                    )
                }
            }
        }

        Text(
            text = "List Vertical (LazyColumn)",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(cats) { cat ->
                CatItemCard(
                    cat = cat,
                    onDetailClick = onDetailClick,
                    onInstagramClick = onInstagramClick
                )
            }
        }
    }
}

@Composable
fun CatItemCard(
    cat: Cat,
    onDetailClick: (Cat) -> Unit,
    onInstagramClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = cat.imageResId),
                contentDescription = "Gambar ${cat.name}",
                modifier = Modifier
                    .size(110.dp, 160.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cat.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = cat.ras, style = MaterialTheme.typography.labelMedium)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(
                        text = "Desc: ",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = cat.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onInstagramClick(cat.instagram) },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Instagram")
                    }

                    Button(
                        onClick = { onDetailClick(cat) }
                    ) {
                        Text("Detail")
                    }
                }
            }
        }
    }
}
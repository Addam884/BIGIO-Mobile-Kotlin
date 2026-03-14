package com.takehomechallenge.adamyanuar.ui.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.takehomechallenge.adamyanuar.data.model.Character
import com.takehomechallenge.adamyanuar.ui.theme.Accent
import com.takehomechallenge.adamyanuar.ui.theme.BgDark
import com.takehomechallenge.adamyanuar.ui.theme.Surface1
import com.takehomechallenge.adamyanuar.ui.theme.Surface2
import com.takehomechallenge.adamyanuar.ui.theme.TextMuted
import com.takehomechallenge.adamyanuar.ui.theme.TextPrimary

@Composable
fun HomeScreen(
    characters: List<Character>,
    favorites: List<Int>,
    onClick: (Character) -> Unit,
    onFavorite: (Character) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTopBar()
            if (characters.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(characters) { character ->
                        CharacterCard(
                            character = character,
                            isFavorite = favorites.contains(character.id),
                            onClick = onClick,
                            onFavorite = onFavorite
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(BgDark, BgDark.copy(alpha = 0.95f))
                )
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Column {
            Text(
                text = "Rick & Morty",
                color = Accent,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Characters",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    isFavorite: Boolean,
    onClick: (Character) -> Unit,
    onFavorite: (Character) -> Unit
) {
    val favoriteScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "fav_scale"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface1, RoundedCornerShape(16.dp))
            .border(1.dp, Surface2, RoundedCornerShape(16.dp))
            .clickable { onClick(character) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = character.name,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = character.species,
                    color = TextMuted,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = character.gender,
                color = TextMuted.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }
        IconButton(onClick = { onFavorite(character) }) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color(0xFFFF4D6D) else TextMuted,
                modifier = Modifier.graphicsLayer(
                    scaleX = favoriteScale,
                    scaleY = favoriteScale
                )
            )
        }
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "∅",
                fontSize = 48.sp,
                color = TextMuted
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "No characters available",
                color = TextMuted,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
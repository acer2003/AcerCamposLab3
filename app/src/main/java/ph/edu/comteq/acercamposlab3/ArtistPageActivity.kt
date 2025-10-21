package ph.edu.comteq.acercamposlab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.acercamposlab3.ui.theme.AcerCamposLab3Theme

class ArtistActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcerCamposLab3Theme {
                ArtistScreen()
            }
        }
    }
}

// Data classes to model our data
data class Artwork(
    val imageRes: Int,
    val title: String
)

data class Artist(
    val name: String,
    val years: String,
    val avatarRes: Int,
    val artworks: List<Artwork>
)

val artists = listOf(
    Artist(
        name = "Leonardo da Vinci",
        years = "1452 - 1519",
        avatarRes = R.drawable.leonardo_da_vinci,
        artworks = listOf(
            Artwork(R.drawable.mona_lisa, "Mona Lisa"),
            Artwork(R.drawable.lady_ermine, "Lady with an Ermine"),
            Artwork(R.drawable.litta_madonna, "Litta Madonna")
        )
    ),
    Artist(
        name = "Michelangelo",
        years = "1475 - 1564",
        avatarRes = R.drawable.michelangelo,
        artworks = listOf(
            Artwork(R.drawable.david, "David"),
            Artwork(R.drawable.torment_of_saint_anthony, "The Torment of Saint Anthony"),
            Artwork(R.drawable.delphic_sibyl, "Delphic Sibyl")
        )
    ),
    Artist(
        name = "Gustav Klimt",
        years = "1862 - 1918",
        avatarRes = R.drawable.gustav_klimt,
        artworks = listOf(
            Artwork(R.drawable.the_kiss, "The Kiss"),
            Artwork(R.drawable.adele_bloch_bauer, "Adele Bloch-Bauer I"),
            Artwork(R.drawable.lady_with_fan, "Lady with a Fan")
        )
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Artists", "Artworks")

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                buildAnnotatedString {
                    append("Explore the art of\n")
                    withStyle(style = SpanStyle(color = Color(0xFFD5A81F), fontWeight = FontWeight.Bold)) {
                        append("Renaissance")
                    }
                },
                fontFamily = playfairdisplayregular,
                color = Color.Black,
                fontSize = 28.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Type to search...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                trailingIcon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan Icon") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color(0xFFD5A81F),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color(0xFFD5A81F)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, color = if (selectedTabIndex == index) Color(0xFFD5A81F) else Color.Gray) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedTabIndex == 0) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(artists) { artist ->
                        ArtistCard(artist = artist)
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistCard(artist: Artist) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = artist.avatarRes),
                contentDescription = artist.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(artist.name, fontFamily = playfairdisplayregular, fontSize = 25.sp, color = Color.Black)
                Text(artist.years, fontFamily = optima, fontSize = 20.sp, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(artist.artworks) { artwork ->
                Image(
                    painter = painterResource(id = artwork.imageRes),
                    contentDescription = artwork.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .width(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArtistScreenPreview() {
    AcerCamposLab3Theme {
        ArtistScreen()
    }
}


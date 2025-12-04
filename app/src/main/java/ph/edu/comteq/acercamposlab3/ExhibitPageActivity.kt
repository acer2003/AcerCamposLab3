package ph.edu.comteq.acercamposlab3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import ph.edu.comteq.acercamposlab3.ui.theme.AcerCamposLab3Theme

class ExhibitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcerCamposLab3Theme {
                var artworks by remember { mutableStateOf<List<ExhibitArtwork>>(emptyList()) }
                var isLoading by remember { mutableStateOf(true) }

                // Get artwork titles from intent
                val artworkTitles = intent.getStringArrayListExtra("artwork_titles")

                LaunchedEffect(Unit) {
                    val allArtworks = loadAllArtworksFromJson()

                    if (artworkTitles != null && artworkTitles.isNotEmpty()) {
                        artworks = allArtworks.filter { artwork: ExhibitArtwork ->
                            artworkTitles.any { title ->
                                title.contains(artwork.title, ignoreCase = true) ||
                                        artwork.title.contains(title, ignoreCase = true)
                            }
                        }
                    } else {
                        artworks = allArtworks
                    }
                    isLoading = false
                }

                if (artworks.isNotEmpty()) {
                    ExhibitScreen(artworks = artworks)
                } else {
                    // Show Error/Empty State instead of infinite loading
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color(0xFFFFC107))
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Error",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(50.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "No artworks found.",
                                    color = Color.White,
                                    fontFamily = Optima
                                )
                                Text(
                                    text = "Check assets/artworks.json",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun loadAllArtworksFromJson(): List<ExhibitArtwork> = withContext(Dispatchers.IO) {
        try {
            // Try to load from assets
            val inputStream = assets.open("artworks.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonText = reader.use { it.readText() }
            val jsonArray = JSONArray(jsonText)

            val artworkList = mutableListOf<ExhibitArtwork>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                artworkList.add(
                    ExhibitArtwork(
                        title = obj.getString("title"),
                        years = obj.getString("years"),
                        bornAt = obj.getString("born_at"),
                        comment = obj.getString("comment")
                    )
                )
            }
            artworkList
        } catch (e: Exception) {
            e.printStackTrace()
            // FALLBACK DATA: Use this if JSON fails so app doesn't stick on loading
            Log.e("ExhibitActivity", "Error loading JSON, using fallback data", e)
            listOf(
                ExhibitArtwork("Mona Lisa", "c. 1503-19", "Florence, Italy", "The best known, the most visited, the most written about, the most sung about, the most parodied work of art in the world"),
                ExhibitArtwork("Lady Ermine", "c. 1489-91", "Milan, Italy", "It is a captivating image of exquisite elegance and reveals the artistic genius of Leonardo's incomparable creative mind"),
                ExhibitArtwork("Litta Madonna", "c. 1490", "Italy", "This portrayal reflects the religious devotion of the Renaissance period, emphasizes the virtues of motherhood")
            )
        }
    }
}

// Data class
data class ExhibitArtwork(
    val title: String,
    val years: String,
    val bornAt: String,
    val comment: String
)

// Fonts (Private to avoid conflicts)
private val PlayfairDisplay = FontFamily(Font(R.font.playfairdisplayregular, FontWeight.Normal))
private val Optima = FontFamily(Font(R.font.optima, FontWeight.Normal))

@Composable
fun ExhibitScreen(artworks: List<ExhibitArtwork>) {
    val pagerState = rememberPagerState(pageCount = { artworks.size })
    val currentPage = pagerState.currentPage
    val currentArtwork = artworks.getOrElse(currentPage) { artworks[0] }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Artwork Pager
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(horizontal = 32.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ArtworkCard(
                            artwork = artworks[page],
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Yellow Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC107))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentArtwork.title,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PlayfairDisplay,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${currentArtwork.years}, ${currentArtwork.bornAt}",
                            fontSize = 16.sp,
                            fontFamily = Optima,
                            color = Color(0xFF555555)
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFF1C1C1C),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(12.dp)
                    )
                }
            }

            // Comment Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A1A1A))
                    .padding(horizontal = 48.dp, vertical = 24.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.FormatQuote,
                    contentDescription = "Quote icon",
                    tint = Color(0xFF808080),
                    modifier = Modifier
                        .size(56.dp)
                        .padding(end = 12.dp)
                )

                Crossfade(
                    targetState = currentArtwork.comment,
                    animationSpec = tween(durationMillis = 300),
                    label = "comment_crossfade"
                ) { comment ->
                    Text(
                        text = comment,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = Optima,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ArtworkCard(
    artwork: ExhibitArtwork,
    modifier: Modifier = Modifier
) {
    val imgRes = getArtworkImageRes(artwork.title)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = artwork.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 150.dp, topEnd = 150.dp))
        )
    }
}

private fun getArtworkImageRes(title: String): Int {
    return when {
        title.contains("Mona Lisa", ignoreCase = true) -> R.drawable.mona_lisa
        title.contains("Lady", ignoreCase = true) && title.contains("Ermine", ignoreCase = true) -> R.drawable.lady_ermine
        title.contains("Litta", ignoreCase = true) -> R.drawable.litta_madonna
        title.contains("David", ignoreCase = true) -> R.drawable.david
        title.contains("Torment", ignoreCase = true) -> R.drawable.torment_of_saint_anthony
        title.contains("Sibyl", ignoreCase = true) -> R.drawable.delphic_sibyl
        title.contains("Kiss", ignoreCase = true) -> R.drawable.the_kiss
        title.contains("Adele", ignoreCase = true) -> R.drawable.adele_bloch_bauer
        title.contains("Fan", ignoreCase = true) -> R.drawable.lady_with_fan
        else -> R.drawable.renaissance
    }
}
package ph.edu.comteq.acercamposlab3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ph.edu.comteq.acercamposlab3.ui.theme.AcerCamposLab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcerCamposLab3Theme {
                Homepage()
            }
        }
    }
}

@Composable
fun Homepage(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // --- Animation States ---
    var isMuseumVisible by remember { mutableStateOf(false) }
    var titleText by remember { mutableStateOf("") }
    var introText by remember { mutableStateOf("") }

    val fullTitle = "Experience Art"
    val fullIntro = "We are thrilled to invite you to join us for an extraordinary event that will immerse you in the world of art."

    // --- Animation Sequence Logic ---
    LaunchedEffect(Unit) {
        // 1. Start Museum Animation (Top to Bottom)
        isMuseumVisible = true
        // Wait for the museum animation to complete (approx 1000ms + buffer)
        delay(1200)

        // 2. Typewriter Effect for Title
        for (i in fullTitle.indices) {
            titleText = fullTitle.substring(0, i + 1)
            delay(100) // Typing speed for title
        }
        delay(300) // Short pause before intro starts

        // 3. Typewriter Effect for Introduction
        for (i in fullIntro.indices) {
            introText = fullIntro.substring(0, i + 1)
            delay(30) // Typing speed for body text
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Museum Area with Expand Animation
            AnimatedVisibility(
                visible = isMuseumVisible,
                enter = expandVertically(
                    // Expands from Top to Bottom
                    expandFrom = Alignment.Top,
                    animationSpec = tween(durationMillis = 1000)
                ) + fadeIn(animationSpec = tween(durationMillis = 1000))
            ) {
                // Big image with text overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.louvre),
                        contentDescription = "Louvre",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )

                    // Gradient
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xAA000000)),
                                    startY = 200f
                                )
                            )
                    )

                    // Title Text (Animated via state)
                    Text(
                        text = titleText,
                        fontFamily = playfairdisplayregular,
                        color = Color(0xFFEBD7B5),
                        fontSize = 36.sp,
                        modifier = Modifier.padding(bottom = 28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Introduction Text (Animated via state)
            Text(
                text = introText,
                fontFamily = optima,
                color = Color(0xFFCCCCCC),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Explore Now button
            Button(
                onClick = {
                    context.startActivity(Intent(context, ExploreActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD5A81F)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(52.dp)
            ) {
                Text(
                    text = "Explore Now",
                    fontFamily = playfairdisplayregular,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun HomepagePreview() {
    AcerCamposLab3Theme {
        Homepage()
    }
}
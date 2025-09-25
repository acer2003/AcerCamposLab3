package ph.edu.comteq.acercamposlab3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Divider as HorizontalDivider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.acercamposlab3.ui.theme.AcerCamposLab3Theme

class ExploreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcerCamposLab3Theme {
                ExploreScreen()
            }
        }
    }
}

@Composable
fun ExploreScreen() {
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Explore",
                fontFamily = playfairdisplayregular,
                color = Color(0xFFE8D9B1),
                fontSize = 28.sp
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                color = Color.Gray,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upcoming Event",
                    fontFamily = optima,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tickets",
                        fontFamily = optima,
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    IconButton(
                        onClick = {
                            context.startActivity(Intent(context, TicketingActivity::class.java))
                        },
                        modifier = Modifier
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ConfirmationNumber,
                            contentDescription = "Open Ticketing",
                            tint = Color(0xFFD4AF37)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.renaissance),
                        contentDescription = "Renaissance",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(56.dp)
                        ) {
                            Text(
                                text = "10",
                                fontFamily = playfairdisplayregular,
                                color = Color(0xFFE8D9B1),
                                fontSize = 22.sp
                            )
                            Text(
                                text = "OCT",
                                fontFamily = optima,
                                color = Color(0xFFBDBDBD),
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Renaissance Exhibition",
                                fontFamily = playfairdisplayregular,
                                color = Color.White,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "9:00 AM - 6:00 PM",
                                fontFamily = optima,
                                color = Color(0xFFBDBDBD),
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Indulge in the rich tapestry of Renaissance art",
                                fontFamily = optima,
                                color = Color(0xFFEFCC7E),
                                fontSize = 13.sp,
                                textDecoration = TextDecoration.Underline
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "+33 (0)1 23 45 67 89",
                                fontFamily = optima,
                                color = Color(0xFFBDBDBD),
                                fontSize = 12.sp,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = {
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0XFFD4AF37)
                        )
                    ) {
                        Text(
                            text = "Visit Gallery",
                            fontFamily = playfairdisplayregular,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ExplorePreview() {
    AcerCamposLab3Theme {
        ExploreScreen()
    }
}

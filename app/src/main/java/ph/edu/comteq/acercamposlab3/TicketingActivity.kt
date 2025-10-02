package ph.edu.comteq.acercamposlab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.comteq.acercamposlab3.ui.theme.AcerCamposLab3Theme
import java.time.Duration
import java.time.Instant

class TicketingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcerCamposLab3Theme {
                Scaffold { innerPadding ->
                    Ticketing(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ticketing(modifier: Modifier = Modifier){
    // Date picker state
    val datePickersState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now()
            .plus(Duration.ofDays(2)).toEpochMilli(),
        selectableDates = object: SelectableDates{
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= Instant.now()
                    .plus(Duration.ofDays(1)).toEpochMilli()
            }
        }
    )

    // Ticket counters and prices
    var generalCount by remember { mutableStateOf(0) }
    var freeCount by remember { mutableStateOf(1) }
    val generalPrice = 500
    val total by remember { derivedStateOf { generalCount * generalPrice } }

    Column(
        modifier = modifier
            .background(Color.Black)
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ){
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.luffy),
                    contentDescription = "Luffy",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(Color.Black.copy(alpha = 0.7f))
                )
                Text(
                    text = "Official\nTicketing Service",
                    fontSize = 32.sp,
                    fontFamily = playfairdisplayregular,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )
            }

            // Inner container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ){
                // Date Picker
                DatePicker(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    state = datePickersState,
                    title = null,
                    showModeToggle = false,
                    headline = {
                        Text(
                            "1. Date to Visit",
                            fontSize = 26.sp,
                            fontFamily = playfairdisplayregular
                        )
                    },
                    colors = DatePickerDefaults.colors(
                        titleContentColor = Color(0xFFd29f1b),
                        headlineContentColor = Color(0xFFd29f1b),
                        weekdayContentColor = Color(0xFFd29f1b),
                        containerColor = Color.Transparent,
                        dayContentColor = Color.White,
                        todayContentColor = Color(0xFFd29f1b),
                        selectedDayContainerColor = Color(0xFFd29f1b),
                        selectedDayContentColor = Color.Black,
                        disabledDayContentColor = Color.Gray,
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Ticket Section
                Text(
                    "2. Number of Tickets",
                    fontSize = 26.sp,
                    fontFamily = playfairdisplayregular,
                    color = Color(0xFFd29f1b),
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                // General Admission
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("General Admission", color = Color.White, fontSize = 18.sp)
                        Text("₱$generalPrice", color = Color(0xFFd29f1b), fontSize = 18.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            onClick = { if (generalCount > 0) generalCount-- },
                            modifier = Modifier.size(44.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("-", fontSize = 20.sp, color = Color.White)
                        }

                        Text(
                            "$generalCount",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        )

                        OutlinedButton(
                            onClick = { generalCount++ },
                            modifier = Modifier.size(44.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("+", fontSize = 20.sp, color = Color.White)
                        }
                    }
                }

                // Free Tickets
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Under 18s, Under 26s,\nresidents of the EEA,\nMuseum members,\nProfessionals",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Text("FREE", color = Color(0xFFd29f1b), fontSize = 18.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            onClick = { if (freeCount > 0) freeCount-- },
                            modifier = Modifier.size(44.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("-", fontSize = 20.sp, color = Color.White)
                        }

                        Text(
                            "$freeCount",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        )

                        OutlinedButton(
                            onClick = { freeCount++ },
                            modifier = Modifier.size(44.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("+", fontSize = 20.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFFd29f1b))
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Total: ₱$total",
                fontSize = 26.sp,
                fontFamily = playfairdisplayregular,
                color = Color.Black
            )
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = { /* TODO: checkout logic */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ){
                Text(
                    "Checkout",
                    fontSize = 20.sp,
                    fontFamily = playfairdisplayregular,
                    color = Color(0xFFd29f1b)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketingPreview() {
    AcerCamposLab3Theme {
        Scaffold { innerPadding ->
            Ticketing(modifier = Modifier.padding(innerPadding))
        }
    }
}

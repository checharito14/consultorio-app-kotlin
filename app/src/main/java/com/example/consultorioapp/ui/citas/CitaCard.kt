package com.example.consultorioapp.ui.citas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.consultorioapp.R
import com.example.consultorioapp.ui.theme.AppTheme

@Composable
fun CitaCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(horizontal = 25.dp, vertical = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Cesar Rice Peraza",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text("Revision General", style = MaterialTheme.typography.bodyMedium)
            }
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Details")
        }

        Spacer(Modifier.height(5.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        bottom = 15.dp,
                        end = 8.dp,
                        start = 8.dp
                    )
                ),
            color = Color(0xff006b5a),
            shape = MaterialTheme.shapes.small,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp).clickable {  },
                        painter = painterResource(id = R.drawable.ic_schedule),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,

                    )
                    Text(
                        "Lunes",
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Light
                    )
                }
                Text(
                    "May 9, 2022",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Light
                )
                Text(
                    "8:00 - 8:45am",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CitaCardPreview() {
    AppTheme {
        CitaCard()
    }
}
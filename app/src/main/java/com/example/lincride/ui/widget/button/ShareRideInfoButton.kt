import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShareRideInfoButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp, top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = { /* Share ride info */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF383838)
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFFD1D1D1)
            ),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(
                text = "Share Ride Info",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
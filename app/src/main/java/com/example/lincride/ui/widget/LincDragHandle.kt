import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.lincride.ui.theme.LincColors

@Composable
fun LincDragHandle(modifier: Modifier = Modifier) {
    Surface(
        modifier =
        Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .semantics {
            }
            .then(modifier),
        color = LincColors.stroke2,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Box(Modifier.size(width = 80.dp, height = 5.0.dp))
    }
}


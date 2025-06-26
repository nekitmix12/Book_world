package nekit.corporation.common_ui.ui_kit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import java.lang.Integer.min

@Composable
fun Grid(
    column: Int,
    row: Int,
    element: ImmutableList<@Composable (Modifier) -> Unit>,
    elementModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    dividerSize: Pair<Int, Int> = Pair(0, 0)
) {
    var counter by remember { mutableIntStateOf(0) }
    Column(modifier) {
        repeat(row) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)

            ) {
                for (el in element.slice(
                    it * column until min(
                        it * (column) + column, element.size
                    )
                )) {
                    el(elementModifier.weight(1f))
                    if (counter != column - 1) {
                        Spacer(Modifier.width(dividerSize.first.dp))
                        counter++
                    } else
                        counter = 0
                }
            }
            if (it != row - 1)
                Spacer(Modifier.height(dividerSize.second.dp))
        }
    }
}
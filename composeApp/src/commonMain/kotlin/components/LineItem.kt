package components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import data.LineItem

@Composable
fun LineItemRow(
    item: LineItem,
    onItemChange: (LineItem) -> Unit,
    onRemoveItem: () -> Unit
) {
    Column {
        OutlinedTextField(
           value = item.item,
            onValueChange = {
                onItemChange(item.copy(item = it))
            }
        )
        OutlinedTextField(

            value = "${item.rate}",
            onValueChange = { onItemChange(item.copy(rate = it.toDoubleOrNull()?:0.0)) }
        )
        OutlinedTextField(
            value = "${item.totalItems}",
            onValueChange = { onItemChange(item.copy(totalItems = it.toDoubleOrNull()?:0.0)) }

        )

    }
}
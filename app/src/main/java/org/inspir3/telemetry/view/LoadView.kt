package org.inspir3.telemetry.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * https://medium.com/make-apps-simple/multi-list-item-selection-in-jetpack-compose-301fcf375a6c
 */
@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = false)
@Composable
fun LoadView(
    mainRoute: () -> Unit = {},
    loadFile: (String) -> Unit = {},
    files: List<String> = listOf("item1", "item2", "item3", "item4", "item5", "item6")
) {
    Column {
        Row {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(files) { file ->
                    ListItem(
                        modifier = Modifier.combinedClickable(
                            onClick = { loadFile(file) },
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                            )
                        },
                        headlineContent = {
                            Text(
                                text = file,
                            )
                        },
                    )
                }
            }
        }
        Row {
            Button(onClick = {
                mainRoute()
            }) {
                Text("Back")
            }
        }
    }
}

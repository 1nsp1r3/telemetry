package org.inspir3.telemetry.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import org.inspir3.common.file.Fichier

/**
 * https://medium.com/make-apps-simple/multi-list-item-selection-in-jetpack-compose-301fcf375a6c
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadView(
    mainRoute: () -> Unit = {},
    loadFile: (Fichier) -> Unit = {},
    files: List<Fichier>,
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
                                imageVector = ImageVector.vectorResource(id = org.inspir3.telemetry.R.drawable.chart),
                                contentDescription = null,
                            )
                        },
                        headlineContent = {
                            Text(
                                text = "${file.name} (${file.getFilesizeForHuman()})",
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

package com.spiphy.screentime.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun GenericDialog(
    showDialog: Boolean,
    toggleDialog: () -> Unit,
    options: List<String>,
    title: String,
    hintText: String = "",
    textLabel: String,
    ok: String,
    cancel: String,
    onOk: (selected: String,textField: String) -> Unit
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }
    var customText by remember { mutableStateOf(hintText) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = toggleDialog,
            title = {
                Text(text = title)
            },
            text = {
                Column {
                    Column(Modifier.selectableGroup()) {
                        options.forEach { option ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (option == selectedOption),
                                        onClick = { onOptionSelected(option) },
                                        role = Role.RadioButton
                                    )
                                    .padding(8.dp),
                            ) {
                                RadioButton(
                                    selected = (option == selectedOption),
                                    onClick = null
                                )
                                Text(
                                    text = option,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 8.dp)
                                )
                            }
                        }
                    }
                    OutlinedTextField(
                        value = customText,
                        onValueChange = {text -> customText = text},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text(textLabel) }
                    )
                }
            },

            confirmButton = {
                TextButton(onClick = {
                    toggleDialog()
                    onOk(selectedOption, customText)
                }) {
                    Text(ok)
                }
            },
            dismissButton = {
                TextButton(onClick = toggleDialog ) {
                    Text(cancel)
                }
            }
        )
    }
}
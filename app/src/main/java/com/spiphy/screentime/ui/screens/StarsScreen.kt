package com.spiphy.screentime.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spiphy.screentime.R
import com.spiphy.screentime.model.StarGroup
import com.spiphy.screentime.model.testStarGroup
import com.spiphy.screentime.ui.screens.components.ErrorScreen
import com.spiphy.screentime.ui.screens.components.GenericDialog
import com.spiphy.screentime.ui.screens.components.LoadingScreen


private val showAwardDialog = mutableStateOf(false)
private val showRedeemDialog = mutableStateOf(false)
private val selectedStarGroup = mutableStateOf<StarGroup?>(null)
private var onAwardStar: (starNote: String) -> Unit = {}
private var onRedeemStar: (starGroup: StarGroup, note: String) -> Unit = { _, _ -> }
private var onConverToScreenTime: () -> Unit = {}

@Composable
fun StarsScreen(
    viewModel: StarViewModel,
    retryAction: () -> Unit,
    contentPadding: PaddingValues
) {
    val uiState = viewModel.starUiState
    when (uiState) {
        is StarUiState.Loading -> LoadingScreen(
            contentPadding = contentPadding,
            modifier = Modifier
        )

        is StarUiState.Success -> {
            onAwardStar = { note -> viewModel.awardStar(note) }
            onRedeemStar = { starGroup, note -> viewModel.redeemStar(starGroup, note) }
            onConverToScreenTime = { viewModel.convertToScreenTime() }
            Stars(
                uiState.starGroups, contentPadding = contentPadding
            )
        }

        is StarUiState.Error -> ErrorScreen(retryAction, Modifier)
    }
}

@Composable
fun Stars(
    starGroups: List<StarGroup>,
    contentPadding: PaddingValues
) {
    var orderedGroups = starGroups.sortedBy { it.earned }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAwardDialog.value = true }
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.award_ticket))
            }
        },
        modifier = Modifier.padding(contentPadding)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
        ) {
            items(orderedGroups) { starGroup ->
                StarGroup(
                    starGroup = starGroup,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
            }
        }

        //Award Star Dialog
        GenericDialog(
            showDialog = showAwardDialog.value,
            toggleDialog = { showAwardDialog.value = !showAwardDialog.value },
            options = listOf("Bed", "Reading", "Glasses", "Custom"),
            title = stringResource(R.string.award_star),
            hintText = "",
            textLabel = stringResource(R.string.custom_reason),
            ok = stringResource(R.string.award_star),
            cancel = stringResource(R.string.cancel),
            onOk = { selected, text ->
                var note = selected
                if (note == "Custom") {
                    note = text
                }
                onAwardStar(note)
            }
        )

        //Redeem Star Dialog
        GenericDialog(
            showDialog = showRedeemDialog.value,
            toggleDialog = { showRedeemDialog.value = !showRedeemDialog.value },
            options = listOf("Screen Time", "Other"),
            title = stringResource(R.string.redeem_star),
            hintText = "",
            textLabel = stringResource(R.string.custom),
            ok = stringResource(R.string.redeem_star),
            cancel = stringResource(R.string.cancel),
            onOk = { selected, text ->
                var note = text
                if (selectedStarGroup.value != null) {
                    if (selected == "Screen Time") {
                        onConverToScreenTime()
                        onConverToScreenTime()
                        note = "Screen Time"
                    }
                    onRedeemStar(selectedStarGroup.value!!, note)
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StarGroup(
    starGroup: StarGroup,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val count = starGroup.stars.count()
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    expanded = !expanded
                },
                onLongClick = {
                    val fullSet = starGroup.earned == 10
                    if (!starGroup.used && fullSet) {
                        selectedStarGroup.value = starGroup
                        showRedeemDialog.value = true
                    }
                }
            )
    ) {
        Row {
            Column(
                modifier = Modifier
                    .alpha(if (starGroup.used) 0.5f else 1.0f)
            ) {
                Row {
                    for (i in 1..5) {
                        Image(
                            painter = painterResource(id = if (i <= count) R.drawable.star else R.drawable.star_outline),
                            contentDescription = null,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Row {
                    for (i in 6..10) {
                        Image(
                            painter = painterResource(id = if (i <= count) R.drawable.star else R.drawable.star_outline),
                            contentDescription = null,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                if (expanded) {
                    starGroup.stars.forEach { star ->
                        Text(
                            modifier = Modifier.padding(2.dp),
                            text = "${star.note} - ${Utilities.starToTimeString(star)}"
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun StarsScreenPreview() {
    StarGroup(testStarGroup[0], Modifier)
}
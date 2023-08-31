package com.apero.sample.ui.compose.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.sample.ui.compose.theme.DefaultTextStyleTitle

@Composable
@Preview
private fun SpinnerDefaultPreview() {
    val optionCreateQR = arrayOf("Add QR Text", "Add QR Image")
    val itemSelected = optionCreateQR.firstOrNull()
    val listDropdown = optionCreateQR.map { DropDownModel(label = it, data = it) }
    SpinnerDefault(
        modifier = Modifier,
        list = listDropdown,
        onItemSelectedListener = {}) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = itemSelected ?: "",
            style = DefaultTextStyleTitle
        )
    }
}

data class DropDownModel<T : Any>(val label: String, val data: T)

@Composable
fun <T : Any> SpinnerDefault(
    modifier: Modifier = Modifier,
    list: List<DropDownModel<T>>,
    onItemSelectedListener: (item: T) -> Unit,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.clickable { expanded = !expanded }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            content()
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (expanded) 180f else 0f),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .align(Alignment.Center)
                .width(IntrinsicSize.Max)
                .background(Color.White)
        ) {
            list.forEach { data ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onItemSelectedListener(data.data)
                }, text = {
                    Text(text = data.label)
                })
            }
        }
    }
}
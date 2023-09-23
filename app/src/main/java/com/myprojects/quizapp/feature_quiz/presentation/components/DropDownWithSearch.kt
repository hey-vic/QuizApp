package com.myprojects.quizapp.feature_quiz.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.Black50
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.Black90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White70
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.White90
import com.myprojects.quizapp.feature_quiz.presentation.ui.theme.rubicFF

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownWithSearch(
    options: List<String>,
    onOptionSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedOptionText by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = CircleShape,
            value = selectedOptionText,
            textStyle = LocalTextStyle.current.copy(
                fontFamily = rubicFF,
                fontWeight = FontWeight.Normal
            ),
            onValueChange = {
                selectedOptionText = it
                expanded = true
            },
            trailingIcon = {
                Icon(
                    imageVector = if (selectedOptionText.isEmpty()) {
                        if (expanded) {
                            Icons.Default.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        }
                    } else {
                        Icons.Default.Clear
                    },
                    contentDescription = if (selectedOptionText.isEmpty()) {
                        "View options"
                    } else {
                        "Clear"
                    },
                    modifier = Modifier.clickable {
                        if (selectedOptionText.isNotEmpty()) {
                            selectedOptionText = ""
                            onOptionSelected(null)
                            expanded = false
                            focusManager.clearFocus()
                        }
                    }
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                textColor = White90,
                placeholderColor = White70,
                focusedBorderColor = White90,
                unfocusedBorderColor = White70,
                focusedTrailingIconColor = White90,
                unfocusedTrailingIconColor = White70,
                cursorColor = White70,
                selectionColors = TextSelectionColors(
                    handleColor = White70,
                    backgroundColor = Black50
                )
            ),
            placeholder = {
                Text(
                    text = "Select a category",
                    fontFamily = rubicFF,
                    fontWeight = FontWeight.Normal
                )
            },
            maxLines = 1,
            singleLine = true
        )
        val filteredOptions = options.filter { it.contains(selectedOptionText, ignoreCase = true) }
        if (filteredOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                filteredOptions.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                fontFamily = rubicFF,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        onClick = {
                            selectedOptionText = option
                            expanded = false
                            onOptionSelected(option)
                            focusManager.clearFocus()
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        colors = MenuDefaults.itemColors(
                            textColor = Black90
                        ),
                        modifier = Modifier.background(Color.White)
                    )
                }
            }
        }
    }
}
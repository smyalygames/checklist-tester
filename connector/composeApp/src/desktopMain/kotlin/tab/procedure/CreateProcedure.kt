package tab.procedure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class CreateProcedure : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val columnPadding = 24.dp

        // Information
        val procedureTypeOptions = listOf("Normal", "Emergency")
        var procedureType by remember { mutableStateOf(procedureTypeOptions[0]) }
        var procedureTypeExpanded by remember { mutableStateOf(false) }

        var procedureName by remember { mutableStateOf("") }
        var procedureDescription by remember { mutableStateOf("") }

        // Actions
        val actionTypeOptions = listOf("Switch", "Button", "Lever")
        var actionType by remember { mutableStateOf(procedureTypeOptions[0]) }
        var actionTypeExpanded by remember { mutableStateOf(false) }


        Scaffold (
            topBar = {
                IconButton(
                    onClick = {
                        navigator.pop()
                    }
                ) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Back Arrow")
                }
            },
        ) {
            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(top = columnPadding, bottom = columnPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column (
                    Modifier.fillMaxWidth(0.7F),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Information",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = procedureName,
                        onValueChange = { procedureName = it },
                        label = { Text("Procedure Name") },
                        singleLine = true,
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = procedureDescription,
                        onValueChange = { procedureDescription = it },
                        label = { Text("Description") },
                        singleLine = true,
                    )

                    exposedDropDownType(procedureTypeExpanded, procedureType, procedureTypeOptions)

                    HorizontalDivider()

                    Text(
                        text = "Actions",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    exposedDropDownType(actionTypeExpanded, actionType, actionTypeOptions)

                    Button(
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                        onClick = {
                            // TODO save new procedure and make sure it redirects to list page
                            navigator.pop()
                        }
                    ) {
                        Icon(Icons.Outlined.Add, "Create Procedure", modifier = Modifier.size(18.dp))
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Create", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun exposedDropDownType(
        procedureTypeExpanded: Boolean,
        procedureType: String,
        procedureTypeOptions: List<String>
    ) {
        var procedureTypeExpanded1 = procedureTypeExpanded
        var procedureType1 = procedureType
        ExposedDropdownMenuBox(
            expanded = procedureTypeExpanded1,
            onExpandedChange = { procedureTypeExpanded1 = it }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = procedureType1,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text("Type") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = procedureTypeExpanded1) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = procedureTypeExpanded1,
                onDismissRequest = { procedureTypeExpanded1 = false },
            ) {
                procedureTypeOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            procedureType1 = option
                            procedureTypeExpanded1 = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

package tab.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class CreateProject : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<ProjectsScreenModel>()

        var projectName by remember { mutableStateOf("") }
        var aircraftType by remember { mutableStateOf("") }

        IconButton(
            onClick = {
                navigator.pop()
            }
        ) {
            Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Back Arrow")
        }

        Column (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Column (
                Modifier.fillMaxWidth(0.7F),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = projectName,
                    onValueChange = { projectName = it },
                    label = { Text("Project Name") },
                    singleLine = true,
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = aircraftType,
                    onValueChange = { aircraftType = it },
                    label = { Text("Aircraft Type") },
                    supportingText = { Text("ICAO Code") },
                    singleLine = true,
                )

                Button(
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                    onClick = {
                        if (projectName.isNotBlank() and aircraftType.isNotBlank()) {
                            screenModel.createProject(
                                projectName = projectName,
                                aircraftType = aircraftType
                            )
                            navigator.pop()
                        }
                    }
                ) {
                    Icon(Icons.Outlined.Add, "Create Project", modifier = Modifier.size(18.dp))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Create", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }

}

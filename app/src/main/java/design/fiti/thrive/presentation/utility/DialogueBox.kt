package design.fiti.thrive.presentation.utility

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import design.fiti.thrive.R
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate
import design.fiti.thrive.presentation.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDialogueBox(
    value: String,
    setShowDialog: (Boolean) -> Unit,
    setValue: (String) -> Unit,
    homeViewmodel: HomeViewModel
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val screenState by viewModel.uiState.collectAsState()
    val txtFieldError = remember { mutableStateOf("") }
    val nameField = remember { mutableStateOf(value) }
    val amountField = remember { mutableStateOf(value) }


    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(0.88f)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (homeViewmodel.getSelectedTransactionType() == "income") "Income" else "Expense",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
//                            tint = colorResource(R.color.purple_200),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
//                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.purple_200 else R.color.purple_200)
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                shape = RoundedCornerShape(30)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "",
//                                tint = colorResource(R.color.purple_200),
                            )
                        },
                        placeholder = { Text(text = "Enter name") },
                        value = nameField.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            nameField.value = it.take(10)
                        })

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
//                                    color = colorResource(id = if (txtFieldError.value.isEmpty()) R.color.purple_200 else R.color.purple_200)
                                    color = MaterialTheme.colorScheme.secondary
                                ),
                                shape = RoundedCornerShape(30)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "",
//                                tint = colorResource(R.color.purple_200),
                            )
                        },
                        placeholder = { Text(text = "Enter amount") },
                        value = amountField.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            amountField.value = it.take(10)
                        })
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(

                            onClick = {

                                if (amountField.value.isEmpty() && nameField.value.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    return@Button
                                } else {
                                    viewModel.handleCreateTransaction(
                                        amount = amountField.value.toInt(),
                                        nameOfYourTransaction = nameField.value
                                    )
                                    setShowDialog(false)
                                }
                                setValue(nameField.value)

                            },
                            shape = RoundedCornerShape(30),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {

                            Text(text = "Done")

                        }
                    }
                }
            }
        }


    }
}

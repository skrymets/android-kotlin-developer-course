package my.course.unitconvertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import my.course.unitconvertor.tool.MeasureValue
import my.course.unitconvertor.tool.Prefix
import my.course.unitconvertor.tool.base
import my.course.unitconvertor.tool.centi
import my.course.unitconvertor.tool.centimetre
import my.course.unitconvertor.tool.metre
import my.course.unitconvertor.tool.milli
import my.course.unitconvertor.tool.millimetre
import my.course.unitconvertor.tool.times
import my.course.unitconvertor.ui.theme.UnitConvertorTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConvertorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}


@Composable
fun UnitConverter() {
    var userInput by remember { mutableStateOf("") }
    var conversionResult by remember { mutableStateOf("") }

    var inputUnit by remember { mutableStateOf("From") }
    var outputUnit by remember { mutableStateOf("To") }
    var inUnitPopupVisible by remember { mutableStateOf(false) }
    var outUnitPopupVisible by remember { mutableStateOf(false) }

    var fromFactor: MeasureValue by remember { mutableStateOf(1.0.metre) }
    var toFactor: MeasureValue by remember { mutableStateOf(1.0.metre) }

    fun convertUnits() {
        val valueToConvert = userInput.toDoubleOrNull() ?: 0.0
        conversionResult = ((valueToConvert * fromFactor) / toFactor).toString()
    }

    verticalSpacer()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Unit Converter")
        verticalSpacer()
        OutlinedTextField(
            value = userInput,
            onValueChange = {
                userInput = it
                convertUnits()
            },
            label = {
                Text("Value to Convert")
            },
            singleLine = true
        )
        verticalSpacer()
        Row {
            val context = LocalContext.current
            Box {
                Button(onClick = { inUnitPopupVisible = true }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(
                    expanded = inUnitPopupVisible,
                    onDismissRequest = { inUnitPopupVisible = false }
                ) {
                    DropdownMenuItem({ Text(text = "Millimeters") }, {
                        inputUnit = "Millimeters"
                        inUnitPopupVisible = false
                        fromFactor = 1.0.millimetre
                        convertUnits()
                    })
                    DropdownMenuItem({ Text(text = "Centimeters") }, {
                        inputUnit = "Centimeters"
                        inUnitPopupVisible = false
                        fromFactor = 1.0.centimetre
                        convertUnits()
                    })
                    DropdownMenuItem({ Text(text = "Meters") }, {
                        inputUnit = "Meters"
                        inUnitPopupVisible = false
                        fromFactor = 1.0.metre
                        convertUnits()
                    })
                }
            }
            Spacer(Modifier.width(16.dp))
            Box {
                Button(onClick = { outUnitPopupVisible = true }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(
                    expanded = outUnitPopupVisible,
                    onDismissRequest = { outUnitPopupVisible = false },
                ) {
                    DropdownMenuItem({ Text(text = "Millimeters") }, {
                        outputUnit = "Millimeters"
                        outUnitPopupVisible = false
                        toFactor = 1.0.millimetre
                        convertUnits()
                    })
                    DropdownMenuItem({ Text(text = "Centimeters") }, {
                        outputUnit = "Centimeters"
                        outUnitPopupVisible = false
                        toFactor = 1.0.centimetre
                        convertUnits()
                    })
                    DropdownMenuItem({ Text(text = "Meters") }, {
                        outputUnit = "Meters"
                        outUnitPopupVisible = false
                        toFactor = 1.0.metre
                        convertUnits()
                    })
                }
            }
        }
        verticalSpacer()
        Text(text = conversionResult)
    }
}

@Composable
private fun verticalSpacer() =
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    )

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConvertorTheme {
        UnitConverter()
    }
}
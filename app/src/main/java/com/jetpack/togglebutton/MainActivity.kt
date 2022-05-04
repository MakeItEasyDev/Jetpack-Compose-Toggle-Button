package com.jetpack.togglebutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetpack.togglebutton.ui.theme.ToggleButtonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToggleButtonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Compose Toggle Button",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            ToggleButtonExp()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToggleButtonExp() {
    val options = arrayOf(
        ToggleButtonOption(
            "Projects",
            iconRes = R.drawable.ic_star
        ),
        ToggleButtonOption(
            "Upcoming",
            iconRes = R.drawable.ic_upcoming
        )
    )

    ToggleButton(
        options = options,
        type = SelectionType.SINGLE,
        modifier = Modifier.padding(end = 4.dp),
        onClick = {  }
    )
}

enum class SelectionType {
    NONE, SINGLE, MULTIPLE
}

data class ToggleButtonOption(
    val text: String,
    val iconRes: Int?
)

@Composable
fun SelectionItem(
    option: ToggleButtonOption,
    selected: Boolean,
    onClick: (option: ToggleButtonOption) -> Unit = {}
) {
    Button(
        onClick = { onClick(option) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background
        ),
        shape = RoundedCornerShape(0),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(14.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = option.text,
                color = if (selected) Color.Blue else Color.LightGray,
                modifier = Modifier.padding(0.dp)
            )

            if (option.iconRes != null) {
                Icon(
                    painter = painterResource(id = option.iconRes),
                    contentDescription = "Icons",
                    tint = if (selected) Color.Blue else Color.LightGray,
                    modifier = Modifier
                        .padding(4.dp, 2.dp, 2.dp, 2.dp)
                )
            }
        }
    }
}

@Composable
fun ToggleButton(
    options: Array<ToggleButtonOption>,
    modifier: Modifier = Modifier,
    type: SelectionType = SelectionType.SINGLE,
    onClick: (selectedOptionS: Array<ToggleButtonOption>) -> Unit = {}
) {
    val state = remember { mutableStateMapOf<String, ToggleButtonOption>() }

    OutlinedButton(
        onClick = { /*TODO*/ },
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.LightGray),
        contentPadding = PaddingValues(0.dp, 0.dp),
        modifier = modifier
            .padding(0.dp)
            .height(52.dp)
    ) {
        if (options.isEmpty()) {
            return@OutlinedButton
        }

        val onItemClick: (option: ToggleButtonOption) -> Unit = { option ->
            if (type == SelectionType.SINGLE) {
                options.forEach {
                    val key = it.text
                    if (key == option.text) {
                        state[key] = option
                    } else {
                        state.remove(key)
                    }
                }
            } else {
                val key = option.text
                if (!state.contains(key)) {
                    state[key] = option
                } else {
                    state.remove(key)
                }
            }
            onClick(state.values.toTypedArray())
        }

        if (options.size == 1) {
            val option = options.first()

            SelectionItem(
                option = option,
                selected = state.contains(option.text),
                onClick = onItemClick
            )
            return@OutlinedButton
        }

        val first = options.first()
        val last = options.last()
        val middle = options.slice(1..options.size - 2)

        SelectionItem(
            option = first,
            selected = state.contains(first.text),
            onClick = onItemClick
        )

        Divider(modifier = Modifier
            .fillMaxHeight()
            .width(2.dp))

        middle.map { option ->
            SelectionItem(
                option = option,
                selected = state.contains(option.text),
                onClick = onItemClick
            )
            Divider(modifier = Modifier
                .fillMaxHeight()
                .width(2.dp))
        }
        SelectionItem(
            option = last,
            selected = state.contains(last.text),
            onClick = onItemClick
        )
    }
}























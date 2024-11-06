package com.my.customviewdemo1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.my.customviewdemo1.databinding.ActivityMainBinding
import com.my.customviewdemo1.view.BatteryView
import com.my.customviewdemo1.view.LogoView
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.layoutComposeView1.setContent {
            val selectedView = remember {
                mutableStateOf(ViewGroup.BatteryView)
            }
            Column(
                modifier = Modifier
                    .background(color = Color.White)
            ) {
                LayoutCustomView(selectedView.value)
                LayoutListView(
                    onItemClick = {
                        selectedView.value = it
                    }
                )
            }
        }

    }
}

@Composable
fun LayoutCustomView(viewGroup: ViewGroup) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
    ) {
        Views(viewGroup)
    }

}

@Composable
fun LayoutListView(onItemClick: (ViewGroup) -> Unit) {
    val viewList = listOf(ViewGroup.BatteryView, ViewGroup.LogoView)
    
    val state = LazyListState()

    LazyColumn(
        state = state
    ) {
        itemsIndexed(viewList) { index: Int, item: ViewGroup ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick.invoke(item)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    text = item.name
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Color.LightGray), )
            }
        }
    }
}

@Composable
fun Views(vg: ViewGroup) {
    when(vg.name) {
        ViewGroup.BatteryView.name -> {
            var percentage by remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                while (percentage < 100) {
                    percentage += 10
                    delay(1000)
                }
            }

            return BatteryView(
                modifier = Modifier
                    .aspectRatio(1f),
                percentage = percentage
            )
        }
        ViewGroup.LogoView.name -> {
            return LogoView(
                modifier = Modifier
                    .aspectRatio(1f),
                imageResId = R.drawable.ic_launcher_background
            )
        }
    }
}

@Preview
@Composable
fun LayoutCustomViewPreview() {
    LayoutCustomView(ViewGroup.BatteryView)
}

@Preview(showBackground = true)
@Composable
fun LayoutListViewPreview() {
    LayoutListView(
        onItemClick = { }
    )
}

enum class ViewGroup {
    BatteryView, LogoView
}
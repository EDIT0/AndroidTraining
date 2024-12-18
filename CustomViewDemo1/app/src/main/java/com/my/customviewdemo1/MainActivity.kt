package com.my.customviewdemo1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.customviewdemo1.databinding.ActivityMainBinding
import com.my.customviewdemo1.databinding.ItemViewBinding
import com.my.customviewdemo1.view.compose.BatteryView
import com.my.customviewdemo1.view.compose.LogoView
import com.my.customviewdemo1.view.xml.ProgressIconButtonView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var viewGroupAdapter: ViewGroupAdapter
    private var xmlViewList = listOf(
        XmlViewGroup.ProgressIconButtonView
    )

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

        layoutVisibleGone()

        binding.layoutComposeView1.setContent {
            val selectedView = remember {
                mutableStateOf(ComposeViewGroup.BatteryView)
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

        viewGroupAdapter = ViewGroupAdapter (
            onItemClick = {
                when(it) {
                    XmlViewGroup.ProgressIconButtonView -> {
                        binding.layoutInflateView.removeAllViews()
                        val inflater = LayoutInflater.from(binding.layoutInflateView.context)
                        val newLayout = inflater.inflate(R.layout.progress_icon_button_view, binding.layoutInflateView, false)
                        binding.layoutInflateView.addView(newLayout)

                        controlProgressIconButtonView()
                    }
                }
            }
        )

        binding.rvXmlViewGroup.apply {
            layoutManager = LinearLayoutManager(binding.rvXmlViewGroup.context, LinearLayoutManager.VERTICAL, false)
            adapter = viewGroupAdapter
            addItemDecoration(DividerItemDecoration(binding.rvXmlViewGroup.context, LinearLayoutManager.VERTICAL))
        }

        viewGroupAdapter.submitList(xmlViewList)
    }

    private fun layoutVisibleGone() {
        binding.btnCompose.setOnClickListener {
            binding.layoutComposeView1.visibility = View.VISIBLE
            binding.layoutXmlView1.visibility = View.GONE
        }

        binding.btnXml.setOnClickListener {
            binding.layoutComposeView1.visibility = View.GONE
            binding.layoutXmlView1.visibility = View.VISIBLE
        }

        binding.layoutXmlView1.visibility = View.GONE
    }

    private fun controlProgressIconButtonView() {
        val progressIconButtonView = binding.layoutInflateView.findViewById<ProgressIconButtonView>(R.id.progressIconButtonView)

        // 직접 percentage 설정
        progressIconButtonView.apply {
            setIcon(R.drawable.ic_rounded_lock_24, R.drawable.ic_rounded_lock_white_24)
            setIconSize(48f)
            setOnClickListener {
                if(getPercentage().toInt() != 0) {
                    return@setOnClickListener
                }
                lifecycleScope.launch {
                    for(i in 0 until 101) {
                        delay(50L)
                        setPercentage(i)
                    }
                    setPercentage(0)
                }
            }
        }

        // 일정시간 동안 애니메이션
//        progressIconButtonView.apply {
//            setIcon(R.drawable.ic_rounded_lock_open_24, R.drawable.ic_rounded_lock_open_white_24)
//            setIconSize(36f)
//            setOnClickListener {
//                if(getPercentage().toInt() != 0) {
//                    return@setOnClickListener
//                }
//                setAutoPercentage()
//            }
//        }
    }
}

@Composable
fun LayoutCustomView(composeViewGroup: ComposeViewGroup) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
    ) {
        Views(composeViewGroup)
    }

}

@Composable
fun LayoutListView(onItemClick: (ComposeViewGroup) -> Unit) {
    val viewList = listOf(ComposeViewGroup.BatteryView, ComposeViewGroup.LogoView)
    
    val state = LazyListState()

    LazyColumn(
        state = state
    ) {
        itemsIndexed(viewList) { index: Int, item: ComposeViewGroup ->
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
fun Views(cvg: ComposeViewGroup) {
    when(cvg.name) {
        ComposeViewGroup.BatteryView.name -> {
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
        ComposeViewGroup.LogoView.name -> {
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
    LayoutCustomView(ComposeViewGroup.BatteryView)
}

@Preview(showBackground = true)
@Composable
fun LayoutListViewPreview() {
    LayoutListView(
        onItemClick = { }
    )
}

enum class ComposeViewGroup {
    BatteryView, LogoView
}

enum class XmlViewGroup {
    ProgressIconButtonView
}

class ViewGroupAdapter(
    private val onItemClick: (XmlViewGroup) -> Unit
) : ListAdapter<XmlViewGroup, ViewGroupAdapter.ViewGroupViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: android.view.ViewGroup,
        viewType: Int
    ): ViewGroupViewHolder {
        return ViewGroupViewHolder(
            ItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewGroupViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewGroupViewHolder(
        private val binding: ItemViewBinding,
        private val listener: (XmlViewGroup) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: XmlViewGroup) {
            binding.root.setOnClickListener {
                listener.invoke(item)
            }
            binding.tvTitle.text = item.name
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<XmlViewGroup>() {
        override fun areItemsTheSame(oldItem: XmlViewGroup, newItem: XmlViewGroup): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: XmlViewGroup, newItem: XmlViewGroup): Boolean {
            return oldItem == newItem
        }
    }
}
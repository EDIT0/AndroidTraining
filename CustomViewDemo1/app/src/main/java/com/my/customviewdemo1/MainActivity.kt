package com.my.customviewdemo1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.customviewdemo1.databinding.ActivityMainBinding
import com.my.customviewdemo1.databinding.ItemViewBinding
import com.my.customviewdemo1.view.compose.BatteryView
import com.my.customviewdemo1.view.compose.LogoView
import com.my.customviewdemo1.view.xml.GaugeView1
import com.my.customviewdemo1.view.xml.PercentageBarView
import com.my.customviewdemo1.view.xml.ProgressBarView1
import com.my.customviewdemo1.view.xml.ProgressIconButtonView
import com.my.customviewdemo1.view.xml.ProgressIconButtonView2
import com.my.customviewdemo1.view.xml.SliderChangeListener
import com.my.customviewdemo1.view.xml.SliderView1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.floor

enum class ComposeViewGroup {
    BatteryView,
    LogoView
}

enum class XmlViewGroup {
    ProgressIconButtonView,
    SliderView1,
    ProgressBarView1,
    GaugeView1,
    ProgressIconButtonView2,
    PercentageBar
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var viewGroupAdapter: ViewGroupAdapter
    private var xmlViewList = listOf(
        XmlViewGroup.ProgressIconButtonView,
        XmlViewGroup.SliderView1,
        XmlViewGroup.ProgressBarView1,
        XmlViewGroup.GaugeView1,
        XmlViewGroup.ProgressIconButtonView2,
        XmlViewGroup.PercentageBar
    )

    private var progressBarView1Job1: Job? = null
    private var progressIconButtonViewJob1: Job? = null
    private var gaugeView1Job1: Job? = null
    private var progressIconButtonView2Job1: Job? = null
    private var scope = CoroutineScope(Dispatchers.Main)

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
                progressBarView1Job1?.cancel()
                progressIconButtonViewJob1?.cancel()
                gaugeView1Job1?.cancel()
                progressIconButtonView2Job1?.cancel()

                when(it) {
                    XmlViewGroup.ProgressIconButtonView -> {
                        binding.layoutInflateView.removeAllViews()
                        val inflater = LayoutInflater.from(binding.layoutInflateView.context)
                        val newLayout = inflater.inflate(R.layout.progress_icon_button_view, binding.layoutInflateView, false)
                        binding.layoutInflateView.addView(newLayout)

                        controlProgressIconButtonView()
                    }
                    XmlViewGroup.SliderView1 -> {
                        binding.layoutInflateView.removeAllViews()
                        val inflater = LayoutInflater.from(binding.layoutInflateView.context)
                        val newLayout = inflater.inflate(R.layout.slider_view_1, binding.layoutInflateView, false)
                        binding.layoutInflateView.addView(newLayout)

                        controlSliderView1()
                    }
                    XmlViewGroup.ProgressBarView1 -> {
                        binding.layoutInflateView.removeAllViews()
                        val inflater = LayoutInflater.from(binding.layoutInflateView.context)
                        val newLayout = inflater.inflate(R.layout.progress_bar_view_1, binding.layoutInflateView, false)
                        binding.layoutInflateView.addView(newLayout)

                        controlProgressBarView1()
                    }
                    XmlViewGroup.GaugeView1 -> {
                        binding.layoutInflateView.removeAllViews()
                        val inflater = LayoutInflater.from(binding.layoutInflateView.context)
                        val newLayout = inflater.inflate(R.layout.gauge_view_1, binding.layoutInflateView, false)
                        binding.layoutInflateView.addView(newLayout)

                        controlGaugeView1()
                    }
                    XmlViewGroup.ProgressIconButtonView2 -> {
                        binding.layoutInflateView.removeAllViews()
                        val inflater = LayoutInflater.from(binding.layoutInflateView.context)
                        val newLayout = inflater.inflate(R.layout.progress_icon_button_view_2, binding.layoutInflateView, false)
                        binding.layoutInflateView.addView(newLayout)

                        controlProgressIconButtonView2()
                    }
                    XmlViewGroup.PercentageBar -> {
                        binding.layoutInflateView.removeAllViews()
                        val inflater = LayoutInflater.from(binding.layoutInflateView.context)
                        val newLayout = inflater.inflate(R.layout.percentage_bar_view, binding.layoutInflateView, false)
                        binding.layoutInflateView.addView(newLayout)

                        controlPercentageBarView()
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
        val btnBack = binding.layoutInflateView.findViewById<Button>(R.id.btnBack)

        // 직접 percentage 설정
        progressIconButtonView.apply {
            setIcon(R.drawable.ic_rounded_lock_24, R.drawable.ic_rounded_lock_white_24)
            setIconSize(48f)
            setIconRectColor(R.color.teal_500)
            setProgressColor(proBgRectColor = R.color.pink_300, proRectColor = R.color.cyan_500)
            setOnClickListener {
                if(getPercentage().toInt() != 0) {
                    return@setOnClickListener
                }
                progressIconButtonViewJob1 = scope.launch {
                    for(i in 0 until 101) {
                        delay(50L)
                        setPercentage(i)
                    }
                }
            }
        }

        // 일정시간 동안 애니메이션
//        progressIconButtonView.apply {
//            setIcon(R.drawable.ic_rounded_lock_open_24, R.drawable.ic_rounded_lock_open_white_24)
//            setIconSize(36f)
//            setIconRectColor(R.color.teal_500)
//            setProgressColor(proBgRectColor = R.color.pink_300, proRectColor = R.color.cyan_500)
//            setOnClickListener {
//                if(getPercentage().toInt() != 0) {
//                    return@setOnClickListener
//                }
//                setAutoPercentage()
//            }
//        }

        btnBack.setOnClickListener {
            progressIconButtonView.setInitState()
        }
    }

    private fun controlSliderView1() {
        val sliderView1 = binding.layoutInflateView.findViewById<SliderView1>(R.id.sliderView1)
        val tvCurrentValue = binding.layoutInflateView.findViewById<TextView>(R.id.tvCurrentValue)
        val tvFrom = binding.layoutInflateView.findViewById<TextView>(R.id.tvFrom)
        val tvTo = binding.layoutInflateView.findViewById<TextView>(R.id.tvTo)

        sliderView1.setRange(from = 16f, to = 32.5f)
        sliderView1.setLine(color = R.color.amber_100, heightDp = 10, lineCornerDp = 10)
        sliderView1.setControlCircleStroke(color = R.color.blue_300, sizeDp = 2f)
        sliderView1.setControlCircleColor(color = R.color.white)
        sliderView1.setLineChargingColor(isEnable = true, color = R.color.teal_200)
        sliderView1.setControlCircleRadius(dp = 10)

        tvFrom.text = "${16}"
        tvTo.text = "${32.5}"

        sliderView1.sliderChangeListener = object : SliderChangeListener {
            override fun onValueChanged(value: Float) {
                val v = String.format("%.1f", value % 1).toFloat()
                if(v < 0.5f) {
                    tvCurrentValue.text = floor(String.format("%.1f", value).toFloat()).toString()
                } else if(v >= 0.5f) {
                    tvCurrentValue.text = (floor(String.format("%.1f", value).toFloat()) + 0.5f).toString()
                }
            }
        }

        sliderView1.setCurrentValue(30.3f)
    }

    private fun controlProgressBarView1() {
        val progressBarView1 = binding.layoutInflateView.findViewById<ProgressBarView1>(R.id.progressBarView1)

        progressBarView1.apply {
            setLineCorner(3)
            setLineHeight(10)
            setLineColor(lineBackgroundColor = R.color.grey_300, chargingLineBackgroundColor = R.color.grey_800)
        }

        progressBarView1Job1 = scope.launch(Dispatchers.IO) {
            for(i in 1 until 101) {
                delay(30L)
                LogUtil.d_dev("${Thread.currentThread()} i값: ${i}")
                progressBarView1.setChargingValue(i.toFloat())
            }
        }
    }

    private fun controlGaugeView1() {
        val gaugeView1 = binding.layoutInflateView.findViewById<GaugeView1>(R.id.gaugeView1)

        gaugeView1.setCenterBottomText(
            text1 = "90점",
            text2 = "전날 대비",
            text3 = " +23점"
        )

        gaugeView1Job1 = scope.launch {
            gaugeView1.setGaugePercentage(90f)
//            for(i in 0 until 91) {
//                delay(30L)
//                LogUtil.d_dev("${Thread.currentThread()} i값: ${i}")
//                gaugeView1.setGaugePercentage(i.toFloat())
//            }
        }

    }

    private fun controlProgressIconButtonView2() {
        val progressIconButtonView2 = binding.layoutInflateView.findViewById<ProgressIconButtonView2>(R.id.progressIconButtonView2)

        progressIconButtonView2Job1 = scope.launch {
            for(i in 0 until 101) {
                delay(100L)
                progressIconButtonView2.setProgress(i.toFloat())
            }
        }
    }
    
    private fun controlPercentageBarView() {
        val percentageBarView = binding.layoutInflateView.findViewById<PercentageBarView>(R.id.percentageBarView)

        percentageBarView.setPercentage(90f)
        percentageBarView.setBarWidth(20f)
        percentageBarView.setBarColor(R.color.light_blue_400)
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
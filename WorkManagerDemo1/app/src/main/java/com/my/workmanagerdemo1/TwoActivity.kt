package com.my.workmanagerdemo1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.my.workmanagerdemo1.databinding.ActivityTwoBinding
import com.my.workmanagerdemo1.work.NoLimitWork2
import kotlinx.coroutines.launch

class TwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMainActivity.setOnClickListener {
            startActivity(Intent(this@TwoActivity, MainActivity::class.java))
        }

        binding.btnOneActivity.setOnClickListener {
            startActivity(Intent(this@TwoActivity, OneActivity::class.java))
        }

        binding.btnDeleteMain.setOnClickListener {
            WorkManager.getInstance(applicationContext).cancelUniqueWork(NO_LIMIT_WORK_1)
        }

        binding.btnDeleteOne.setOnClickListener {
            WorkManager.getInstance(applicationContext).cancelUniqueWork(ONE_TIME_WORK_1)
        }

        binding.btnDeleteTwo.setOnClickListener {
            WorkManager.getInstance(applicationContext).cancelUniqueWork(NO_LIMIT_WORK_2)
        }

        val workConstraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)  // 배터리가 부족하지 않을 때만 실행
            .setRequiresCharging(false)  // 충전 중이 아닐 때도 실행 가능
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)  // 네트워크 필요 없음
            .build()

        val inputData = Data.Builder()
            .putString("key1", "${javaClass.simpleName}")
            .putInt("key2", 42)
            .build()

        val noLimitWork2 = OneTimeWorkRequest.Builder(NoLimitWork2::class.java)
            .setConstraints(workConstraints)
            .setInputData(inputData)
            .build()

        /**
         * 포그라운드 작업 2 실행
         */
        WorkManager.getInstance(applicationContext).enqueueUniqueWork(NO_LIMIT_WORK_2, ExistingWorkPolicy.REPLACE, noLimitWork2)
        lifecycleScope.launch {
            WorkManager.getInstance(applicationContext).getWorkInfoByIdFlow(noLimitWork2.id)
                .collect { workInfo ->
                    workInfo?.let {
                        val progress = workInfo.progress.getInt("progress", 0)
                        Log.d("MYTAG", "${progress}% ${workInfo}")
                        binding.btnDeleteTwo.setText("${progress}% DeleteTwo")
                        when(workInfo.state) {
                            WorkInfo.State.ENQUEUED -> {
                                // 실행 대기 중 (조건 충족 시 실행됨) (아직 종료 X)
                            }
                            WorkInfo.State.RUNNING -> {
                                // 현재 실행 중 (아직 종료 X)
                            }
                            WorkInfo.State.SUCCEEDED -> {
                                // 정상적으로 완료됨 (종료됨)
                                binding.btnDeleteTwo.setText("100% DeleteTwo")
                            }
                            WorkInfo.State.FAILED -> {
                                // 작업이 실패하여 종료됨 (종료됨)
                                binding.btnDeleteTwo.setText("DeleteTwo")
                            }
                            WorkInfo.State.BLOCKED -> {
                                // 다른 Work가 끝나야 실행될 수 있는 상태 (아직 종료 X)
                            }
                            WorkInfo.State.CANCELLED -> {
                                // 사용자가 취소했거나 시스템이 제거함 (종료됨)
                                binding.btnDeleteTwo.setText("DeleteTwo")
                            }
                        }
                    }
                }
        }
    }
}
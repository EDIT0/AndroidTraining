package com.example.roomdemo1.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.example.roomdemo1.data.model.User
import com.example.roomdemo1.databinding.ActivityMainBinding
import com.example.roomdemo1.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            mainViewModel.add(
                User(binding.etName.text.toString().trim(), binding.etAge.text.toString().trim().toInt(), binding.etMajor.text.toString().trim())
            )
        }

        binding.btnDelete.setOnClickListener {
            mainViewModel.delete(
                binding.etDelete.text.toString().trim().toInt()
            )
        }

        binding.btnUpdate.setOnClickListener {
            mainViewModel.update(
                binding.etId.text.toString().trim().toInt(),
                binding.etNewName.text.toString().trim()
            )
        }

        binding.btnFindName.setOnClickListener {
            mainViewModel.select(binding.etFindName.text.toString().trim())
        }

        mainViewModel.searchList.observe(this as LifecycleOwner) {
            binding.apply {
                txtResult.text = ""
                etFindName.setText("")
            }
            for(i in it) {
                binding.txtResult.append("\n ${i.id}, ${i.name}, ${i.age}, ${i.major}")
            }
        }

        mainViewModel.userList.observe(this as LifecycleOwner) {
            binding.apply {
                etName.setText("")
                etAge.setText("")
                etMajor.setText("")
                etDelete.setText("")
                txtResult.text = ""
                etId.setText("")
                etNewName.setText("")
            }
            for(i in it) {
                binding.txtResult.append("\n ${i.id}, ${i.name}, ${i.age}, ${i.major}")
            }
        }

        mainViewModel.response.observe(this as LifecycleOwner) {
            when(it) {
                MainViewModel.ResponseSet.SUCCESS -> {
                    Toast.makeText(this, "작업 성공", Toast.LENGTH_SHORT).show()
                }
                MainViewModel.ResponseSet.ERROR -> {
                    Toast.makeText(this, "작업 실패", Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }
    }
}
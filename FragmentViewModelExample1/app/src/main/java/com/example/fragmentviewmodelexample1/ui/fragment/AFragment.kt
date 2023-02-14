package com.example.fragmentviewmodelexample1.ui.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentviewmodelexample1.R
import com.example.fragmentviewmodelexample1.databinding.FragmentABinding
import com.example.fragmentviewmodelexample1.ui.adapter.ItemAdapter
import com.example.fragmentviewmodelexample1.ui.viewmodel.AViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class AFragment : Fragment() {

    private val TAG = AFragment::class.java.simpleName

    private var _binding: FragmentABinding? = null
    private val binding get() = _binding!!


    var aViewModel: AViewModel = AViewModel(Application())

    private lateinit var itemAdapter: ItemAdapter

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.i(TAG, "전달받은 데이터 ${param1} / ${param2}")
        aViewModel.getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a, container, false)
        _binding?.avm = aViewModel
        _binding?.lifecycleOwner = this
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated() ${this.hashCode()} ${aViewModel.hashCode()}")

        itemAdapter = ItemAdapter()

        binding.rvData.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvData.adapter = itemAdapter
        itemAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        aViewModel.itemList.observe(viewLifecycleOwner) {
            Log.i(TAG, "${aViewModel.itemList.hashCode()} 리스트 적용 / 갯수: ${it.size}")
            itemAdapter.submitList(it.toMutableList())
        }

        binding.btnNext.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
                .replace(R.id.fragment, AFragment.newInstance("", ""))
                .addToBackStack(null)
                .commit()
        }

        binding.btnGetData.setOnClickListener {
            aViewModel.getData()
        }

        binding.btnCheckData.setOnClickListener {
            Log.i(TAG, "Check ItemCount: ${aViewModel.itemList.value?.size}")
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView() ${this.hashCode()} ${aViewModel.hashCode()}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach() ${this.hashCode()} ${aViewModel.hashCode()}")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
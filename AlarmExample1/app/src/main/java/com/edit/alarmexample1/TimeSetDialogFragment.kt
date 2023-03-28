package com.edit.alarmexample1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.edit.alarmexample1.databinding.FragmentTimeSetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimeSetDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimeSetDialogFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTimeSetDialogBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTimeSetDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            (requireActivity() as MainActivity).apply {
                mainViewModel.apply {
                    binding.apply {
                        if(etYear.text.toString().isEmpty() ||
                            etMonth.text.toString().isEmpty() ||
                            etDay.text.toString().isEmpty() ||
                            etHour.text.toString().isEmpty() ||
                            etMinute.text.toString().isEmpty() ||
                            etSeconds.text.toString().isEmpty()) {
                            Toast.makeText(requireContext(), "날짜, 시간을 적어주세요.", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }


                    year = binding.etYear.text.toString().toInt()
                    month = binding.etMonth.text.toString().toInt()
                    day = binding.etDay.text.toString().toInt()

                    hour = binding.etHour.text.toString().toInt()
                    minute = binding.etMinute.text.toString().toInt()
                    seconds = binding.etSeconds.text.toString().toInt()


                    mainViewModel.saveAlarm()
                }

            }
            (requireActivity() as MainActivity).apply {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimeSetDialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimeSetDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
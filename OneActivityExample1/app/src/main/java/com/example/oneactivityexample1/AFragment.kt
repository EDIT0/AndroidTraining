package com.example.oneactivityexample1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AFragment().apply {
                Log.i("MYTAG", "A newInstance")
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("MYTAG", "AFragment onAttach")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("MYTAG", "AFragment onCreateView")
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("MYTAG", "AFragment onViewCreated")

        Log.i("MYTAG", "AFragment ${view.hashCode()} ${this.hashCode()}")
        Log.i("MYTAG", "AFragment ${param1} / ${param2}")

        param1 = "A에서 파라미터 변경하기1"
        param2 = "A에서 파라미터 변경하기2"

        view.findViewById<AppCompatButton>(R.id.button).setOnClickListener {
            val transaction = (activity as MainActivity).supportFragmentManager.beginTransaction()
            transaction.apply {
//                add(R.id.fragment, (activity as MainActivity).bFragment!!)
//                setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
                add(R.id.fragment, BFragment.newInstance("프래그먼트A-1", "프래그먼트A-2"))
                addToBackStack(null)
                commit()
            }
//            if((activity as MainActivity).bFragment == null) {
//                Log.i("MYTAG", "Create BFragment")
//                (activity as MainActivity).bFragment = BFragment()
//                (activity as MainActivity).fm.beginTransaction()
//                    .replace(R.id.fragment, (activity as MainActivity).bFragment!!)
//                    .addToBackStack(null)
//                    .commit()
//            }
//            if((activity as MainActivity).aFragment != null) {
//                Log.i("MYTAG", "Hide AFragment")
//                (activity as MainActivity).fm.beginTransaction().hide((activity as MainActivity).aFragment!!).commit()
//            }
//            if((activity as MainActivity).bFragment != null) {
//                Log.i("MYTAG", "Show BFragment")
//                (activity as MainActivity).fm.beginTransaction().show((activity as MainActivity).bFragment!!).commit()
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("MYTAG", "AFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MYTAG", "AFragment onDestroy ${this.hashCode()}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("MYTAG", "AFragment onDetach")
    }
}
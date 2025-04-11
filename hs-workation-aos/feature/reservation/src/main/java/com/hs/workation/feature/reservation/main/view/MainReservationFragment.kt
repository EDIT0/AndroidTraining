package com.hs.workation.feature.reservation.main.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.reservation.R
import com.hs.workation.feature.reservation.databinding.FragmentMainReservationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainReservationFragment : BaseDataBindingFragment<FragmentMainReservationBinding>(R.layout.fragment_main_reservation) {

    private val mainReservationVM: MainReservationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        LogUtil.d_dev("MYTAG ${javaClass.simpleName} onResume()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d_dev("MYTAG ${javaClass.simpleName} onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.d_dev("MYTAG ${javaClass.simpleName} onDestroyView()")
    }
}
package com.edit.alarmexample1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edit.alarmexample1.databinding.AlarmListItemBinding

class AlarmListAdapter() : ListAdapter<AlarmModel, AlarmListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val _binding = SelectedImageItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
//        val viewHolder = ViewHolder(_binding)
        return ViewHolder(AlarmListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: AlarmListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(alarmModel: AlarmModel) {

            binding.tvId.text = "알람 고유 번호: " + alarmModel.id.toString()
            binding.tvFinishDate.text = "${Alarm.convertDateDayString(alarmModel.finishDate)}"
            binding.tvTime.text = alarmModel.time.toString()
            binding.tbSwitch.setChecked(alarmModel.isSwitch)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, alarmModel)
                }
            }

            binding.tbSwitch.setOnClickListener {
                binding.root.performClick()
            }
        }
    }

    private var onItemClickListener : ((Int, AlarmModel) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Int, AlarmModel) -> Unit) {
        onItemClickListener = listener
    }

    /**
     * DiffUtil작동 안함
     * */
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<AlarmModel>() {
            override fun areContentsTheSame(oldItem: AlarmModel, newItem: AlarmModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: AlarmModel, newItem: AlarmModel) =
                oldItem.id == newItem.id
        }
    }

}
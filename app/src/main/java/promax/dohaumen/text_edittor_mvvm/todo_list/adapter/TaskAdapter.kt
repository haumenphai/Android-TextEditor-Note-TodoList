package promax.dohaumen.text_edittor_mvvm.todo_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import promax.dohaumen.text_edittor_mvvm.MyApplication
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ItemTaskBinding
import promax.dohaumen.text_edittor_mvvm.helper.getTuVietTat
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.hmp.dev.heler.StringHelper
import java.util.*
import kotlin.collections.ArrayList

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.Holder>() {
    private var listTask: List<Task> = ArrayList()

    fun setList(list: List<Task>) {
        listTask = list
        notifyDataSetChanged()
    }

    fun getList() = listTask




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding  = ItemTaskBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.task = listTask[position]
        holder.binding.stt = "${position+1}"
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = listTask.size


    inner class Holder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.background.setOnClickListener {
                Toast.makeText(MyApplication.context, listTask[adapterPosition].name, Toast.LENGTH_SHORT).show()
            }
            binding.checkboxComplete.setOnClickListener {
                // todo: chỉnh sửa phần này!
                listTask[adapterPosition].isCompleted = (it as CheckBox).isChecked
                Toast.makeText(MyApplication.context, "hoàn thành nhiệm vụ:\n ${listTask[adapterPosition].name}", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }
        }
    }
}
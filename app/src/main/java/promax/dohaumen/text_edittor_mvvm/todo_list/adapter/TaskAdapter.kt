package promax.dohaumen.text_edittor_mvvm.todo_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import promax.dohaumen.text_edittor_mvvm.MyApplication
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ItemTaskBinding
import promax.dohaumen.text_edittor_mvvm.helper.getCurentDate24h
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskDatabase
import promax.hmp.dev.utils.TimeDelayUlti
import kotlin.collections.ArrayList
open class TaskAdapter: RecyclerView.Adapter<TaskAdapter.Holder>() {
    private var listTask: List<Task> = ArrayList()

    fun setList(list: List<Task>) {
        listTask = list
        notifyDataSetChanged()
    }

    fun getList() = listTask
    var isShowNumber = false




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding  = ItemTaskBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private val animation = AnimationUtils.loadAnimation(MyApplication.context, R.anim.anim_item_task)
    var playAnimation = false

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val task = listTask[position]

        holder.binding.task = task
        holder.binding.stt = "${position+1}"
        holder.binding.isShowNummber = isShowNumber
        holder.binding.executePendingBindings()



        if (playAnimation && task.isChecked) {
            holder.binding.background.startAnimation(animation)
            TimeDelayUlti.setTime(500).runAfterMilisecond {
                playAnimation = false
                CoroutineScope(Dispatchers.IO).launch {
                    listTask.forEach {
                        TaskDatabase.get.dao().update(it)
                    }
                }

            }
        }


    }

    override fun getItemCount(): Int = listTask.size


    lateinit var onClickItem:(task: Task) -> Unit

    inner class Holder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.background.setOnClickListener {
                onClickItem(listTask[adapterPosition])
            }
            binding.checkboxComplete.setOnClickListener {
                val task = listTask[adapterPosition]
                task.isChecked = (it as CheckBox).isChecked
                if (task.isChecked) {
                    task.dateCompleted = getCurentDate24h()
                } else {
                    task.dateCompleted = ""
                }

                notifyDataSetChanged()
            }
        }
    }
}
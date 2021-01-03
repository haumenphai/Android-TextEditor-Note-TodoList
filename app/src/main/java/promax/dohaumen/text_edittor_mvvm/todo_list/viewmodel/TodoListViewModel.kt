package promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import promax.dohaumen.text_edittor_mvvm.todo_list.adapter.TaskAdapter
import promax.dohaumen.text_edittor_mvvm.todo_list.data.DataTodoListFragment
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskDatabase
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskResposity

class TodoListViewModel: ViewModel() {
    val tasks: LiveData<List<Task>> by lazy {
        TaskDatabase.get.dao().getLiveData(false)
    }

    val allTasks: LiveData<List<Task>> by lazy {
        TaskDatabase.get.dao().getLiveData()
    }

    var listTask: MutableList<Task> = mutableListOf()


    val isShowNumber: MutableLiveData<Boolean> by lazy {
        val isShow = DataTodoListFragment.isShowNumber()
        MutableLiveData(isShow)
    }

    fun saveData() {
        DataTodoListFragment.setShowNumber(isShowNumber.value!!)
    }

    private val viewTypeTask: MutableLiveData<Int> = MutableLiveData(VIEW_TASK)
    fun setViewTypeTask(type: Int) {
        viewTypeTask.value = type
    }
    fun cancelAction(adapter: TaskAdapter) {
        adapter.getList().forEach {
            it.isSelected = false
        }
        adapter.setSelectMode(false)
        when(viewTypeTask.value) {
            VIEW_TASK -> {
                adapter.checkBoxEnable = true
            }
            VIEW_TASK_COMPLETED -> {
                adapter.checkBoxEnable = false
            }
            VIEW_ALL_TASK -> {
                adapter.checkBoxEnable = false
            }
        }
        adapter.notifyDataSetChanged()
    }



    companion object {
        val VIEW_ALL_TASK = 1
        val VIEW_TASK = 2
        val VIEW_TASK_COMPLETED = 3
    }
}
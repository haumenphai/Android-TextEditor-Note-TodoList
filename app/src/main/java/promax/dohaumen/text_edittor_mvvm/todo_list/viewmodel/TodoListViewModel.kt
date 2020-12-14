package promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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







}
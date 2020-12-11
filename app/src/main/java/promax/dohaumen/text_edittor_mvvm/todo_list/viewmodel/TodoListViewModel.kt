package promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskDatabase

class TodoListViewModel: ViewModel() {
    val tasks: LiveData<List<Task>> = TaskDatabase.get.dao().getLiveData(false)

}
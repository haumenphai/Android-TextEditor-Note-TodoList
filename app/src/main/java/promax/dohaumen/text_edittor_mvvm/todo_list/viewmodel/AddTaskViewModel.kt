package promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.helper.getCurentDate24h
import promax.dohaumen.text_edittor_mvvm.helper.getString
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskDatabase

class AddTaskViewModel: ViewModel() {
    val listTask: LiveData<List<Task>> by lazy {
        TaskDatabase.get.dao().getLiveData()
    }

    fun addTask(name: String, describe: String, onFailure:(mess: String) -> Unit, onSuccess:(mess: String) -> Unit) {
        if (name.trim() == "") {
            onFailure(getString(R.string.file_name_must_not_empty))
        } else {
            val task = Task(name, describe)
            task.dateCreated = getCurentDate24h()
            TaskDatabase.get.dao().insert(task)
            onSuccess(getString(R.string.add_task_successfully))
        }
    }

    fun editTask(task: Task , name: String, describe: String, onFailure:(mess: String) -> Unit, onSuccess:(mess: String) -> Unit) {
        if (name.trim() == "") {
            onFailure(getString(R.string.file_name_must_not_empty))
        } else {
            task.name = name
            task.describe = describe
            TaskDatabase.get.dao().update(task)
            onSuccess(getString(R.string.edit_task_successfully))
        }
    }
}
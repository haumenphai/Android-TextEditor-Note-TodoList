package promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel

import android.os.AsyncTask
import android.util.Log
import android.widget.Filter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import promax.dohaumen.text_edittor_mvvm.helper.getTuVietTat
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskDatabase
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskResposity
import promax.hmp.dev.heler.StringHelper
import java.util.*

class TodoListViewModel: ViewModel() {
    lateinit var tasks: LiveData<List<Task>>
    init {
        tasks = TaskDatabase.get.dao().getLiveData()
    }



}
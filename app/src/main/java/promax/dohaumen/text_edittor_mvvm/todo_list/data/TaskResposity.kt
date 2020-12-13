package promax.dohaumen.text_edittor_mvvm.todo_list.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


object TaskResposity {
    private val dao = TaskDatabase.get.dao()

    suspend fun getList(): List<Task> {
        return GlobalScope.async(Dispatchers.IO) {
            dao.getList()
        }.await()
    }

    suspend fun getListCompleted(): List<Task> {
        return GlobalScope.async(Dispatchers.IO) {
            dao.getList(true)
        }.await()
    }

    suspend fun getLiveData(): LiveData<List<Task>> {
        return GlobalScope.async(Dispatchers.IO) {
            dao.getLiveData()
        }.await()
    }

    suspend fun getLiveDataListCompleted(): LiveData<List<Task>> {
        return GlobalScope.async(Dispatchers.IO) {
            dao.getLiveData(true)
        }.await()
    }

    suspend fun update(vararg task: Task) {
        GlobalScope.async(Dispatchers.IO) {
            dao.update(*task)
        }.await()
    }

    suspend fun insert(vararg task: Task) {
        GlobalScope.async(Dispatchers.IO) {
            dao.insert(*task)
        }.await()
    }

    suspend fun delete(vararg task: Task) {
        GlobalScope.async(Dispatchers.IO) {
            dao.delete(*task)
        }.await()
    }

    suspend fun deleteAll(vararg task: Task) {
        GlobalScope.async(Dispatchers.IO) {
            dao.delete(*task)
        }.await()
    }



    suspend fun updateList(list: List<Task>) {
        GlobalScope.async(Dispatchers.IO) {
            list.forEach {
                dao.update(it)
            }
        }.await()
    }
}
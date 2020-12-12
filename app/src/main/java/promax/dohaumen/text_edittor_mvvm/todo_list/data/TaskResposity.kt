package promax.dohaumen.text_edittor_mvvm.todo_list.data

object TaskResposity {
    private val dao = TaskDatabase.get.dao()

    suspend fun getList(isCompleted: Boolean) = dao.getList(isCompleted)
    suspend fun getLiveData(isCompleted: Boolean) = dao.getLiveData(isCompleted)
    suspend fun deleteAll() = dao.deleteAll()
    suspend fun delete(vararg task: Task) = dao.delete(*task)
    suspend fun insert(vararg task: Task) = dao.insert(*task)
    suspend fun update(vararg task: Task) = dao.update(*task)
}
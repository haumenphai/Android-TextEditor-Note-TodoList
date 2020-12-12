package promax.dohaumen.text_edittor_mvvm.todo_list.data

import androidx.lifecycle.LiveData
import androidx.room.*
import promax.dohaumen.text_edittor_mvvm.MyApplication.Companion.context

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun dao() : TaskDao

    companion object {
        val get: TaskDatabase by lazy {
            Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}

@Dao
interface TaskDao {
    @Insert
    fun insert(vararg task: Task)

    @Update
    fun update(vararg task: Task)

    @Delete
    fun delete(vararg task: Task)

    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("SELECT * FROM task where isCompleted = :isComplete")
    fun getList(isComplete: Boolean): List<Task>

    @Query("SELECT * FROM task where isCompleted = :isComplete")
    fun getLiveData(isComplete: Boolean): LiveData<List<Task>>

    @Query("SELECT * FROM task where isDeleted = :isDelete")
    fun getListDeleted(isDelete: Boolean): List<Task>
}
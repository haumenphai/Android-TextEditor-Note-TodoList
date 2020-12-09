package promax.dohaumen.text_edittor_mvvm.data.task

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import promax.dohaumen.text_edittor_mvvm.MyApplication.Companion.context
import promax.dohaumen.text_edittor_mvvm.models.Task

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
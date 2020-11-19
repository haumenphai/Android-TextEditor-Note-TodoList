package promax.dohaumen.text_edittor_mvvm.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import promax.dohaumen.text_edittor_mvvm.MyApplication
import promax.dohaumen.text_edittor_mvvm.models.FileText

@Database(entities = arrayOf(FileText::class), version = 1, exportSchema = false)
abstract class FileTextDataBase: RoomDatabase() {
    abstract fun dao(): FileTextDao
    companion object {
        val db = Room.databaseBuilder(
            MyApplication.context,
            FileTextDataBase::class.java, "database-name"
        ).build()
    }
}
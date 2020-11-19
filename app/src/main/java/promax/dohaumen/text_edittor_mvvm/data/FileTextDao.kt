package promax.dohaumen.text_edittor_mvvm.data

import androidx.lifecycle.LiveData
import androidx.room.*
import promax.dohaumen.text_edittor_mvvm.models.FileText

@Dao
interface FileTextDao {
    @Query("SELECT * FROM filetext")
    fun getListText(): MutableList<FileText>

    @Query("SELECT * FROM filetext")
    fun getLiveData(): LiveData<MutableList<FileText>>

    @Update
    fun update(fileText: FileText)

    @Delete
    fun detele(fileText: FileText)

    @Insert
    fun insert(vararg fileText: FileText)

    @Query("DELETE FROM filetext")
    fun deleteAll()

}
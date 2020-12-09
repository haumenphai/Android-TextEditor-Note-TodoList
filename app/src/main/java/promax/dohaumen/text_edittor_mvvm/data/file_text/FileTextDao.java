package promax.dohaumen.text_edittor_mvvm.data.file_text;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import promax.dohaumen.text_edittor_mvvm.models.FileText;

/**
 * 19/11/2020
 * Room trên kotlin không dùng được, cuối cùng phải chuyển lên Java lại ngon
 */
@Dao
public interface FileTextDao {
    @Insert
    void insert(FileText... fileText);

    @Update
    void update(FileText... fileText);

    @Delete
    void delete(FileText... fileText);

    @Query("DELETE FROM filetext")
    void deleteAll();

    @Query("SELECT * FROM filetext WHERE isDeleted = :getListDelete")
    List<FileText> getList(boolean getListDelete);

    @Query("SELECT * FROM filetext WHERE isDeleted = :getListDelete")
    LiveData<List<FileText>> getLiveData(boolean getListDelete);

}

package promax.dohaumen.text_edittor_mvvm.data;


import android.os.AsyncTask;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

import promax.dohaumen.text_edittor_mvvm.MyApplication;
import promax.dohaumen.text_edittor_mvvm.models.FileText;

@Database(entities = {FileText.class}, version = 5)
public abstract class FileTextDatabase extends RoomDatabase {
    public abstract FileTextDao dao();

    private static FileTextDatabase INSTANCE;

    public static FileTextDatabase getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(MyApplication.Companion.getContext(), FileTextDatabase.class, "database_text")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(romCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback romCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new FileTextDatabase.PopulateDatabaseAsyncTask(getINSTANCE()).execute();
        }
    };
    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private FileTextDao dao;
        private PopulateDatabaseAsyncTask(FileTextDatabase db){
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insert(new FileText("Hello", "123","2/2/2020"));
            dao.insert(new FileText("Ghi chu", "123","2/2/2020"));
            dao.insert(new FileText("dohaumen", "123","2/2/2020"));
            dao.insert(new FileText("hey", "123","2/2/2020"));
            return null;
        }

    }


}



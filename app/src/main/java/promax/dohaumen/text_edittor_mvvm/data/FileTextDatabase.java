package promax.dohaumen.text_edittor_mvvm.data;


import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import kotlin.jvm.Volatile;
import promax.dohaumen.text_edittor_mvvm.MyApplication;
import promax.dohaumen.text_edittor_mvvm.models.FileText;

@Database(entities = {FileText.class}, version = 6)
public abstract class FileTextDatabase extends RoomDatabase {
    public abstract FileTextDao dao();

    private volatile static FileTextDatabase INSTANCE;

    public synchronized static FileTextDatabase getINSTANCE() {
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
            dao.insert(new FileText("Text editor note", "welcome","default"));

            return null;
        }

    }


}



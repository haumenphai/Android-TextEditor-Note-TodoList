package promax.dohaumen.text_edittor_mvvm

import android.app.Application
import android.content.Context
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
            private set
    }
}
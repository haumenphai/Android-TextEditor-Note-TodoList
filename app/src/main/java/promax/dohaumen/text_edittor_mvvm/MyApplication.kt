package promax.dohaumen.text_edittor_mvvm

import android.app.Application
import android.content.Context
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase

class MyApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
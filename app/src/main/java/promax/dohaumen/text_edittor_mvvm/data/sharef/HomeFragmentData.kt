package promax.dohaumen.text_edittor_mvvm.data.sharef

import android.content.Context
import android.content.SharedPreferences
import promax.dohaumen.text_edittor_mvvm.MyApplication

object HomeFragmentData {
    private val sharedPreferences: SharedPreferences =
        MyApplication.context.getSharedPreferences("home_fragment", Context.MODE_PRIVATE)
    private val edit: SharedPreferences.Editor = sharedPreferences.edit()

    fun getTextTemp() = sharedPreferences.getString("text_temp", "")
    fun setTextTemp(text: String) {
        edit.putString("text_temp", text)
        edit.apply()
    }
}
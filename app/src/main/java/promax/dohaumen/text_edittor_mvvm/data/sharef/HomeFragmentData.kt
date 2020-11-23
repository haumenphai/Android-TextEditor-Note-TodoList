package promax.dohaumen.text_edittor_mvvm.data.sharef

import android.content.Context
import android.content.SharedPreferences
import promax.dohaumen.text_edittor_mvvm.MyApplication

object HomeFragmentData {
    private val sharedPreferences: SharedPreferences =
        MyApplication.context.getSharedPreferences("home_fragment", Context.MODE_PRIVATE)
    private val edit: SharedPreferences.Editor = sharedPreferences.edit()

    private const val textDefault = ""
    private const val textSize = 16 // todo: change to 18sp

    fun getTextTemp() = sharedPreferences.getString("text_temp", textDefault)
    fun setTextTemp(text: String) {
        edit.putString("text_temp", text)
        edit.apply()
    }

    fun getTextSize() = sharedPreferences.getInt("text_size", textSize)
    fun setTextSize(textSize: Int) {
        edit.putInt("text_size", textSize)
        edit.apply()
    }




}
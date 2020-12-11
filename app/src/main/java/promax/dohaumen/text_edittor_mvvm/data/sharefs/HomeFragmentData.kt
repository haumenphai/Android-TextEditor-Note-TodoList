package promax.dohaumen.text_edittor_mvvm.data.sharefs

import android.content.Context
import android.content.SharedPreferences
import promax.dohaumen.text_edittor_mvvm.MyApplication

object HomeFragmentData {
    private val sharedPreferences: SharedPreferences =
        MyApplication.context.getSharedPreferences("home_fragment", Context.MODE_PRIVATE)
    private val edit: SharedPreferences.Editor = sharedPreferences.edit()

    private const val textDefault = ""
    private const val textSize = 16
    private const val isShowLineNumber = true
    private const val automaticCapitalization = true // tự động viết hoa đầu dòng

    fun isAutomaticCapitalization() = sharedPreferences.getBoolean("tu_dong_viet_hoa", automaticCapitalization)
    fun setAutomaticCapitalization(auto: Boolean) {
        edit.putBoolean("tu_dong_viet_hoa", auto)
    }

    fun isShowLineNumber() = sharedPreferences.getBoolean("is_show_line_number", isShowLineNumber)
    fun setShowLineNumber(isShowLineNummber: Boolean) {
        edit.putBoolean("is_show_line_number", isShowLineNummber)
        edit.apply()
    }

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
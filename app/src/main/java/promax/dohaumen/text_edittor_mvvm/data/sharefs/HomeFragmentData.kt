package promax.dohaumen.text_edittor_mvvm.data.sharefs

import android.content.Context
import android.content.SharedPreferences
import promax.dohaumen.text_edittor_mvvm.MyApplication

object HomeFragmentData {
    private val sharefs: SharedPreferences =
        MyApplication.context.getSharedPreferences("home_fragment", Context.MODE_PRIVATE)

    private const val textDefault = ""
    private const val textSize = 16
    private const val isShowLineNumber = true
    private const val automaticCapitalization = true // tự động viết hoa đầu dòng

    fun isAutomaticCapitalization() = sharefs.getBoolean("tu_dong_viet_hoa", automaticCapitalization)
    fun setAutomaticCapitalization(auto: Boolean) = sharefs.edit().putBoolean("tu_dong_viet_hoa", auto).apply()

    fun isShowLineNumber() = sharefs.getBoolean("is_show_line_number", isShowLineNumber)
    fun setShowLineNumber(isShowLineNummber: Boolean) = sharefs.edit().putBoolean("is_show_line_number", isShowLineNummber).apply()

    fun getTextTemp() = sharefs.getString("text_temp", textDefault)
    fun setTextTemp(text: String) = sharefs.edit().putString("text_temp", text).apply()

    fun getTextSize() = sharefs.getInt("text_size", textSize)
    fun setTextSize(textSize: Int) = sharefs.edit().putInt("text_size", textSize).apply()



    fun clearAllValue() = sharefs.edit().clear().apply()

}
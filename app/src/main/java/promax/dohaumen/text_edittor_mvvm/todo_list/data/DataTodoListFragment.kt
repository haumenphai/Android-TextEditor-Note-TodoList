package promax.dohaumen.text_edittor_mvvm.todo_list.data

import android.content.Context
import android.content.SharedPreferences
import promax.dohaumen.text_edittor_mvvm.MyApplication

object DataTodoListFragment {
    private const val sharefsName = "data_todo_list_fragment"
    private const val isShowNumber = false

    private val sharedPreferences: SharedPreferences by lazy {
        MyApplication.context.getSharedPreferences(sharefsName, Context.MODE_PRIVATE)
    }

    fun isShowNumber() = sharedPreferences.getBoolean("show_nummber", isShowNumber)
    fun setShowNumber(isShow: Boolean) {
        sharedPreferences.edit().putBoolean("show_nummber", isShow).apply()
    }


}
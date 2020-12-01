package promax.dohaumen.text_edittor_mvvm.views.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.data.sharef.HomeFragmentData
import promax.dohaumen.text_edittor_mvvm.databinding.DialogSettingHomeFragmentBinding

class DialogSettingEditView(val context: Context) {
    val dialog: Dialog
    val b: DialogSettingHomeFragmentBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_setting_home_fragment, null)
        b = DialogSettingHomeFragmentBinding.bind(view)
        dialog = Dialog(context)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)
    }

    fun show() = dialog.show()
    fun cancel() = dialog.cancel()

    companion object {
        private var isShowLineNumber: MutableLiveData<Boolean> = MutableLiveData()
        fun isShowLineNumber(): LiveData<Boolean> {
            isShowLineNumber.value = HomeFragmentData.isShowLineNumber()
            return isShowLineNumber
        }
        fun saveIsShowLineNummber(isShow: Boolean) {
            isShowLineNumber.value = isShow
            HomeFragmentData.setShowLineNumber(isShow)

        }

    }
}

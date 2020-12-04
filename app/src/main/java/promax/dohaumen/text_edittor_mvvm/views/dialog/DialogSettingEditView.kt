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
        dialog.setCanceledOnTouchOutside(false)
    }

    fun show() = dialog.show()
    fun cancel() = dialog.cancel()

    companion object {
        private val isShowLineNumber: MutableLiveData<Boolean> by lazy {
            val live: MutableLiveData<Boolean> = MutableLiveData()
            live.value = HomeFragmentData.isAutomaticCapitalization()
            live
        }
        fun isShowLineNumber(): LiveData<Boolean>  = isShowLineNumber
        fun saveIsShowLineNummber(isShow: Boolean) {
            isShowLineNumber.value = isShow
            HomeFragmentData.setShowLineNumber(isShow)
        }


        private val isAutoCap: MutableLiveData<Boolean> by lazy {
            val live: MutableLiveData<Boolean> = MutableLiveData()
            live.value = HomeFragmentData.isAutomaticCapitalization()
            live
        }
        fun isAutoCap(): LiveData<Boolean> = isAutoCap
        fun saveAutoCap(isAuto: Boolean) {
            isAutoCap.value = isAuto
            HomeFragmentData.setAutomaticCapitalization(isAuto)
        }

    }














}

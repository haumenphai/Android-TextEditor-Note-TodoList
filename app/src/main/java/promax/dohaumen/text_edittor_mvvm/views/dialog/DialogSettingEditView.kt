package promax.dohaumen.text_edittor_mvvm.views.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import promax.dohaumen.text_edittor_mvvm.R
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

}
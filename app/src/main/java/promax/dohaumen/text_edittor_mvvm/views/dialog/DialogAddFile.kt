package promax.dohaumen.text_edittor_mvvm.views.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.DialogAddNewFileBinding

class DialogAddFile(context: Context) {

    var b: DialogAddNewFileBinding
    var dialog: Dialog

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_new_file, null)
        b = DialogAddNewFileBinding.bind(view)

        dialog = Dialog(context)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
    }

    fun show() {
        dialog.show()
    }

    fun cancel() {
        dialog.cancel()
    }


}
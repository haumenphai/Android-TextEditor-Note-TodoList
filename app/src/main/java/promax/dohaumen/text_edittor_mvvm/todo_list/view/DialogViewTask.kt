package promax.dohaumen.text_edittor_mvvm.todo_list.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.DialogViewTaskBinding
import promax.dohaumen.text_edittor_mvvm.helper.getString
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task

@SuppressLint("SetTextI18n")
class DialogViewTask(val context: Context, val task: Task) {
    var b: DialogViewTaskBinding
    var dialog: Dialog

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_view_task, null)
        b = DialogViewTaskBinding.bind(view)
        dialog = Dialog(context)
        dialog.setContentView(b.root)

        b.btnOk.setOnClickListener {
            dialog.cancel()
        }

        b.tvTaskName.text       = task.name
        b.tvTaskDescribe.text   = task.describe
        b.tvDateCreated.text    = "${getString(R.string.date_created)}: ${task.dateCreated}"

        if (task.dateCompleted == "") {
            b.tvDateCompleted.visibility = View.GONE
        } else {
            b.tvDateCompleted.text = "${getString(R.string.date_compled)}: ${task.dateCompleted}"
        }

        if (task.dateDeleted == "") {
            b.tvDateDeleted.visibility = View.GONE
        } else {
            b.tvDateCompleted.text = "${getString(R.string.date_delete_2)}: ${task.dateDeleted}"
        }
    }

    fun show() {
        dialog.show()
    }

    fun cancel() {
        dialog.cancel()
    }
}
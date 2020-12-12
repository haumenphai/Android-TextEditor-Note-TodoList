package promax.dohaumen.text_edittor_mvvm.todo_list.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityAddTaskBinding
import promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel.AddTaskViewModel
@SuppressLint("SetTextI18n")
class AddTaskActivity : AppCompatActivity() {
    val b: ActivityAddTaskBinding by lazy { ActivityAddTaskBinding.inflate(layoutInflater) }
    val viewModel: AddTaskViewModel by lazy { ViewModelProvider(this).get(AddTaskViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        setClick()

        viewModel.listTask.observe(this, {
            b.tvTaskCount.setText(getString(R.string.task_count) + it.size)
        })
    }

    fun setClick() {
        b.btnCancel.setOnClickListener {
            finish()
        }
        b.btnOk.setOnClickListener {
            val name = b.editName.text.toString()
            val desc = b.editDescribe.text.toString()

            viewModel.addTask(name, desc, onFailure = { mess ->
                Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
            }, onSuccess = { mess ->
                b.editName.setText("")
                b.editDescribe.setText("")
               Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
            })
        }
    }
}
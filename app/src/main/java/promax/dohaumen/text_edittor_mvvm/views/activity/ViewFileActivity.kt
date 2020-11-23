package promax.dohaumen.text_edittor_mvvm.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityViewFileBinding
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.viewmodel.ViewFileActivityViewModel
import promax.dohaumen.text_edittor_mvvm.views.DialogSettingEditView

class ViewFileActivity : AppCompatActivity() {
    lateinit var b: ActivityViewFileBinding
    lateinit var viewModel: ViewFileActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityViewFileBinding.inflate(layoutInflater)
        setContentView(b.root)

        val fileText: FileText? = intent.getParcelableExtra("fileText")

        b.editViewFile.setText(fileText!!.content)

        viewModel = ViewModelProvider(this).get(ViewFileActivityViewModel::class.java)
        viewModel.fileText = fileText


        viewModel.isEditTextEnable().observeForever { isEditHometEnable ->
            b.editViewFile.isEnabled = isEditHometEnable

            if (isEditHometEnable) {
                menu?.findItem(R.id.menu_edit)?.setIcon(R.drawable.ic_edit)
                Toast.makeText(this, "editing...", Toast.LENGTH_SHORT).show()
            } else {
                menu?.findItem(R.id.menu_edit)?.setIcon(R.drawable.ic_edit_gray)
            }
        }
        viewModel.getTextSize().observeForever {
            b.editViewFile.textSize = it.toFloat()
        }

    }




    var menu: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_view_file_menu, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                viewModel.setItemEditEnableClick()
            }
            R.id.menu_save -> {
                AlertDialog.Builder(this)
                    .setTitle("Save")
                    .setPositiveButton("OK") {s,s1 ->
                        viewModel.saveFileText(b.editViewFile.text.toString())
                        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") {s,s1 ->

                    }.show()
            }
            R.id.menu_setting -> {
                val dialog = DialogSettingEditView(this).apply {
                    viewModel.onTextSizeChangeComple = {
                        b.tvTextSize.setText("$it sp")
                    }

                    b.tvTextSize.setText("${viewModel.getTextSize().value} sp")
                    b.btnOk.setOnClickListener {
                        dialog.cancel()
                        viewModel.saveTextSize()
                    }

                    b.imgGiam.setOnClickListener {
                        viewModel.setTextSizeReduce()
                    }
                    b.imgTang.setOnClickListener {
                        viewModel.setTextSizeIncrease()
                    }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d("AAA","onresum")
    }


    override fun onPause() {
        super.onPause()
        Log.d("AAA","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("AAA","onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("AAA","onReset")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AAA","onDestroy")
    }


}
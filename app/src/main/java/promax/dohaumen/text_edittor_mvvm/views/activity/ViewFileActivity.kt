package promax.dohaumen.text_edittor_mvvm.views.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityViewFileBinding
import promax.dohaumen.text_edittor_mvvm.helper.*
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.viewmodel.ViewFileActivityViewModel
import promax.dohaumen.text_edittor_mvvm.views.dialog.DialogSettingEditView
import promax.hmp.dev.utils.TimeDelayUlti
import java.io.File
import java.lang.Exception


class ViewFileActivity : AppCompatActivity() {
    val b: ActivityViewFileBinding by lazy { ActivityViewFileBinding.inflate(layoutInflater) }
    val viewModel: ViewFileActivityViewModel by lazy { ViewModelProvider(this).get(ViewFileActivityViewModel::class.java) }

    private var readOnLy = false // readOnly ap dung voi mo file da bi xoa
    private var contentFile: String? = null // content open from storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        hViewModel()
        setConfigToolBar()
        handleAction()

        b.editViewFile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setTextLineNumber(b.tvLineNumber, b.editViewFile)
            }
        })

    }

    fun hViewModel() {
        viewModel.isEditTextEnable().observeForever { isEditHometEnable ->
            b.editViewFile.isEnabled = isEditHometEnable

            if (isEditHometEnable) {
                menu?.findItem(R.id.menu_edit)?.setIcon(R.drawable.ic_edit)
                Toast.makeText(this, "editing...", Toast.LENGTH_SHORT).show()
                b.editViewFile.setSelection(b.editViewFile.text.toString().length)
            } else {
                menu?.findItem(R.id.menu_edit)?.setIcon(R.drawable.ic_edit_gray)
            }
        }
        viewModel.getTextSize().observeForever {
            b.editViewFile.textSize = it.toFloat()
            b.tvLineNumber.textSize = it.toFloat()
        }

        DialogSettingEditView.isShowLineNumber().observeForever {
            b.tvLineNumber.visibility = if (it) View.VISIBLE else View.GONE
        }
        DialogSettingEditView.isAutoCap().observeForever { isAutoCap ->
            if (isAutoCap) {
                b.editViewFile.setInputType(
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                            InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or
                            InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                )
            } else {
                b.editViewFile.setInputType(
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_FLAG_MULTI_LINE or
                            InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                )
            }
        }
    }

    fun setConfigToolBar() {
        setSupportActionBar(b.toolBar)
        b.appbarLayout.outlineProvider = null

        b.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun handleAction() {
        // Mở từ list file bị xóa
        if (intent.action == "Action view file read-only") {
            readOnLy = true
            val fileText: FileText? = intent.getParcelableExtra("fileText")
            b.editViewFile.setText(fileText!!.content)
            viewModel.fileText = fileText
            supportActionBar!!.title = viewModel.fileText?.name
            TimeDelayUlti.setTime(100).runAfterMilisecond {
                setTextLineNumber(b.tvLineNumber, b.editViewFile)
            }
        }

        // Mở từ file văn bản bằng app khác
        if (intent.action == Intent.ACTION_VIEW) {
            contentFile = readTextFromUri(intent.data!!)
            b.editViewFile.setText(contentFile)
            supportActionBar!!.title = File(intent.data!!.path).name
            TimeDelayUlti.setTime(100).runAfterMilisecond {
                setTextLineNumber(b.tvLineNumber, b.editViewFile)
            }
            b.tvLineNumber.visibility = View.VISIBLE // mở từ file trong storage sẽ luôn luôn hiển thị line number
        }

        // Mở từ list file bình thường
        if (intent.action == null) {
            val fileText: FileText? = intent.getParcelableExtra("fileText")
            b.editViewFile.setText(fileText!!.content)
            viewModel.fileText = fileText
            supportActionBar!!.title = viewModel.fileText?.name
            TimeDelayUlti.setTime(100).runAfterMilisecond {
                setTextLineNumber(b.tvLineNumber, b.editViewFile)
            }
        }
    }





    private var menu: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_view_file_menu, menu)
        this.menu = menu
        if (readOnLy) {
            menu?.findItem(R.id.menu_save)?.isVisible = false
            menu?.findItem(R.id.menu_edit)?.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                viewModel.setItemEditEnableClick()
            }
            R.id.menu_save -> {
                if (intent.action == Intent.ACTION_VIEW) {
                    if (isGrantedpermisstionWriteFile()) {
                        writeFileText(intent.data!!, b.editViewFile.text.toString())
                        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
                    } else {
                        requestPermisstionWriteFile()
                    }
                } else
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.btn_save))
                        .setPositiveButton("OK") { s, s1 ->
                            viewModel.saveFileText(b.editViewFile.text.toString())
                            Toast.makeText(this, getString(R.string.mess_saved), Toast.LENGTH_SHORT)
                                .show()
                        }
                        .setNegativeButton(getString(R.string.btn_cancel)) { s, s1 ->

                        }.show()
            }
            R.id.menu_setting -> {
                val dialog = DialogSettingEditView(this).apply {
                    viewModel.onTextSizeChangeComple = {
                        b.tvTextSize.setText("$it sp")
                    }

                    b.checkboxShowLineNumber.isChecked = DialogSettingEditView.isShowLineNumber().value!!
                    b.checkboxShowLineNumber.setOnClickListener {
                        val isChecked = b.checkboxShowLineNumber.isChecked
                        DialogSettingEditView.saveIsShowLineNummber(isChecked)
                    }

                    b.checkboxIsAutoCap.isChecked = DialogSettingEditView.isAutoCap().value!!
                    b.checkboxIsAutoCap.setOnClickListener {
                        val isChecked = b.checkboxIsAutoCap.isChecked
                        DialogSettingEditView.saveAutoCap(isChecked)
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


    override fun onBackPressed() {
        when(intent.action) {
            Intent.ACTION_VIEW -> {
                if (b.editViewFile.text.toString() != contentFile) {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.btn_save)
                        .setMessage(getString(R.string.mess_save_and_exit))
                        .setPositiveButton(getString(R.string.btn_save)) { s, s1 ->
                            intent.data?.let {
                                writeFileText(it, b.editViewFile.text.toString())
                            }
                            Toast.makeText(this, getString(R.string.mess_saved), Toast.LENGTH_SHORT).show()
                            super.onBackPressed()
                        }.setNegativeButton(getString(R.string.btn_cancel)) { s, s1 ->
                            super.onBackPressed()
                        }.show()
                } else {
                    super.onBackPressed()
                }
            }
            else -> {
                if (viewModel.fileText?.content != b.editViewFile.text.toString() && intent.action == null) {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.btn_save)
                        .setMessage(getString(R.string.mess_save_and_exit))
                        .setPositiveButton(getString(R.string.btn_save)) { s, s1 ->
                            viewModel.saveFileText(b.editViewFile.text.toString())
                            Toast.makeText(this, getString(R.string.mess_saved), Toast.LENGTH_SHORT).show()
                            super.onBackPressed()
                        }.setNegativeButton(getString(R.string.btn_cancel)) { s, s1 ->
                            super.onBackPressed()
                        }.show()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    private val RESQUEST_CODE_WRITE_FILE = 11

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RESQUEST_CODE_WRITE_FILE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // can write file
                    writeFileText(intent.data!!, b.editViewFile.text.toString())
                    Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.cannot_write_file, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestPermisstionWriteFile() {
        ActivityCompat.requestPermissions(this , arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), RESQUEST_CODE_WRITE_FILE)
    }

    private fun isGrantedpermisstionWriteFile(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
        } else {
            return true
        }
    }


}
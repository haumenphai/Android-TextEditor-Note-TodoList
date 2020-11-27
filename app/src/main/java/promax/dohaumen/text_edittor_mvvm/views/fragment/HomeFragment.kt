package promax.dohaumen.text_edittor_mvvm.views.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import promax.dohaumen.text_edittor_mvvm.views.activity.MainActivity
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentHomeBinding
import promax.dohaumen.text_edittor_mvvm.viewmodel.HomeFragmentViewModel
import promax.dohaumen.text_edittor_mvvm.views.dialog.DialogAddFile
import promax.dohaumen.text_edittor_mvvm.views.dialog.DialogSettingEditView
import promax.hmp.dev.utils.HandleUI

class HomeFragment: Fragment() {
    lateinit var b: FragmentHomeBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: HomeFragmentViewModel
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentHomeBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity
        setConfigToolBar()


        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)

        return b.root
    }

    private fun setConfigToolBar() {
        mainActivity.setSupportActionBar(b.toolBar)
        b.appbarLayout.outlineProvider = null
        mainActivity.supportActionBar!!.title = "Text Editor"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTextTemp().observeForever {
            b.editHome.setText(it)
        }
        viewModel.isEditTextEnable().observeForever { isEditHometEnable ->
            b.editHome.isEnabled = isEditHometEnable

            if (isEditHometEnable) {
                menu?.findItem(R.id.menu_edit)?.setIcon(R.drawable.ic_edit)
                Toast.makeText(mainActivity, "editing...", Toast.LENGTH_SHORT).show()
                b.editHome.setSelection(b.editHome.text.toString().length)
            } else {
                menu?.findItem(R.id.menu_edit)?.setIcon(R.drawable.ic_edit_gray)
            }
        }
        viewModel.getTextSize().observeForever {
            b.editHome.textSize = it.toFloat()
        }

    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_menu, menu)
        this.menu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                viewModel.setItemEditEnableClick()
            }
            R.id.menu_delete -> {
                AlertDialog.Builder(mainActivity)
                    .setTitle(getString(R.string.delete_temp_text))
                    .setNegativeButton(getString(R.string.btn_ok)) { dialogInterface: DialogInterface, i: Int ->
                        viewModel.deleteTextTemp()
                    }
                    .setPositiveButton(getString(R.string.btn_cancel)) { dialogInterface: DialogInterface, i: Int ->

                    }
                    .show()

            }
            R.id.menu_save -> {
                val dialog = DialogAddFile(mainActivity)
                dialog.b.btnCancel.setOnClickListener {
                    HandleUI.hideKeyboardFrom(mainActivity, dialog.b.root)
                    dialog.cancel()
                }
                dialog.b.btnSave.setOnClickListener {
                    viewModel.saveFileText(dialog.b.editFileName.text.toString(), b.editHome.text.toString())
                }
                viewModel.onSaveFileTextComple = { mess, isSuccess ->
                    Toast.makeText(mainActivity, mess, Toast.LENGTH_SHORT).show()
                    if (isSuccess) {
                        HandleUI.hideKeyboardFrom(mainActivity, dialog.b.root)
                        dialog.cancel()
                    }
                }

                HandleUI.showKeyboard(mainActivity)
                dialog.show()
            }
            R.id.menu_setting -> {
                // show dialog
                val dialog = DialogSettingEditView(mainActivity).apply {
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

    override fun onStop() {
        super.onStop()
        viewModel.saveTextTemp(b.editHome.text.toString())
    }




}
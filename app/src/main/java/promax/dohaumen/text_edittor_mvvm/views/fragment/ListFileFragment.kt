package promax.dohaumen.text_edittor_mvvm.views.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import promax.dohaumen.text_edittor_mvvm.MainActivity
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.views.activity.ViewFileActivity
import promax.dohaumen.text_edittor_mvvm.adapter.FileTextAdapter
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding
import promax.dohaumen.text_edittor_mvvm.helper.Search
import promax.dohaumen.text_edittor_mvvm.helper.demSoTu
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.viewmodel.ListFileFragmentViewModel
import promax.dohaumen.text_edittor_mvvm.views.dialog.DialogAddFile
import promax.hmp.dev.utils.HandleUI
import promax.hmp.dev.views.DialogAddNewFile2

class ListFileFragment() : Fragment() {
    private lateinit var b: FragmentListFileBinding
    private val mainActivity: MainActivity by lazy { activity as MainActivity }
    private val viewModel: ListFileFragmentViewModel by lazy { ViewModelProvider(this).get(ListFileFragmentViewModel::class.java) }
    private val adapter = FileTextAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentListFileBinding.inflate(inflater, container, false)


        hideView()
        setConfigToolBar()
        initRecyclerView()
        setClickItem()
        setClickAction()

        return b.root
    }
    private fun hideView() {
        b.layoutAction.root.visibility = View.GONE
        b.layoutSearch.visibility = View.GONE
        b.tvMess.visibility = View.GONE
        b.progressBar.visibility = View.GONE
    }



    private fun setConfigToolBar() {
        mainActivity.setSupportActionBar(b.toolBar2)
        mainActivity.supportActionBar!!.title = "List File"
        b.toolBar2.setTitleTextColor(Color.WHITE)
        b.toolBar2.inflateMenu(R.menu.list_fragment_menu)


        b.imgSearchClose.setOnClickListener {
            b.layoutSearch.visibility = View.GONE
            b.toolBar2.visibility = View.VISIBLE
            b.editSearch.setText("")
            HandleUI.hideKeyboardFrom(mainActivity, b.editSearch)
        }

        b.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    searchFileText()
                }
            }
        })
        b.editSearch.setOnEditorActionListener { v, actionId, event ->
            searchFileText()
            true
        }
    }



    private fun initRecyclerView() {
        b.recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        b.recyclerView.adapter = adapter

        viewModel.getListFileText().observeForever {
            adapter.setList(it)
            b.tvListEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }


    private fun setClickItem() {
        adapter.onClickITem = { fileText ->
            if (fileText.password != null) {
                val dialog = DialogAddFile(mainActivity)
                dialog.b.tvTitle.text = getString(R.string.enter_password)
                dialog.b.editFileName.hint = getString(R.string.password)
                dialog.b.btnSave.text = "OK"


                dialog.b.btnCancel.setOnClickListener {
                    HandleUI.hideKeyboardFrom(mainActivity, dialog.b.editFileName)
                    dialog.cancel()
                }
                fun checkPass() {
                    if (dialog.b.editFileName.text.toString() == fileText.password) {
                        HandleUI.hideKeyboardFrom(mainActivity, dialog.b.editFileName)
                        startActivityViewFile(fileText)
                        dialog.cancel()
                    } else {
                        Toast.makeText(context, getString(R.string.password_is_not_correct), Toast.LENGTH_SHORT).show()
                        dialog.b.editFileName.setText("")
                    }
                }
                dialog.b.btnSave.setOnClickListener {
                    checkPass()
                }
                dialog.b.editFileName.setOnEditorActionListener { v, actionId, event ->
                    checkPass()
                    true
                }

                HandleUI.showKeyboard(mainActivity)
                dialog.show()
            } else {
                startActivityViewFile(fileText)
            }


        }
        adapter.onLongClickITem = {
            b.layoutAction.root.visibility = View.VISIBLE

            adapter.setSwapCheckItem(it)
            adapter.onClickITem = {
                adapter.setSwapCheckItem(it)
            }
        }
    }

    private fun setClickAction() {
        fun cancelAction() {
            b.layoutAction.root.visibility = View.GONE
            setClickItem()
            adapter.unCheckAll()
        }

        b.layoutAction.actionSelectAll.setOnClickListener {
            adapter.setCheckAll()
        }
        b.layoutAction.actionCancel.setOnClickListener {
            cancelAction()
        }
        b.layoutAction.actionDelete.setOnClickListener {
            val listCheckedLength = adapter.getListChecked().size
            if (listCheckedLength != 0)
            AlertDialog.Builder(context)
                .setTitle("${getString(R.string.tv_delete)} $listCheckedLength File")
                .setMessage("${getString(R.string.do_you_want_to_delete)} $listCheckedLength files?")
                .setPositiveButton(R.string.delete) { _1, _2 ->
                    viewModel.listDeleteUndo = adapter.getListChecked()
                    viewModel.deleteListChecked(adapter.getListChecked()) {
                        Snackbar.make(b.recyclerView, "${getString(R.string.deleted)} $listCheckedLength file",3000)
                            .setAction(R.string.undo) {
                                viewModel.undoDelete()
                            }.show()
                    }
                    cancelAction()
                }
                .setNegativeButton(R.string.btn_cancel) { dialogInterface: DialogInterface, i: Int ->
                }.show()
        }
        b.layoutAction.actionRename.setOnClickListener {
            val listCheckedLength = adapter.getListChecked().size
            if (listCheckedLength != 1) {
                Snackbar.make(b.recyclerView, R.string.only_1_file_can_be_selected_to_rename, 1111).show()
            } else {
                val fileText = adapter.getListChecked().get(0)
                val dialogAddFile = DialogAddFile(mainActivity).run {
                    HandleUI.showKeyboard(mainActivity)
                    b.editFileName.setText(fileText.name)
                    b.editFileName.setSelection(0,fileText.name.length)
                    b.tvTitle.text = getString(R.string.rename)
                    b.btnCancel.setOnClickListener {
                        HandleUI.hideKeyboardFrom(mainActivity, b.editFileName)
                        cancel()
                    }

                    viewModel.onSetPasswordComplete = { mess, isSucssec ->
                        Snackbar.make(this@ListFileFragment.b.recyclerView, mess, 1111).show()
                        if (isSucssec) {
                            HandleUI.hideKeyboardFrom(mainActivity, b.editFileName)
                            cancel()
                            cancelAction()
                        }
                    }
                    b.btnSave.setOnClickListener {
                        viewModel.reNameFile(b.editFileName.text.toString(), adapter.getListChecked().get(0))
                    }

                    show()
                }
            }
        }
        b.layoutAction.actionInfo.setOnClickListener {
            val listCheckedLength = adapter.getListChecked().size
            if (listCheckedLength != 1) {
                Snackbar.make(b.recyclerView, getString(R.string.only_1_file_can_be_selected_to_view_information), 1111).show()
            } else {
                val fileText = adapter.getListChecked().get(0)
                AlertDialog.Builder(mainActivity)
                    .setTitle(R.string.info)
                    .setMessage("${getString(R.string.file_name)}: ${fileText.name}\n" +
                            "${getString(R.string.content)}: ${demSoTu(fileText.content)} ${getString(R.string.word)}, " +
                            "${fileText.content.length} ${getString(R.string.characters)}\n" +
                            "${getString(R.string.date_created)}: \n \t${fileText.dateCreate}\n" +
                            "${getString(R.string.last_edited_date)}: \n \t${fileText.lastEditedDate}")
                    .setNegativeButton("Ok") {s,s1 ->}
                    .show()
            }
        }
        b.layoutAction.actionSetPassword.setOnClickListener {
            val listCheckedLength = adapter.getListChecked().size

            if (listCheckedLength != 1) {
                Snackbar.make(b.recyclerView, R.string.only_1_file_can_be_selected_to_set_password, 1111).show()
            } else {
                val fileText = adapter.getListChecked().get(0)
                val popupMenu = PopupMenu(context, b.layoutAction.actionSetPassword)
                if (fileText.password != null) {
                    popupMenu.menu.add(R.string.change_password)
                    popupMenu.menu.add(R.string.delete_password)
                } else {
                    popupMenu.menu.add(R.string.set_password)
                }

                popupMenu.setOnMenuItemClickListener {
                    when (it.title) {
                        getString(R.string.set_password) -> {
                            val dialog = DialogAddNewFile2(context!!)
                            dialog.dialog.setCanceledOnTouchOutside(false)
                            dialog.tvTitle.text = getString(R.string.set_password)
                            dialog.tvMess.visibility = View.GONE
                            dialog.editText2.visibility = View.VISIBLE
                            dialog.editText1.hint = getString(R.string.password)
                            dialog.editText2.hint = getString(R.string.confirm_password)

                            dialog.btnCancel.setOnClickListener {
                                HandleUI.hideKeyboardFrom(context, dialog.editText1)
                                dialog.cancel()
                            }
                            dialog.btnOk.setOnClickListener {
                                viewModel.setPasswordToFile(fileText, dialog.editText1.text.toString(), dialog.editText2.text.toString())
                            }
                            viewModel.onSetPasswordComplete = { mess, isSucssec ->
                                Toast.makeText(mainActivity, mess, Toast.LENGTH_SHORT).show()
                                if (isSucssec) {
                                    HandleUI.hideKeyboardFrom(context, dialog.editText1)
                                    dialog.cancel()
                                }
                            }

                            HandleUI.showKeyboard(context)
                            dialog.show()
                        }
                        getString(R.string.change_password) -> {
                            val dialog = DialogAddNewFile2(context!!)
                            dialog.dialog.setCanceledOnTouchOutside(false)
                            dialog.tvTitle.text = getString(R.string.change_password)
                            dialog.tvMess.visibility = View.GONE
                            dialog.editText2.visibility = View.VISIBLE
                            dialog.editText1.hint = getString(R.string.enter_old_password)
                            dialog.editText2.hint = getString(R.string.enter_new_password)

                            dialog.btnCancel.setOnClickListener {
                                HandleUI.hideKeyboardFrom(context, dialog.editText1)
                                dialog.cancel()
                            }
                            dialog.btnOk.setOnClickListener {
                                viewModel.changePassword(fileText, dialog.editText1.text.toString(), dialog.editText2.text.toString())
                            }
                            viewModel.onChangePasswordComplete = { mess, isSucssec ->
                                Toast.makeText(mainActivity, mess, Toast.LENGTH_SHORT).show()
                                if (isSucssec) {
                                    HandleUI.hideKeyboardFrom(context, dialog.editText1)
                                    dialog.cancel()
                                }
                            }

                            HandleUI.showKeyboard(context)
                            dialog.show()
                        }
                        getString(R.string.delete_password) -> {
                            val dialog = DialogAddNewFile2(context!!)
                            dialog.dialog.setCanceledOnTouchOutside(false)
                            dialog.tvTitle.text = getString(R.string.delete_password)
                            dialog.tvMess.visibility = View.GONE
                            dialog.editText1.hint = getString(R.string.enter_old_password)

                            dialog.btnCancel.setOnClickListener {
                                HandleUI.hideKeyboardFrom(context, dialog.editText1)
                                dialog.cancel()
                            }
                            dialog.btnOk.setOnClickListener {
                                viewModel.deletePassWord(fileText, dialog.editText1.text.toString())
                            }
                            viewModel.onDeletePasswordComplete = { mess, isSucssec ->
                                Toast.makeText(mainActivity, mess, Toast.LENGTH_SHORT).show()
                                if (isSucssec) {
                                    HandleUI.hideKeyboardFrom(context, dialog.editText1)
                                    dialog.cancel()
                                }
                            }

                            HandleUI.showKeyboard(context)
                            dialog.show()
                        }
                    }
                    true
                }

                popupMenu.show()
            }



        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_file -> {
                val dialogAddFile = DialogAddFile(mainActivity)

                viewModel.onSaveFileTextComple = { mess, isSuccess ->
                    Toast.makeText(mainActivity, mess, Toast.LENGTH_SHORT).show()
                    if (isSuccess) {
                        HandleUI.hideKeyboardFrom(mainActivity, dialogAddFile.b.root)
                        dialogAddFile.cancel()
                    }
                }

                dialogAddFile.b.btnCancel.setOnClickListener {
                    HandleUI.hideKeyboardFrom(mainActivity, dialogAddFile.b.root)
                    dialogAddFile.cancel()
                }
                dialogAddFile.b.btnSave.setOnClickListener {
                    viewModel.addFileText(dialogAddFile.b.editFileName.text.toString(), "")
                }

                HandleUI.showKeyboard(mainActivity)
                dialogAddFile.show()

            }
            R.id.menu_search -> {
                b.layoutSearch.visibility = View.VISIBLE
                b.toolBar2.visibility = View.GONE
                b.editSearch.requestFocus()
                HandleUI.showKeyboard(context)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun startActivityViewFile(fileText: FileText) {
        val intent = Intent(context, ViewFileActivity::class.java)
        intent.putExtra("fileText", fileText)
        startActivity(intent)
    }

    private fun searchFileText() {
        val key = b.editSearch.text.toString()
        Search.searchFileText(viewModel.getListFileText().value!!, key, onPreSearch = {
            b.progressBar.visibility = View.VISIBLE
            b.tvMess.visibility = View.GONE
        }, onComplete = { result ->
            adapter.setList(result)
            if (result.isEmpty()) {
                b.tvMess.visibility = View.VISIBLE
                b.tvMess.setText(getString(R.string.not_found))
            }
            b.progressBar.visibility = View.GONE
        })
    }


}
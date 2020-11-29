package promax.dohaumen.text_edittor_mvvm.views.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.adapter.FileTextAdapter
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileDeletedBinding
import promax.dohaumen.text_edittor_mvvm.helper.demSoTu
import promax.dohaumen.text_edittor_mvvm.helper.searchFileText
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.viewmodel.ListFileDeletedFragmentViewModel
import promax.dohaumen.text_edittor_mvvm.views.activity.ViewFileActivity
import promax.dohaumen.text_edittor_mvvm.views.activity.ViewListFileDeteledActivity
import promax.dohaumen.text_edittor_mvvm.views.dialog.DialogAddFile
import promax.hmp.dev.utils.HandleUI

class ListFileDeletedFragment: Fragment() {
    lateinit var b: FragmentListFileDeletedBinding
    lateinit var myActivity: ViewListFileDeteledActivity
    lateinit var viewModel: ListFileDeletedFragmentViewModel
    var adapter = FileTextAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentListFileDeletedBinding.inflate(inflater, container, false)
        b.layoutAction.root.visibility = View.GONE

        myActivity = activity as ViewListFileDeteledActivity
        viewModel = ViewModelProvider(this).get(ListFileDeletedFragmentViewModel::class.java)

        setConfigToolBar()
        initRecyclerView()
        setClickItem()
        setClickAction()

        return b.root
    }

    private fun setConfigToolBar() {
        myActivity.setSupportActionBar(b.toolBar2)
        myActivity.supportActionBar!!.title = "List File deleted"
        b.toolBar2.setTitleTextColor(Color.WHITE)
        b.toolBar2.setNavigationOnClickListener {
            myActivity.onBackPressed()
        }

        b.layoutSearch.visibility = View.GONE
        b.imgSearchClose.setOnClickListener {
            b.layoutSearch.visibility = View.GONE
            b.toolBar2.visibility = View.VISIBLE
            b.editSearch.setText("")
            HandleUI.hideKeyboardFrom(myActivity, b.editSearch)
        }

        b.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    adapter.setList(searchFileText(viewModel.getListFileText().value!!, b.editSearch.text.toString()))
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    private fun initRecyclerView() {
        adapter.hienThiItemListFileBiXoa = true
        b.recyclerView.layoutManager = LinearLayoutManager(myActivity)
        b.recyclerView.adapter = adapter

        viewModel.getListFileText().observeForever {
            adapter.setList(it)
            b.tvListEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE

        }
    }


    private fun setClickItem() {
        adapter.onClickITem = { fileText ->
            // todo: problem?
            if (fileText.password != null) {
                val dialog = DialogAddFile(myActivity)
                dialog.b.tvTitle.text = getString(R.string.enter_password)
                dialog.b.editFileName.hint = getString(R.string.password)
                dialog.b.btnSave.text = "OK"

                dialog.b.btnCancel.setOnClickListener {
                    HandleUI.hideKeyboardFrom(myActivity, dialog.b.editFileName)
                    dialog.cancel()
                }
                fun checkPass() {
                    if (dialog.b.editFileName.text.toString() == fileText.password) {
                        HandleUI.hideKeyboardFrom(myActivity, dialog.b.editFileName)
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

                HandleUI.showKeyboard(myActivity)
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
        b.layoutAction.actionRename.visibility = View.GONE
        b.layoutAction.actionSetPassword.visibility = View.GONE
        b.layoutAction.actionRestore.visibility = View.VISIBLE

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
            if (listCheckedLength != 0) {
                AlertDialog.Builder(context)
                    .setTitle("${getString(R.string.tv_delete)} $listCheckedLength File")
                    .setMessage("${getString(R.string.do_you_want_to_delete)} $listCheckedLength files?\n" +
                            getString(R.string.deleted_files_cannot_be_recovered)
                    )
                    .setPositiveButton(R.string.delete) { _1, _2 ->
                        viewModel.deleteListChecked(adapter.getListChecked()) {
                            Toast.makeText(context, "${getString(R.string.deleted)} $listCheckedLength file", Toast.LENGTH_SHORT).show()
                        }
                        cancelAction()
                    }
                    .setNegativeButton(R.string.btn_cancel) { dialogInterface: DialogInterface, i: Int ->
                    }.show()
            }
        }
        b.layoutAction.actionInfo.setOnClickListener {
            val listCheckedLength = adapter.getListChecked().size
            if (listCheckedLength != 1) {
                Snackbar.make(b.recyclerView, getString(R.string.only_1_file_can_be_selected_to_view_information), 1111).show()
            } else {
                val fileText = adapter.getListChecked().get(0)
                AlertDialog.Builder(myActivity)
                    .setTitle(R.string.info)
                    .setMessage("${getString(R.string.file_name)}: ${fileText.name}\n" +
                            "${getString(R.string.content)}: ${demSoTu(fileText.content)} ${getString(R.string.word)}, " +
                            "${fileText.content.length} ${getString(R.string.characters)}\n" +
                            "${getString(R.string.date_created)}: \n \t${fileText.dateCreate}\n" +
                            "${getString(R.string.last_edited_date)}: \n \t${fileText.lastEditedDate}\n" +
                            "${getString(R.string.date_deleted)}: \n \t${fileText.dateDeteled}\n")
                    .setNegativeButton("Ok") {s,s1 ->}
                    .show()
            }
        }
        b.layoutAction.actionRestore.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(R.string.restore)
                .setNegativeButton(R.string.btn_cancel) {s1,s2->

                }.setPositiveButton(R.string.btn_ok) {s1, s2->
                    viewModel.restoreListChecked(adapter.getListChecked()) {
                        Toast.makeText(context, R.string.successful_recovery, Toast.LENGTH_SHORT).show()
                    }
                }.show()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        menu.findItem(R.id.menu_add_file).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                b.layoutSearch.visibility = View.VISIBLE
                b.toolBar2.visibility = View.GONE
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startActivityViewFile(fileText: FileText) {
        val intent = Intent(context, ViewFileActivity::class.java)
        intent.setAction("Action view file read-only")
        intent.putExtra("fileText", fileText)
        startActivity(intent)
    }
}
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import promax.dohaumen.text_edittor_mvvm.views.activity.MainActivity
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.views.activity.ViewFileActivity
import promax.dohaumen.text_edittor_mvvm.adapter.FileTextAdapter
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding
import promax.dohaumen.text_edittor_mvvm.helper.demSoTu
import promax.dohaumen.text_edittor_mvvm.helper.searchFileText
import promax.dohaumen.text_edittor_mvvm.viewmodel.ListFileFragmentViewModel
import promax.dohaumen.text_edittor_mvvm.views.dialog.DialogAddFile
import promax.hmp.dev.utils.HandleUI

class ListFileFragment: Fragment() {
    lateinit var b: FragmentListFileBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: ListFileFragmentViewModel
    var adapter = FileTextAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentListFileBinding.inflate(inflater, container, false)
        b.layoutAction.root.visibility = View.GONE

        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(this).get(ListFileFragmentViewModel::class.java)

        setConfigToolBar()
        initRecyclerView()
        setClickItem()
        setClickAction()

        return b.root
    }

    private fun setConfigToolBar() {
        mainActivity.setSupportActionBar(b.toolBar2)
        mainActivity.supportActionBar!!.title = "List File"
        b.toolBar2.setTitleTextColor(Color.WHITE)
        b.toolBar2.inflateMenu(R.menu.list_fragment_menu)

        b.layoutSearch.visibility = View.GONE
        b.imgSearchClose.setOnClickListener {
            b.layoutSearch.visibility = View.GONE
            b.toolBar2.visibility = View.VISIBLE
            b.editSearch.setText("")
            HandleUI.hideKeyboardFrom(mainActivity, b.editSearch)
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


    fun initRecyclerView() {
        b.recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        b.recyclerView.adapter = adapter

        viewModel.getListFileText().observeForever {
            adapter.setList(it)
        }
    }

    fun setClickItem() {
        adapter.onClickITem = {
            val intent = Intent(context, ViewFileActivity::class.java)
            intent.putExtra("fileText", it)
            startActivity(intent)
        }
        adapter.onLongClickITem = {
            b.layoutAction.root.visibility = View.VISIBLE

            adapter.setSwapCheckItem(it)
            adapter.onClickITem = {
                adapter.setSwapCheckItem(it)
            }
        }
    }

    fun setClickAction() {
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
            AlertDialog.Builder(context)
                .setTitle("Delete $listCheckedLength File")
                .setMessage("Do you want to delete $listCheckedLength files?")
                .setPositiveButton("OK") { _1, _2 ->
                    viewModel.deleteListChecked(adapter.getListChecked()) {
                        Snackbar.make(b.recyclerView, "Đã xóa $listCheckedLength file",1111).show()
                    }
                    cancelAction()
                }
                .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                }.show()
        }
        b.layoutAction.actionRename.setOnClickListener {
            val listCheckedLength = adapter.getListChecked().size
            if (listCheckedLength != 1) {
                Snackbar.make(b.recyclerView, "Chỉ được chọn 1 file để đổi tên", 1111).show()
            } else {
                val fileText = adapter.getListChecked().get(0)
                val dialogAddFile = DialogAddFile(mainActivity).run {
                    HandleUI.showKeyboard(mainActivity)
                    b.editFileName.setText(fileText.name)
                    b.editFileName.setSelection(0,fileText.name.length)
                    b.tvTitle.text = "Đổi tên"
                    b.btnCancel.setOnClickListener {
                        HandleUI.hideKeyboardFrom(mainActivity, b.editFileName)
                        cancel()
                    }

                    viewModel.onRenameComple = { mess, isSucssec ->
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
            val fileText = adapter.getListChecked().get(0)
            if (listCheckedLength != 1) {
                Snackbar.make(b.recyclerView, "Chỉ được chọn 1 file để xem thông tin", 1111).show()
            }
            AlertDialog.Builder(mainActivity)
                .setTitle("Thông tin")
                .setMessage("Tên file: ${fileText.name}\n" +
                            "Nội dung: ${demSoTu(fileText.content)} từ, ${fileText.content.length} kí tự\n" +
                            "Ngày tạo: \n \t${fileText.date}\n" +
                            "Ngày chỉnh sửa gần nhất: \n \t${fileText.lastEditedDate}")
                .setNegativeButton("Ok") {s,s1 ->}
                .show()
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
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
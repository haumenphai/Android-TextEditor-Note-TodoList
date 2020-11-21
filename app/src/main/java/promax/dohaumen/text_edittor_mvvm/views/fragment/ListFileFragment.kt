package promax.dohaumen.text_edittor_mvvm.views.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import promax.dohaumen.text_edittor_mvvm.views.activity.MainActivity
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.views.activity.ViewFileActivity
import promax.dohaumen.text_edittor_mvvm.adapter.FileTextAdapter
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding
import promax.dohaumen.text_edittor_mvvm.viewmodel.ListFileFragmentViewModel
import promax.dohaumen.text_edittor_mvvm.views.DialogAddFile
import promax.hmp.dev.utils.HandleUI

class ListFileFragment: Fragment() {
    lateinit var b: FragmentListFileBinding
    lateinit var mainActivity: MainActivity
    lateinit var viewModel: ListFileFragmentViewModel
    var adapter = FileTextAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        b = FragmentListFileBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(this).get(ListFileFragmentViewModel::class.java)

        initRecyclerView()
        return b.root
    }



    fun initRecyclerView() {
        b.recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        b.recyclerView.adapter = adapter

        viewModel.getListFileText().observeForever {
            adapter.setList(it)
        }

        adapter.onClickITem = {
            val intent = Intent(context, ViewFileActivity::class.java)
            intent.putExtra("fileText", it)
            startActivity(intent)
        }
        adapter.onLongClickITem = {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
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
                    viewModel.saveFileText(dialogAddFile.b.editFileName.text.toString(), "")
                }

                HandleUI.showKeyboard(mainActivity)
                dialogAddFile.show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

}
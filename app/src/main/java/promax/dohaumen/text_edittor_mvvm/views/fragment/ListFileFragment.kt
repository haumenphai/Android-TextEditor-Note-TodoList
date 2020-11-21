package promax.dohaumen.text_edittor_mvvm.views.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import promax.dohaumen.text_edittor_mvvm.MainActivity
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.adapter.FileTextAdapter
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.viewmodel.ListFileFragmentViewModel
import promax.dohaumen.text_edittor_mvvm.views.DialogAddFile
import promax.hmp.dev.utils.HandleUI
import promax.hmp.dev.utils.NetworkUtil

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

        viewModel.getList().observeForever {
            it.reversed()
            adapter.setList(it)
        }

        adapter.onClickITem = {

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

                dialogAddFile.b.btnCancel.setOnClickListener {
                    HandleUI.hideKeyboardFrom(mainActivity, dialogAddFile.b.root)
                    dialogAddFile.cancel()
                }
                dialogAddFile.b.btnSave.setOnClickListener {
                    // todo: remove code to view model
                    val fileText = FileText(dialogAddFile.b.editFileName.text.toString(), "","fake")
                    FileTextDatabase.getINSTANCE().dao().insert(fileText)
                    dialogAddFile.cancel()
                    HandleUI.hideKeyboardFrom(mainActivity, dialogAddFile.b.root)
                }

                HandleUI.showKeyboard(mainActivity)
                dialogAddFile.show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

}
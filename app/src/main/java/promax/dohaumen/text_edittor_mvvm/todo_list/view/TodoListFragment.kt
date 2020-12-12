package promax.dohaumen.text_edittor_mvvm.todo_list.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentTodoListBinding
import promax.dohaumen.text_edittor_mvvm.todo_list.adapter.TaskAdapter
import promax.dohaumen.text_edittor_mvvm.helper.Search
import promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel.TodoListViewModel
import promax.dohaumen.text_edittor_mvvm.views.activity.MainActivity
import promax.hmp.dev.utils.HandleUI
import java.util.*
import java.util.concurrent.Executors


class TodoListFragment: Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var b: FragmentTodoListBinding
    private val viewModel: TodoListViewModel by lazy { ViewModelProvider(this).get(TodoListViewModel::class.java) }
    val adapter: TaskAdapter = TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)
        mainActivity = activity as MainActivity


        b.recyclerView.layoutManager = LinearLayoutManager(context)
        b.recyclerView.adapter = adapter
        b.viewModel = viewModel
        b.lifecycleOwner = this

        viewModel.tasks.observe(viewLifecycleOwner, {
            adapter.setList(it)
        })


        setConfigToolBar()
        setClick()
        return b.root
    }

    private fun setConfigToolBar() {
        b.layoutSearch.visibility = View.GONE
        mainActivity.setSupportActionBar(b.toolBar)
        b.toolBar.inflateMenu(R.menu.todo_list_fragment_menu)
    }

    private fun setClick() {
        b.imgSearchClose.setOnClickListener {
            b.editSearch.setText("")
            b.layoutSearch.visibility = View.GONE
            HandleUI.hideKeyboardFrom(context, b.editSearch)
            adapter.setList(viewModel.tasks.value!!)
        }
        b.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchTask()
            }
        })
        b.editSearch.setOnEditorActionListener { v, actionId, event ->
            searchTask()
            HandleUI.hideKeyboardFrom(context, b.editSearch)
            true
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> {
                b.layoutSearch.visibility = View.VISIBLE
                b.editSearch.requestFocus()
                HandleUI.showKeyboard(context)
            }
            R.id.menu_delete_file_completed -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun searchTask() {
        val key = b.editSearch.text.toString()
        lifecycleScope.launch {
            Search.searchTask(viewModel.tasks.value!!, key) { result ->
                adapter.setList(result)
            }
        }
    }

}
package promax.dohaumen.text_edittor_mvvm.todo_list.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentTodoListBinding
import promax.dohaumen.text_edittor_mvvm.helper.Search
import promax.dohaumen.text_edittor_mvvm.todo_list.adapter.TaskAdapter
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskDatabase
import promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel.TodoListViewModel
import promax.dohaumen.text_edittor_mvvm.MainActivity
import promax.hmp.dev.utils.HandleUI


class TodoListFragment: Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var b: FragmentTodoListBinding
    private val viewModel: TodoListViewModel by lazy { ViewModelProvider(this).get(TodoListViewModel::class.java) }
    private val adapter: TaskAdapter = TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)
        mainActivity = activity as MainActivity


        b.recyclerView.layoutManager = LinearLayoutManager(context)
        b.recyclerView.adapter = adapter
        b.lifecycleOwner = this

        viewModel.tasks.observe(viewLifecycleOwner, {
            adapter.setList(it)
        })

        hideView()
        setConfigToolBar()
        setClick()
        setClickItemRecyclerView()
        setClickAction()
        return b.root
    }

    private fun hideView() {
        b.progressBar.visibility        = View.GONE
        b.tvMess.visibility             = View.GONE
        b.layoutAction.root.visibility  = View.GONE
    }

    lateinit var menu: Menu
    private fun setConfigToolBar() {
        b.layoutSearch.visibility = View.GONE
        mainActivity.setSupportActionBar(b.toolBar)
        b.toolBar.inflateMenu(R.menu.todo_list_fragment_menu)

        menu = b.toolBar.menu
        viewModel.isShowNumber.observe(this, { isShow ->
            val item = menu.findItem(R.id.menu_show_number)
            item.isChecked = isShow
            adapter.isShowNumber = isShow
            adapter.notifyDataSetChanged()
        })

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

    private fun setClickItemRecyclerView() {
        adapter.onClickItem = { task, i ->
            DialogViewTask(mainActivity, task).show()
        }
        adapter.onLongClick = { it, i ->
            b.layoutAction.root.visibility  = View.VISIBLE
            it.isSelected = !it.isSelected
            adapter.onClickItem = { it2, i2 ->
                it2.isSelected = !it2.isSelected
                adapter.notifyItemChanged(i2)
            }
            adapter.setSelectMode(true)

        }
    }

    private fun setClickAction() {
        fun cancelAction() {
            b.layoutAction.root.visibility = View.GONE
            adapter.getList().forEach {
                it.isSelected = false
            }
            adapter.setSelectMode(false)
            setClickItemRecyclerView()
        }

        b.layoutAction.actionCancel.setOnClickListener {
            cancelAction()
        }
        b.layoutAction.actionSelectAll.setOnClickListener {
            adapter.getList().forEach { task -> task.isSelected = true }
            adapter.notifyDataSetChanged()
        }
        b.layoutAction.actionDelete.setOnClickListener {
            val listSelected = adapter.getListSelected()
            if (listSelected.isEmpty()) {
                Toast.makeText(context, getString(R.string.no_task_select), Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(context)
                    .setTitle(R.string.delete_task)
                    .setMessage("${getString(R.string.delete)} ${listSelected.size} ${getString(R.string.task)} ?")
                    .setPositiveButton(R.string.btn_ok) { d,i ->
                        listSelected.forEach {
                            TaskDatabase.get.dao().delete(it)
                        }
                        Snackbar.make(b.recyclerView, R.string.deleted, 3000)
                            .setAction(R.string.undo) {
                                listSelected.forEach {
                                    TaskDatabase.get.dao().insert(it)
                                }
                            }.show()

                    }.setNegativeButton(R.string.btn_cancel) { d,i->

                    }.show()
            }
        }
        b.layoutAction.actionRename.setOnClickListener {
            val listSelected = adapter.getListSelected()
            val itemSelected = listSelected[0]
            if (listSelected.size != 1) {
                Toast.makeText(context, R.string.only_once_task_can_be_selected, Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(mainActivity, AddTaskActivity::class.java)
                intent.action = "edit task"
                intent.putExtra("task", itemSelected)
                startActivity(intent)
                cancelAction()
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> {
                b.layoutSearch.visibility = View.VISIBLE
                b.editSearch.requestFocus()
                HandleUI.showKeyboard(context)
            }
            R.id.menu_hide_task_completed -> {
                adapter.hideTaskCompleted()
                adapter.notifyDataSetChanged()
            }
            R.id.menu_add_task -> {
                val intent = Intent(mainActivity, AddTaskActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_show_number -> {
                item.setChecked(!item.isChecked)
                viewModel.isShowNumber.value = item.isChecked
            }
            R.id.menu_view_task -> {
                item.isChecked = true
                menu.findItem(R.id.menu_view_task_completed).isChecked = false
                menu.findItem(R.id.menu_view_all_task).isChecked = false
                adapter.setList(viewModel.tasks.value!!)
                adapter.checkBoxEnable = true
                adapter.notifyDataSetChanged()
            }
            R.id.menu_view_task_completed -> {
                item.isChecked = true
                menu.findItem(R.id.menu_view_task).isChecked = false
                menu.findItem(R.id.menu_view_all_task).isChecked = false
                adapter.setList(TaskDatabase.get.dao().getList(true))
                adapter.checkBoxEnable = false
                adapter.notifyDataSetChanged()
            }
            R.id.menu_view_all_task -> {
                item.isChecked = true
                menu.findItem(R.id.menu_view_task).isChecked = false
                menu.findItem(R.id.menu_view_task_completed).isChecked = false
                adapter.setList(TaskDatabase.get.dao().getList())
                adapter.checkBoxEnable = false
                adapter.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun searchTask() {
        val key = b.editSearch.text.toString()
        lifecycleScope.launch {
            Search.searchTask(viewModel.allTasks, key, onPreSearch = {
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

    override fun onStop() {
        super.onStop()
        viewModel.saveData()
    }


}
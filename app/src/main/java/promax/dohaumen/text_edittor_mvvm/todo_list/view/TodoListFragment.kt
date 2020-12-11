package promax.dohaumen.text_edittor_mvvm.todo_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentTodoListBinding
import promax.dohaumen.text_edittor_mvvm.todo_list.adapter.TaskAdapter
import promax.dohaumen.text_edittor_mvvm.todo_list.viewmodel.TodoListViewModel
import promax.dohaumen.text_edittor_mvvm.views.activity.MainActivity

class TodoListFragment: Fragment() {
    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentTodoListBinding
    lateinit var viewModel: TodoListViewModel
    val adapter: TaskAdapter = TaskAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)
        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        viewModel.tasks.observe(viewLifecycleOwner, {
            adapter.listTask = it
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
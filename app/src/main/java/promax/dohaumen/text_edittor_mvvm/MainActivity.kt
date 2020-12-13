package promax.dohaumen.text_edittor_mvvm

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityMainBinding
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.dohaumen.text_edittor_mvvm.todo_list.data.TaskDatabase
import promax.dohaumen.text_edittor_mvvm.views.fragment.HomeFragment
import promax.dohaumen.text_edittor_mvvm.views.fragment.ListFileFragment
import promax.dohaumen.text_edittor_mvvm.views.fragment.SettingFragment
import promax.dohaumen.text_edittor_mvvm.todo_list.view.TodoListFragment


class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding
    lateinit var botNavMenu: Menu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        controlFragmentWithSaveState()

        b.bottomNav.inflateMenu(R.menu.bottom_nav_main)
//        b.bottomNav.menu.getItem(1).isVisible = false

        // for test todo_list
//        TaskDatabase.get.dao().deleteAll()
//        for (i in 1..100) {
//            val task = Task("nhiem vu: $i","")
//            TaskDatabase.get.dao().insert(task)
//        }

    }

    /**
     * Lưu giữ trạng thái của fragment khi chuyển qua fragment bằng bottomNav.
     * Tất cả các fragment được khởi tạo cùng lúc, có cùng trạng thái.
     */
    private fun controlFragmentWithSaveState() {
        val homeFragment = HomeFragment()
        val listFileFragment = ListFileFragment()
        val settingFragment = SettingFragment()
        val todoListFragment = TodoListFragment()

        var activeFragment: Fragment = homeFragment
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container_fragment, homeFragment, "home")
            add(R.id.container_fragment, listFileFragment, "list").hide(listFileFragment)
            add(R.id.container_fragment, settingFragment, "setting").hide(settingFragment)
            add(R.id.container_fragment, todoListFragment, "todo_list").hide(todoListFragment)
        }.commit()

        b.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                }

                R.id.list_file -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(listFileFragment).commit()
                    activeFragment = listFileFragment
                }
                R.id.setting -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(settingFragment).commit()
                    activeFragment = settingFragment
                }
                R.id.todo_list -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(todoListFragment).commit()
                    activeFragment = todoListFragment
                }
            }

            true
        }
    }


    /**
     * Không lưu giữ trạng thái khi chuyển qua lại các fragment,
     */
    private fun controlFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment, HomeFragment()).commit()

        b.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container_fragment,
                        HomeFragment()
                    ).commit()
                }
                R.id.list_file -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container_fragment,
                        ListFileFragment()
                    ).commit()
                }
                R.id.setting -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.container_fragment,
                        SettingFragment()
                    ).commit()
                }
            }

            true
        }
    }


}

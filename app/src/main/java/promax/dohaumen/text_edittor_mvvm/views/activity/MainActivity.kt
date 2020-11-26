package promax.dohaumen.text_edittor_mvvm.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityMainBinding
//import promax.dohaumen.text_edittor_mvvm.databinding.ActivityMainBinding
import promax.dohaumen.text_edittor_mvvm.views.fragment.HomeFragment
import promax.dohaumen.text_edittor_mvvm.views.fragment.ListFileFragment
import promax.dohaumen.text_edittor_mvvm.views.fragment.SettingFragment


class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        controlFragmentWithSaveState()
    }

    /**
     * Lưu giữ trạng thái của fragment khi chuyển qua fragment bằng bottomNav.
     * Tất cả các fragment được khởi tạo cùng lúc, có cùng trạng thái.
     */
    fun controlFragmentWithSaveState() {
        val homeFragment = HomeFragment()
        val listFileFragment = ListFileFragment()
        val settingFragment = SettingFragment()
        var activeFragment: Fragment = homeFragment
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container_fragment, homeFragment, "homeFragment")
            add(R.id.container_fragment, listFileFragment, "homeFragment").hide(listFileFragment)
            add(R.id.container_fragment, settingFragment, "homeFragment").hide(settingFragment)
        }.commit()

        b.bottomNav.setOnNavigationItemSelectedListener {
            when (it.title.toString()) {
                "Home" -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                }

                "List file" -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(listFileFragment).commit()
                    activeFragment = listFileFragment
                }
                "Setting" -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(settingFragment).commit()
                    activeFragment = settingFragment
                }
            }

            true
        }
    }


    /**
     * Không lưu giữ trạng thái khi chuyển qua lại các fragment,
     */
    fun controlFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment, HomeFragment()).commit()

        b.bottomNav.setOnNavigationItemSelectedListener {
            when (it.title.toString()) {
                "Home" -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container_fragment, HomeFragment()).commit()
                }
                "List file" -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container_fragment, ListFileFragment()).commit()
                }
                "Setting" -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container_fragment, SettingFragment()).commit()
                }
            }

            true
        }
    }


}

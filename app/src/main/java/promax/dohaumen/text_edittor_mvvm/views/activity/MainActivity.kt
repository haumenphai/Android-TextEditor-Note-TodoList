package promax.dohaumen.text_edittor_mvvm.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityMainBinding
//import promax.dohaumen.text_edittor_mvvm.databinding.ActivityMainBinding
import promax.dohaumen.text_edittor_mvvm.views.fragment.HomeFragment
import promax.dohaumen.text_edittor_mvvm.views.fragment.ListFileFragment
import promax.dohaumen.text_edittor_mvvm.views.fragment.SettingFragment

/**
 * Lưu giữ trạng thái của fragment khi chuyển qua fragment bằng bottomNav.
 * Tất cả các fragment được khởi tạo cùng lúc, có cùng trạng thái
 */
class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val listFileFragment = ListFileFragment()
    private val settingFragment = SettingFragment()
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

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




}

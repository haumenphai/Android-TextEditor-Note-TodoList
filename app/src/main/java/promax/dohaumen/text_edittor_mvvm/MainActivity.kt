package promax.dohaumen.text_edittor_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment, HomeFragment()).commit()

        b.bottomNav.setOnNavigationItemSelectedListener {
            when (it.title.toString()) {
                "Home" ->
                    supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, HomeFragment()).commit()
                "List file" ->
                    supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, ListFileFragment()).commit()
                "Setting" ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, SettingFragment()).commit()
            }

            true
        }


    }


}

package promax.dohaumen.text_edittor_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import promax.dohaumen.text_edittor_mvvm.data.FileTextDao
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityMainBinding
//import promax.dohaumen.text_edittor_mvvm.databinding.ActivityMainBinding
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.views.HomeFragment
import promax.dohaumen.text_edittor_mvvm.views.ListFileFragment
import promax.dohaumen.text_edittor_mvvm.views.SettingFragment

//import promax.dohaumen.text_edittor_mvvm.views.HomeFragment
//import promax.dohaumen.text_edittor_mvvm.views.ListFileFragment
//import promax.dohaumen.text_edittor_mvvm.views.SettingFragment

class MainActivity : AppCompatActivity() {
    lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

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

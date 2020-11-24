package promax.dohaumen.text_edittor_mvvm.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.views.fragment.ListFileDeletedFragment
import promax.dohaumen.text_edittor_mvvm.views.fragment.ListFileFragment

class ViewListFileDeteledActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list_file_deteled)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_list_file_bi_xoa, ListFileDeletedFragment()).commit()
    }
}
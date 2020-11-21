package promax.dohaumen.text_edittor_mvvm.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import promax.dohaumen.text_edittor_mvvm.databinding.ActivityViewFileBinding
import promax.dohaumen.text_edittor_mvvm.models.FileText

class ViewFileActivity : AppCompatActivity() {
    lateinit var b: ActivityViewFileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityViewFileBinding.inflate(layoutInflater)
        setContentView(b.root)

        val fileText: FileText? = intent.getParcelableExtra("fileText")

        b.editViewFile.setText(fileText!!.content)

    }
}
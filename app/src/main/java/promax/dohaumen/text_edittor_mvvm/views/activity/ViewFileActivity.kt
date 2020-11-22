package promax.dohaumen.text_edittor_mvvm.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        Log.d("AAA","oncreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("AAA","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("AAA","onresum")
    }


    override fun onPause() {
        super.onPause()
        Log.d("AAA","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("AAA","onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("AAA","onReset")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("AAA","onDestroy")
    }


}
package promax.dohaumen.text_edittor_mvvm.views.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_setting.*
import promax.dohaumen.text_edittor_mvvm.views.activity.MainActivity
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentSettingBinding
import promax.dohaumen.text_edittor_mvvm.views.activity.ViewListFileDeteledActivity
import java.lang.Exception

class SettingFragment: Fragment() {
    lateinit var b: FragmentSettingBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentSettingBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        return b.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.tvXemCacFileDaBiXoa.setOnClickListener {
            startActivity(Intent(mainActivity, ViewListFileDeteledActivity::class.java))
        }
        b.tvXemCacFileDaBiXoa.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    (v as TextView).setTextColor(Color.RED)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    (v as TextView).setTextColor(Color.parseColor("#515151"))
                    true
                }
            }

            false
        }
        b.tvLienHeFacebook.setOnClickListener {
            val uri = Uri.parse("https://www.facebook.com/profile.php?id=100003819878345")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(intent)
            } catch (e: Exception) {}
        }
    }
}
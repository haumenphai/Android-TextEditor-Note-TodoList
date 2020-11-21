package promax.dohaumen.text_edittor_mvvm.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import promax.dohaumen.text_edittor_mvvm.views.activity.MainActivity
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentSettingBinding

class SettingFragment: Fragment() {
    lateinit var b: FragmentSettingBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentSettingBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        return b.root
    }
}
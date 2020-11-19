package promax.dohaumen.text_edittor_mvvm.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import promax.dohaumen.text_edittor_mvvm.MainActivity
import promax.dohaumen.text_edittor_mvvm.databinding.FragmentListFileBinding

class ListFileFragment: Fragment() {
    lateinit var b: FragmentListFileBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentListFileBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        return b.root
    }
}
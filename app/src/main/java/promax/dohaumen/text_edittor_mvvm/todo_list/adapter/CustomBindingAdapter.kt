package promax.dohaumen.text_edittor_mvvm.todo_list.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import promax.dohaumen.text_edittor_mvvm.R

@BindingAdapter("android:customBackground")
fun setCustomPrimaryBackground(v: View, isCompleted: Boolean) {
    if (!isCompleted) {
        v.setBackgroundResource(R.drawable.rippler_brow)
    } else {
        v.setBackgroundResource(R.drawable.ripper_btn_red)
    }
}

package promax.dohaumen.text_edittor_mvvm.todo_list.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import promax.dohaumen.text_edittor_mvvm.R

@BindingAdapter("promax:customBackground")
fun setCustomPrimaryBackground(v: View, isCompleted: Boolean) {
    if (!isCompleted) {
        v.setBackgroundResource(R.drawable.rippler_item_brow)
    } else {
        v.setBackgroundResource(R.drawable.rippler_item_completed)
    }
}

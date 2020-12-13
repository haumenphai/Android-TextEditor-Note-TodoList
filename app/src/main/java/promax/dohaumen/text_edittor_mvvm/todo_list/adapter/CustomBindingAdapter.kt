package promax.dohaumen.text_edittor_mvvm.todo_list.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import promax.dohaumen.text_edittor_mvvm.MyApplication
import promax.dohaumen.text_edittor_mvvm.R

@BindingAdapter("promax:isSelectedMode", "promax:isSelectd", "promax:isCompleted")
fun setCustomPrimaryBackground(v: View, isSelectedMode: Boolean, isSelected: Boolean, isCompleted: Boolean) {
    if (isSelectedMode) {
        if (isSelected) {
            v.setBackgroundColor(ContextCompat.getColor(MyApplication.context, R.color.red_500))
        } else {
            v.setBackgroundResource(R.drawable.rippler_item_brow)
        }
    } else {
        if (isCompleted) {
            v.setBackgroundResource(R.drawable.rippler_item_completed)
        } else {
            v.setBackgroundResource(R.drawable.rippler_item_brow)
        }
    }

}

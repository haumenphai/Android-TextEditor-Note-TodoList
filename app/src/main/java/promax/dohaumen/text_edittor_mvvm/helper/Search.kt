package promax.dohaumen.text_edittor_mvvm.helper

import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.hmp.dev.heler.StringHelper
import java.util.*
import kotlin.collections.ArrayList

object Search {
    fun searchTask(listInput: List<Task>, keySearch: String, onComplete: (list: List<Task>) -> Unit) {
        if (keySearch.trim() == "") {
            onComplete(listInput)
            return
        }
        object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val FilteredArrList: ArrayList<Task> = ArrayList()
                val c1 = constraint.toString().toLowerCase(Locale.ROOT)

                listInput.forEach {
                    val fileName: String = it.name
                    if (fileName == c1
                        || getTuVietTat(fileName).contains(c1)
                        || fileName.contains(c1)
                        || StringHelper.loaiBoDauTiengViet(fileName).contains(c1)
                    ) {
                        FilteredArrList.add(it)
                    }
                }
                results.count = FilteredArrList.size
                results.values = FilteredArrList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val list: List<Task> = results!!.values as List<Task>
                onComplete(list)
            }
        }.filter(keySearch)
    }


}
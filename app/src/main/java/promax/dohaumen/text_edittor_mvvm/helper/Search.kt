package promax.dohaumen.text_edittor_mvvm.helper

import android.widget.Filter
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.hmp.dev.heler.StringHelper
import java.util.*
import kotlin.collections.ArrayList

object Search {
    fun searchTask(listInput: List<Task>, keySearch: String, onPreSearch:() -> Unit, onComplete: (list: List<Task>) -> Unit) {
        onPreSearch()
        if (keySearch.trim() == "") {
            onComplete(listInput)
            return
        }

        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val FilteredArrList: ArrayList<Task> = ArrayList()
                val c1 = constraint.toString().toLowerCase(Locale.ROOT)

                listInput.forEach {
                    val fileName: String = it.name.toLowerCase(Locale.ROOT)
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
        }.filter(keySearch.toLowerCase(Locale.ROOT))
    }


    fun searchFileText(listInput: List<FileText>, keySearch: String, onPreSearch:() -> Unit, onComplete: (list: List<FileText>) -> Unit) {
        onPreSearch()
        if (keySearch.trim() == "") {
            onComplete(listInput)
            return
        }

        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val FilteredArrList: ArrayList<FileText> = ArrayList()
                val c1 = constraint.toString().toLowerCase(Locale.ROOT)

                listInput.forEach {
                    val fileName: String = it.name.toLowerCase(Locale.ROOT)
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
                val list: List<FileText> = results!!.values as List<FileText>
                onComplete(list)
            }
        }.filter(keySearch.toLowerCase(Locale.ROOT))
    }

}
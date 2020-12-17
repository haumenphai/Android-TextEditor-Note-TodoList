package promax.dohaumen.text_edittor_mvvm.helper

import android.widget.Filter
import kotlinx.coroutines.*
import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.dohaumen.text_edittor_mvvm.todo_list.data.Task
import promax.hmp.dev.heler.StringHelper
import java.util.*
import kotlin.collections.ArrayList

object Search {


    private var job1: Job? = null
    fun searchTaskV2(listInput: List<Task>, keySearch: String, onPreSearch:() -> Unit, onComplete: (list: List<Task>) -> Unit) {
        onPreSearch()
        if (keySearch.trim() == "") {
            onComplete(listInput)
            return
        }
        job1?.cancel()
        job1 = GlobalScope.launch {
            val key = keySearch.toLowerCase(Locale.ROOT)
            val result: MutableList<Task> = mutableListOf()
            listInput.forEach {
                val fileName: String = it.name.toLowerCase(Locale.ROOT)
                if (fileName == key
                    || getTuVietTat(fileName).contains(key)
                    || fileName.contains(key)
                    || StringHelper.loaiBoDauTiengViet(fileName).contains(key)
                ) {
                    result.add(it)
                }
            }
            withContext(Dispatchers.Main) {
                onComplete(result)
            }
        }
    }

    private var job2: Job? = null
    fun searchFileTextV2(listInput: List<FileText>, keySearch: String, onPreSearch:() -> Unit, onComplete: (list: List<FileText>) -> Unit) {
        onPreSearch()
        if (keySearch.trim() == "") {
            onComplete(listInput)
            return
        }
        job2?.cancel()
        job2 = GlobalScope.launch {
            val key = keySearch.toLowerCase(Locale.ROOT)
            val result: MutableList<FileText> = mutableListOf()
            listInput.forEach {
                val fileName: String = it.name.toLowerCase(Locale.ROOT)
                if (fileName == key
                    || getTuVietTat(fileName).contains(key)
                    || fileName.contains(key)
                    || StringHelper.loaiBoDauTiengViet(fileName).contains(key)
                ) {
                    result.add(it)
                }
            }
            withContext(Dispatchers.Main) {
                onComplete(result)
            }
        }
    }


    // search in main thread
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


    // search in main thread
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
package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.data.file_text.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.models.FileText

class ListFileDeletedFragmentViewModel: ViewModel() {
    private val fileTexts: MutableLiveData<List<FileText>> = MutableLiveData()
    fun getListFileText(): LiveData<List<FileText>> = fileTexts

    init {
        FileTextDatabase.getINSTANCE().dao().getLiveData(true).observeForever {
            it.reverse()
            fileTexts.value = it
        }
    }


    fun deleteListChecked(list: List<FileText>, onComplete:() -> Unit = {}) {
        list.forEach { fileText ->
            FileTextDatabase.getINSTANCE().dao().delete(fileText)
        }
        onComplete()
    }

    fun restoreListChecked(list: List<FileText>, onComplete: () -> Unit = {}) {
        list.forEach { fileText ->
            fileText.isDeleted = false
            FileTextDatabase.getINSTANCE().dao().update(fileText)
            onComplete()
        }
    }







}
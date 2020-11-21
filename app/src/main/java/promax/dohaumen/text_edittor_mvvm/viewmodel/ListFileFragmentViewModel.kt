package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.models.FileText

class ListFileFragmentViewModel: ViewModel() {

    fun getList(): LiveData<List<FileText>> {
        return FileTextDatabase.getINSTANCE().dao().liveData
    }

}
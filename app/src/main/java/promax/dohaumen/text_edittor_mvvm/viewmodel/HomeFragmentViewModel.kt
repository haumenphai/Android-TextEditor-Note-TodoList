package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.data.sharef.HomeFragmentData
import promax.dohaumen.text_edittor_mvvm.models.FileText

class HomeFragmentViewModel: ViewModel() {
    private var textTemp: MutableLiveData<String> = MutableLiveData()
    private var isEditTextEnable: MutableLiveData<Boolean> = MutableLiveData()

    init {
        textTemp.value  = HomeFragmentData.getTextTemp()
        isEditTextEnable.value = false
    }

    fun getTextTemp(): LiveData<String> = textTemp
    fun isEditTextEnable(): LiveData<Boolean> = isEditTextEnable

    fun setOnItemEditClick() {
        isEditTextEnable.value = !isEditTextEnable.value!!
    }

    fun deleteTextTemp() {
        textTemp.value = ""
    }

    lateinit var onSaveFileTextComple:(mess: String, isSuccess: Boolean) -> Unit



    fun saveFileText(fileName: String, content: String) {
        if (fileName.isEmpty()) {
            onSaveFileTextComple("Tên file không được để trống!", false)
        } else {
            val fileText = FileText(fileName, content, "fake date")
            FileTextDatabase.getINSTANCE().dao().insert(fileText)
            onSaveFileTextComple("Lưu file thành công", true)
        }
    }

    fun saveTextTemp(text: String) {
        HomeFragmentData.setTextTemp(text)
    }




}
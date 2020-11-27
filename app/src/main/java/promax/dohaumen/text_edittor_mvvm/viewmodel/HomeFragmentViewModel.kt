package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.MyApplication
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.data.sharef.HomeFragmentData
import promax.dohaumen.text_edittor_mvvm.helper.getCurrentDate12h
import promax.dohaumen.text_edittor_mvvm.models.FileText

class HomeFragmentViewModel: ViewModel() {
    private var textTemp: MutableLiveData<String> = MutableLiveData()
    private var textSize: MutableLiveData<Int> = MutableLiveData()
    private var isEditTextEnable: MutableLiveData<Boolean> = MutableLiveData()

    init {
        textTemp.value  = HomeFragmentData.getTextTemp()
        textSize.value  = HomeFragmentData.getTextSize()
        isEditTextEnable.value = false
    }

    fun getTextTemp(): LiveData<String> = textTemp
    fun deleteTextTemp() { textTemp.value = ""}
    fun saveTextTemp(text: String) = HomeFragmentData.setTextTemp(text)

    fun isEditTextEnable(): LiveData<Boolean> = isEditTextEnable

    fun setItemEditEnableClick() {
        isEditTextEnable.value = !isEditTextEnable.value!!
    }



    fun getTextSize(): LiveData<Int> = textSize
    fun saveTextSize() = HomeFragmentData.setTextSize(textSize.value!!)

    lateinit var onTextSizeChangeComple:(currentValue: Int) -> Unit
    fun setTextSizeReduce() {
        textSize.value = textSize.value?.minus(1) // -1
        onTextSizeChangeComple(textSize.value!!)
    }
    fun setTextSizeIncrease() {
        textSize.value = textSize.value?.plus(1) // +1
        onTextSizeChangeComple(textSize.value!!)
    }



    lateinit var onSaveFileTextComple:(mess: String, isSuccess: Boolean) -> Unit
    fun saveFileText(fileName: String, content: String) {
        if (fileName.isEmpty()) {
            onSaveFileTextComple(MyApplication.context.getString(R.string.mess_file_name_must_not_emtpy), false)
        } else {
            val fileText = FileText(fileName, content, "fake date")
            fileText.dateCreate = getCurrentDate12h()
            fileText.lastEditedDate = fileText.dateCreate
            FileTextDatabase.getINSTANCE().dao().insert(fileText)
            onSaveFileTextComple(MyApplication.context.getString(R.string.mess_save_file_successfully), true)
        }
    }






}
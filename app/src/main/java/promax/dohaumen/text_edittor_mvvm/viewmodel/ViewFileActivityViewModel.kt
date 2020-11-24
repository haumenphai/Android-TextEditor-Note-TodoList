package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.data.FileTextRes
import promax.dohaumen.text_edittor_mvvm.data.sharef.HomeFragmentData
import promax.dohaumen.text_edittor_mvvm.models.FileText

class ViewFileActivityViewModel: ViewModel() {
    private var textSize: MutableLiveData<Int> = MutableLiveData()
    private var isEditTextEnable: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var fileText: FileText

    init {
        textSize.value  = HomeFragmentData.getTextSize()
        isEditTextEnable.value = false
    }

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

    fun saveFileText(newContent: String) {
        fileText.content = newContent
        FileTextRes.update(fileText)
    }

}
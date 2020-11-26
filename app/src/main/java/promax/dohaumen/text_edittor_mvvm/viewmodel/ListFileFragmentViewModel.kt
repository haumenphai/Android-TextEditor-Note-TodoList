package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.data.FileTextRes
import promax.dohaumen.text_edittor_mvvm.helper.getCurentDate24h
import promax.dohaumen.text_edittor_mvvm.helper.getCurrentDate12h
import promax.dohaumen.text_edittor_mvvm.models.FileText

class ListFileFragmentViewModel: ViewModel() {

    private val fileTexts: MutableLiveData<List<FileText>> = MutableLiveData()
    fun getListFileText(): LiveData<List<FileText>> = fileTexts

    init {
        FileTextDatabase.getINSTANCE().dao().getLiveData(false).observeForever {
            it.reverse()
            fileTexts.value = it
        }
    }


    lateinit var onSaveFileTextComple:(mess: String, isSuccess: Boolean) -> Unit
    fun addFileText(fileName: String, content: String) {
        if (fileName.isEmpty()) {
            onSaveFileTextComple("Tên file không được để trống!", false)
        } else {
            val fileText = FileText(fileName, content, "fake date")
            fileText.dateCreate = getCurrentDate12h()
            fileText.lastEditedDate = fileText.dateCreate
            FileTextDatabase.getINSTANCE().dao().insert(fileText)
            onSaveFileTextComple("Lưu file thành công", true)
        }
    }

    fun deleteListChecked(list: List<FileText>, onComplete:() -> Unit = {}) {
        list.forEach { fileText ->
            fileText.isDeleted = true
            fileText.dateDeteled = getCurrentDate12h()
            FileTextDatabase.getINSTANCE().dao().update(fileText)
        }
        onComplete()
    }


    var onRenameComple:(mess: String, isSucssec: Boolean) -> Unit = { mess, isSucssec -> }
    fun reNameFile(newName: String, fileText: FileText) {
        if (newName != "") {
            fileText.name = newName
            FileTextRes.update(fileText)
            onRenameComple("Đổi tên thành công", true)
        } else {
            onRenameComple("Tên file mới không được để trống", false)
        }
    }

    lateinit var onSetPasswordComplete:(mess: String, isSucssec: Boolean) -> Unit
    fun setPasswordToFile(fileText: FileText, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            onSetPasswordComplete("Mật khẩu và xác nhận lại mật khẩu phải trùng nhau", false)
        } else {
            fileText.password = password
            FileTextDatabase.getINSTANCE().dao().update(fileText)
            onSetPasswordComplete("Đặt mật khẩu thành công", true)
        }
    }

    lateinit var onRePasswordComplete:(mess: String, isSucssec: Boolean) -> Unit
    fun rePassword(fileText: FileText, oldpassword: String, newPassword: String) {
        if (fileText.password != oldpassword) {
            onRePasswordComplete("Mật khẩu cũ sai!", false)
        }  else {
            fileText.password = newPassword
            FileTextDatabase.getINSTANCE().dao().update(fileText)
            onRePasswordComplete("Đổi mật khẩu thành công", true)
        }
    }

    lateinit var onDeletePasswordComplete:(mess: String, isSucssec: Boolean) -> Unit
    fun deletePassWord(fileText: FileText, password: String) {
        if (fileText.password == password) {
            fileText.password = null
            FileTextDatabase.getINSTANCE().dao().update(fileText)
            onDeletePasswordComplete("Xóa mật khẩu thành công!", true)
        } else {
            onRePasswordComplete("Mật khẩu sai", false)
        }
    }









}
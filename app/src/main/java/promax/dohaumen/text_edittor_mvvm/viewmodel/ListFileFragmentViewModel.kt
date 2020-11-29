package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.MyApplication
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.data.FileTextRes
import promax.dohaumen.text_edittor_mvvm.helper.getCurrentDate12h
import promax.dohaumen.text_edittor_mvvm.helper.getString
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
            onSaveFileTextComple(getString(R.string.mess_file_name_must_not_emtpy), false)
        } else {
            val fileText = FileText(fileName, content, getCurrentDate12h())
            fileText.lastEditedDate = fileText.dateCreate
            FileTextDatabase.getINSTANCE().dao().insert(fileText)
            onSaveFileTextComple(getString(R.string.mess_save_file_successfully), true)
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

    lateinit var listDeleteUndo: List<FileText>
    fun undoDelete() {
        listDeleteUndo.forEach { fileText ->
            fileText.isDeleted = false
            fileText.dateDeteled = ""
            FileTextDatabase.getINSTANCE().dao().update(fileText)
        }
    }


    var onRenameComple:(mess: String, isSucssec: Boolean) -> Unit = { mess, isSucssec -> }
    fun reNameFile(newName: String, fileText: FileText) {
        if (newName != "") {
            fileText.name = newName
            FileTextRes.update(fileText)
            onRenameComple(getString(R.string.mess_rename_successfully), true)
        } else {
            onRenameComple(getString(R.string.mess_file_name_must_not_emtpy), false)
        }
    }

    lateinit var onSetPasswordComplete:(mess: String, isSucssec: Boolean) -> Unit
    fun setPasswordToFile(fileText: FileText, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            onSetPasswordComplete(getString(R.string.mess_setup_password_faild), false)
        } else {
            fileText.password = password
            FileTextDatabase.getINSTANCE().dao().update(fileText)
            onSetPasswordComplete(getString(R.string.mess_setup_password_succseccfully), true)
        }
    }

    lateinit var onChangePasswordComplete:(mess: String, isSucssec: Boolean) -> Unit
    fun changePassword(fileText: FileText, oldpassword: String, newPassword: String) {
        if (fileText.password != oldpassword) {
            onChangePasswordComplete(getString(R.string.mess_old_password_is_not_correct), false)
        }  else {
            fileText.password = newPassword
            FileTextDatabase.getINSTANCE().dao().update(fileText)
            onChangePasswordComplete(getString(R.string.mess_changed_password_successfully), true)
        }
    }

    lateinit var onDeletePasswordComplete:(mess: String, isSucssec: Boolean) -> Unit
    fun deletePassWord(fileText: FileText, password: String) {
        if (fileText.password == password) {
            fileText.password = null
            FileTextDatabase.getINSTANCE().dao().update(fileText)
            onDeletePasswordComplete(getString(R.string.mess_delete_password_succsecfully), true)
        } else {
            onDeletePasswordComplete(getString(R.string.mess_delete_password_faild), false)
        }
    }









}
package promax.dohaumen.text_edittor_mvvm.data

import promax.dohaumen.text_edittor_mvvm.helper.getCurrentDate12h
import promax.dohaumen.text_edittor_mvvm.models.FileText

object FileTextRes {
    fun update(fileText: FileText) {
        fileText.lastEditedDate = getCurrentDate12h()
        FileTextDatabase.getINSTANCE().dao().update(fileText)
    }
}
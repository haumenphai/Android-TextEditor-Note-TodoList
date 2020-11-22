package promax.dohaumen.text_edittor_mvvm.data

import promax.dohaumen.text_edittor_mvvm.models.FileText

object FileTextRes {
    fun update(fileText: FileText) {
        // todo: cập nhật ngày chỉnh sửa gần nhất
        FileTextDatabase.getINSTANCE().dao().update(fileText)
    }
}
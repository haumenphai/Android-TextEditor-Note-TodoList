package promax.dohaumen.text_edittor_mvvm.helper

import promax.dohaumen.text_edittor_mvvm.models.FileText
import promax.hmp.dev.heler.StringHelper
import java.util.*

fun demSoTu(string: String): Int {
    if (string == "") return 0
    return string.split(" ").size
}
fun getTuVietTat(s: String):String {
    val arr = s.trim().split(" ")
    var result = ""
    for (i in 0..arr.size-1) {
        result += arr[i].substring(0,1)
    }
    return result
}



fun searchFileText(list: List<FileText>, keySearch: String): List<FileText> {
    val result = ArrayList<FileText>()

    for (i in 0..list.size-1) {
        val fileName = list[i].name.toLowerCase(Locale.ROOT)

        if (fileName == keySearch || getTuVietTat(fileName).contains(keySearch) || fileName.contains(keySearch)
            || StringHelper.loaiBoDauTiengViet(fileName).contains(keySearch)
        ) {
            result.add(list[i])
        }
    }
    return result
}
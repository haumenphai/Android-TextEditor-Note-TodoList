package promax.dohaumen.text_edittor_mvvm.helper

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val date = Date()
    val simpleDateFormat = SimpleDateFormat("[E] dd-MM-yyyy k:mm:ss a")
    return  simpleDateFormat.format(date)
}
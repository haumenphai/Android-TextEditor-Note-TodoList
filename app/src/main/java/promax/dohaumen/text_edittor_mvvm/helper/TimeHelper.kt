package promax.dohaumen.text_edittor_mvvm.helper

import java.text.SimpleDateFormat
import java.util.*

// định dạng 12h
fun getCurrentDate12h(): String {
    val date = Date()
    val simpleDateFormat = SimpleDateFormat("[E] dd/MM/yyyy hh:mm:ss aa")
    var result = simpleDateFormat.format(date)
    result = result.replace("SA", "AM")
    result = result.replace("CH", "PM")
    return result
}

// định dạng 24h
fun getCurentDate24h(): String {
    val date = Date()
    val simpleDateFormat = SimpleDateFormat("[E] dd/MM/yyyy k:mm:ss")
    return  simpleDateFormat.format(date)
}


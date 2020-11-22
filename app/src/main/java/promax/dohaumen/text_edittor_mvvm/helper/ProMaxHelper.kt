package promax.dohaumen.text_edittor_mvvm.helper

fun demSoTu(string: String): Int {
    if (string == "") return 0
    return string.split(" ").size
}
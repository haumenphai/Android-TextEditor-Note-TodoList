package promax.dohaumen.text_edittor_mvvm.helper

import android.widget.EditText
import android.widget.TextView

fun setTextLineNumber(tvLineNumber: TextView, editText: EditText) {
    tvLineNumber.text = ""
    val lineCount: Int = editText.lineCount
    var lineNumber = 1
    var textLine = ""

    for (i in 0 until lineCount) {
        if (i == 0) {
            textLine += lineNumber.toString()
            ++lineNumber
        } else if (editText.text.toString()
                .get(editText.layout.getLineStart(i) - 1) == '\n'
        ) {
            textLine += lineNumber.toString()
            ++lineNumber
        }
        textLine += "\n"

    }
    try {
        textLine = textLine.substring(0, textLine.length - 1)
    } catch (e: Exception) {}
    tvLineNumber.text = textLine
}
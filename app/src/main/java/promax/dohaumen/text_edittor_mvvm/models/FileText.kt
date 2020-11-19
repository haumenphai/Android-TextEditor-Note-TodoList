package promax.dohaumen.text_edittor_mvvm.models

import androidx.room.Entity

@Entity
class FileText {
    lateinit var name: String
    lateinit var content: String
    lateinit var date: String

    constructor()
    constructor(name: String, content: String, date: String) {
        this.name = name
        this.content = content
        this.date = date
    }


}
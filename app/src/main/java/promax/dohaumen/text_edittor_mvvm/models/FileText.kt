package promax.dohaumen.text_edittor_mvvm.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
public class FileText {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var content: String =""
    var date: String =""

    constructor()
    @Ignore
    constructor(name: String, content: String, date: String) {
        this.name = name
        this.content = content
        this.date = date
    }


}
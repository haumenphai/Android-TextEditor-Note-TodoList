package promax.dohaumen.text_edittor_mvvm.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Task {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var describe: String = ""
    var dateCreated: String = ""
    var dateCompleted: String = ""
    var dateDeleted: String = ""


    var isCompleted: Boolean = false
    var isDeleted: Boolean = false

    constructor()

    @Ignore
    constructor(name: String, describe: String) {
        this.name = name
        this.describe = describe
    }
}

package promax.dohaumen.text_edittor_mvvm.todo_list.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Task: Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var describe: String = ""
    var dateCreated: String = ""
    var dateCompleted: String = ""
    var dateDeleted: String = ""


    var isChecked: Boolean = false  // task is completed
    var isDeleted: Boolean = false

    @Ignore
    var isSelected = false // select for edit


    constructor()

    @Ignore
    constructor(name: String, describe: String) {
        this.name = name
        this.describe = describe
    }
}

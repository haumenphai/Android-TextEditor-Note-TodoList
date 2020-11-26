package promax.dohaumen.text_edittor_mvvm.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class FileText : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var content: String = ""
    var dateCreate: String =   ""
    var lastEditedDate: String = ""
    var isDeleted: Boolean = false
    var dateDeteled: String = ""
    var password: String? = null

    @Ignore
    var isCheck = false


    constructor() {}
    @Ignore
    constructor(name: String, content: String, date: String) {
        this.name = name
        this.content = content
        this.dateCreate = date
        this.lastEditedDate = date
    }

    @Ignore
    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString().toString()
        content = parcel.readString().toString()
        dateCreate = parcel.readString().toString()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(content)
        parcel.writeString(dateCreate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FileText> {
        override fun createFromParcel(parcel: Parcel): FileText {
            return FileText(parcel)
        }

        override fun newArray(size: Int): Array<FileText?> {
            return arrayOfNulls(size)
        }
    }


}
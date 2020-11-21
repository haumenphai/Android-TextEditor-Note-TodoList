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
    var content: String =""
    var date: String =""


    constructor() {}
    @Ignore
    constructor(name: String, content: String, date: String) {
        this.name = name
        this.content = content
        this.date = date
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString().toString()
        content = parcel.readString().toString()
        date = parcel.readString().toString()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(content)
        parcel.writeString(date)
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
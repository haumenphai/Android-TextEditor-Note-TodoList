package promax.dohaumen.text_edittor_mvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ItemFileTextBinding
import promax.dohaumen.text_edittor_mvvm.models.FileText
@SuppressLint("SetTextI18n")

class FileTextAdapter: RecyclerView.Adapter<FileTextAdapter.Holder>() {
    private var list: List<FileText> = mutableListOf()

    lateinit var onClickITem:(fileText: FileText) -> Unit
    lateinit var onLongClickITem:(fileText: FileText) -> Unit

    fun setList(list: List<FileText>) {
        this.list = list
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_text, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val fileText = list[position]
        holder.b.tvName.text = fileText.name
        holder.b.tvDate.text = fileText.date
        holder.b.tvSTT.text  = "${position+1}"

    }

    override fun getItemCount() = list.size

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val b = ItemFileTextBinding.bind(itemView)
        init {
            b.background.setOnClickListener {
                onClickITem(list[adapterPosition])
            }
            b.background.setOnLongClickListener {
                onClickITem(list[adapterPosition])
                true
            }
        }
    }
}
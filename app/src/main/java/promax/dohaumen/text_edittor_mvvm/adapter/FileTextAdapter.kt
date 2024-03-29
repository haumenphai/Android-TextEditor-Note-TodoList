package promax.dohaumen.text_edittor_mvvm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import promax.dohaumen.text_edittor_mvvm.R
import promax.dohaumen.text_edittor_mvvm.databinding.ItemFileTextBinding
import promax.dohaumen.text_edittor_mvvm.models.FileText
@SuppressLint("SetTextI18n")

class FileTextAdapter: RecyclerView.Adapter<FileTextAdapter.Holder>() {
    private var list: List<FileText> = mutableListOf()
    private lateinit var context: Context

    lateinit var onClickITem:(fileText: FileText) -> Unit
    lateinit var onLongClickITem:(fileText: FileText) -> Unit

    var hienThiItemListFileBiXoa = false

    fun setList(list: List<FileText>) {
        this.list = list
        notifyDataSetChanged()
    }
    fun getList() = list

    fun setSwapCheckItem(fileText: FileText) {
        fileText.isCheck = !fileText.isCheck
        notifyDataSetChanged()
    }

    fun getListChecked() = list.filter { fileText -> fileText.isCheck }

    fun setCheckAll() = list.forEach { fileText ->
        run {
            fileText.isCheck = true;
            notifyDataSetChanged()
        }
    }
    fun unCheckAll() = list.forEach { fileText ->
        run {
            fileText.isCheck = false
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_text, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val fileText = list[position]
        holder.b.tvName.text = fileText.name
        holder.b.tvDate.text = fileText.dateCreate
        holder.b.tvSTT.text  = "${position+1}"

        if (fileText.isCheck) {
            holder.b.background.setBackgroundColor(ContextCompat.getColor(context, R.color.red_500))
        } else {
            holder.b.background.setBackgroundResource(R.drawable.rippler_item_brow)
        }

        if (hienThiItemListFileBiXoa) {
            holder.b.tvDate.text = "${context.getString(R.string.date_delete)} ${fileText.dateDeteled}"
        }

        if (fileText.password != null) {
            holder.b.imgFileIsLooked.visibility = View.VISIBLE
        } else {
            holder.b.imgFileIsLooked.visibility = View.GONE
        }

    }

    override fun getItemCount() = list.size

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val b = ItemFileTextBinding.bind(itemView)
        init {
            b.background.setOnClickListener {
                onClickITem(list[layoutPosition])
            }
            b.background.setOnLongClickListener {
                onLongClickITem(list[layoutPosition])
                true
            }
        }
    }
}
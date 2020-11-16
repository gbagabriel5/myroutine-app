package com.gba.myroutine.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gba.myroutine.R
import com.gba.myroutine.model.Tarefa
import com.gba.myroutine.ui.listener.TarefasListener
import kotlinx.android.synthetic.main.card_tarefa.view.*

class TarefasAdapter : RecyclerView.Adapter<TarefasAdapter.GuestViewHolder>() {

    private var guestList: List<Tarefa> = arrayListOf()
    private lateinit var mListener: TarefasListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.card_tarefa, parent,false)
        return GuestViewHolder(item, mListener)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(guestList[position])
    }

    override fun getItemCount(): Int {
        return guestList.count()
    }

    fun updateGuests(list: List<Tarefa>) {
        guestList = list
        notifyDataSetChanged()

    }

    fun attachListener(listener: TarefasListener) {
        mListener = listener
    }

    class GuestViewHolder(itemView: View,
                          private var listener: TarefasListener) : RecyclerView.ViewHolder(itemView) {

        fun bind(tarefa: Tarefa) {
            itemView.txtTitulo.text = tarefa.titulo
            itemView.txtDesc.text = tarefa.descricao
            itemView.txtData.text = tarefa.data
            itemView.rootView.setOnClickListener {
                listener.onClick(tarefa.id)
            }
//            itemView.txtName.setOnLongClickListener {
//                AlertDialog.Builder(itemView.context)
//                    .setTitle(R.string.remocao_convidado)
//                    .setMessage(R.string.deseja_remover)
//                    .setPositiveButton(R.string.remover) { dialog, wich ->
//                        listener.onDelete(guest.id)
//                    }
//                    .setNeutralButton(R.string.cancelar,null)
//                    .show()
//                true
//            }
        }
    }
}

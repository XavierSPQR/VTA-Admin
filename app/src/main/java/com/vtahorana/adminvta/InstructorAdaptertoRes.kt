package com.vtahorana.adminvta

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class InstructorAdaptertoRes(private val context: Context, private var dataList: List<InstructorDataClass>):
    RecyclerView.Adapter<InsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.instructor_item, parent, false)
        return InsViewHolder(view)
    }

    override fun onBindViewHolder(holder: InsViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].InsImage)
            .into(holder.cImage)
        holder.cName.text = dataList[position].InsName
        holder.CType.text = dataList[position].InsCourse

        holder.recCard.setOnClickListener{
            val intent = Intent(context, expandInstructorDetails::class.java)
            intent.putExtra("Image", dataList[holder.adapterPosition].InsImage)
            intent.putExtra("Exp", dataList[holder.adapterPosition].InsExp)
            intent.putExtra("Qual", dataList[holder.adapterPosition].InsQualification)
            intent.putExtra("Name", dataList[holder.adapterPosition].InsName)
            intent.putExtra("Course", dataList[holder.adapterPosition].InsCourse)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}
class InsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cImage: ImageView
    var cName: TextView
    var CType: TextView
    var recCard: CardView
    init {
        cImage = itemView.findViewById(R.id.imgviewCourseImage)
        cName = itemView.findViewById(R.id.txtInsName)
        CType=itemView.findViewById(R.id.txtInsCourse)
        recCard = itemView.findViewById(R.id.insCard)
    }
}

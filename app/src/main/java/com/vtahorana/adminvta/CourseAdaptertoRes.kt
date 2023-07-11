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

class CourseAdaptertoRes(private val context: Context, private var dataList: List<CourseDataClass>):RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(dataList[position].CourseImage)
            .into(holder.cImage)
        holder.cName.text = dataList[position].Name
        holder.CType.text = dataList[position].CourseType
        holder.CFee.text = dataList[position].Fee
        holder.CLevel.text = dataList[position].NvqLevel
        holder.cDuration.text = dataList[position].Duration
        holder.recCard.setOnClickListener {
            val intent = Intent(context, expandCourseDetails::class.java)
            intent.putExtra("Image", dataList[holder.adapterPosition].CourseImage)
            intent.putExtra("Description", dataList[holder.adapterPosition].Description)
            intent.putExtra("Name", dataList[holder.adapterPosition].Name)
            intent.putExtra("Level", dataList[holder.adapterPosition].NvqLevel)
            intent.putExtra("Fee", dataList[holder.adapterPosition].Fee)
            intent.putExtra("Type", dataList[holder.adapterPosition].CourseType)
            intent.putExtra("Duration", dataList[holder.adapterPosition].Duration)
            intent.putExtra("Category", dataList[holder.adapterPosition].CourseCategory)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    fun searchDataList(searchList: List<CourseDataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}
class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cImage: ImageView
    var cName: TextView
    var cDuration :TextView
    var CFee: TextView
    var CLevel: TextView
    var CType: TextView
    var recCard: CardView
    init {
        cImage = itemView.findViewById(R.id.imgviewCourseImage)
        cName = itemView.findViewById(R.id.txtCourseName)
        cDuration = itemView.findViewById(R.id.txtDuration)
        CFee= itemView.findViewById(R.id.txtFee)
        CLevel=itemView.findViewById(R.id.txtNvqLevel)
        CType=itemView.findViewById(R.id.txtType)
        recCard = itemView.findViewById(R.id.recCard)
    }

}


package com.vtahorana.adminvta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vtahorana.adminvta.databinding.ActivityInstructorBinding

class Instructor : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<InstructorDataClass>
    private lateinit var adapter: InstructorAdaptertoRes
    private lateinit var binding: ActivityInstructorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@Instructor, 1)
        binding.InsrecyclerView.layoutManager = gridLayoutManager
        binding.search.clearFocus()

        val builder = AlertDialog.Builder(this@Instructor)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = InstructorAdaptertoRes(this@Instructor, dataList)
        binding.InsrecyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Ins")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(InstructorDataClass::class.java)
                    if (dataClass != null) {
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddInstructor::class.java)
            startActivity(intent)
        }
    }
}
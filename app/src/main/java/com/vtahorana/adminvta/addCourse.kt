package com.vtahorana.adminvta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vtahorana.adminvta.databinding.ActivityAddCourseBinding
import java.util.Locale

class addCourse : AppCompatActivity() {
    private lateinit var context: Context
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<CourseDataClass>
    private lateinit var adapter: CourseAdaptertoRes
    private lateinit var binding: ActivityAddCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@addCourse, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.search.clearFocus()

        val builder = AlertDialog.Builder(this@addCourse)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = CourseAdaptertoRes(this@addCourse, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(CourseDataClass::class.java)
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
            val intent = Intent(this, upload::class.java)
            startActivity(intent)
        }
       binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query: String?): Boolean {
               return false
           }

           override fun onQueryTextChange(newT: String?): Boolean {
               if (newT != null) {
                   searchList(newT)
               }
               return true
           }

       })
    }
    fun searchList(text: String) {
        val searchList = java.util.ArrayList<CourseDataClass>()
        for (dataClass in dataList) {
            if (dataClass.Name?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}














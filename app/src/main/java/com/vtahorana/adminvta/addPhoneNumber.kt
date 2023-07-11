package com.vtahorana.adminvta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vtahorana.adminvta.databinding.ActivityAddPhoneNumberBinding
import com.vtahorana.adminvta.databinding.ActivityExpandInstructorDetailsBinding

class addPhoneNumber : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<phoneNumberDataClass>
    private lateinit var adapter: phoneNumbertoRes
    private lateinit var binding: ActivityAddPhoneNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=ActivityAddPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val gridLayoutManager = GridLayoutManager(this@addPhoneNumber, 1)
        binding.phnrecyclerView.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@addPhoneNumber)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = phoneNumbertoRes(this@addPhoneNumber, dataList)
        binding.phnrecyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Courses")
        dialog.show()
    }
}
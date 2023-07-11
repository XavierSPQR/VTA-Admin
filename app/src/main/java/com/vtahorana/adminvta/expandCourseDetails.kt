package com.vtahorana.adminvta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.vtahorana.adminvta.databinding.ActivityExpandCourseDetailsBinding

class expandCourseDetails : AppCompatActivity() {

    var imageUrl = ""
    private lateinit var binding: ActivityExpandCourseDetailsBinding
    private lateinit var Img:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpandCourseDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            Img= bundle.getString("Image").toString()
            binding.txtEDetailsCourseDesc.text = bundle.getString("Description")
            binding.txtEDetailCourseName.text = bundle.getString("Name")
            binding.txtEDetailsCourseCategory.text = bundle.getString("Category")
            binding.txtEDetailsCourseDuration.text = bundle.getString("Duration")
            binding.txtEDetailsCourseFee.text = bundle.getString("Fee")
            binding.txtEDetailsCourseType.text = bundle.getString("Type")
            binding.txtEDetailsNvqLevel.text = bundle.getString("Level")
            imageUrl = bundle.getString("Image")!!
            Glide.with(this).load(bundle.getString("Image"))
                .into(binding.imgviewEDetailImage)
        }
        binding.btnECourseUpdate.setOnClickListener {
            val intent = Intent(this, upload::class.java)
            intent.putExtra("Image", Img)
            intent.putExtra("Description", binding.txtEDetailsCourseDesc.text)
            intent.putExtra("Name",  binding.txtEDetailCourseName.text)
            intent.putExtra("Level", binding.txtEDetailsNvqLevel.text)
            intent.putExtra("Fee",binding.txtEDetailsCourseFee.text )
            intent.putExtra("Type",binding.txtEDetailsCourseType.text )
            intent.putExtra("Duration", binding.txtEDetailsCourseDuration.text)
            intent.putExtra("Category", binding.txtEDetailsCourseCategory.text)
           startActivity(intent)
        }
        binding.btnECourseDelete.setOnClickListener {

        }
    }

}
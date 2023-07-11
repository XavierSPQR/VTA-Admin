package com.vtahorana.adminvta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.vtahorana.adminvta.databinding.ActivityExpandInstructorDetailsBinding
import com.vtahorana.adminvta.databinding.ActivityInstructorBinding

class expandInstructorDetails : AppCompatActivity() {
    private lateinit var binding: ActivityExpandInstructorDetailsBinding
    var imageUrl = ""
    lateinit var Img:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpandInstructorDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            Img=bundle.getString("Image").toString()
            binding.txtexpandInsName.text=bundle.getString("Name")
            binding.txtexpandInsCourse.text=bundle.getString("Course")
            binding.txtexpandInsQualfications.text=bundle.getString("Qual")
            binding.txtexpandInsExp.text=bundle.getString("Exp")
            imageUrl = bundle.getString("Image")!!
            Glide.with(this).load(bundle.getString("Image"))
                .into(binding.expandInsImage)
        }
        binding.saveButtonIns.setOnClickListener {
            val intent = Intent(this, AddInstructor::class.java)
            intent.putExtra("Image", Img)
            intent.putExtra("Course",binding.txtexpandInsCourse.text)
            intent.putExtra("Name",binding.txtexpandInsName.text)
            intent.putExtra("Qual",binding.txtexpandInsQualfications.text)
            intent.putExtra("Exp",binding.txtexpandInsExp.text)
            startActivity(intent)

        }
    }
}
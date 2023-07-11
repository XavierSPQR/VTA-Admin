package com.vtahorana.adminvta

import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.vtahorana.adminvta.databinding.ActivityUploadBinding

class upload : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    var imageURL: String? = null
    var uri: Uri? = null
    var imageUrl = ""
    lateinit var spnCcategory:Spinner
    lateinit var spnCtype:Spinner
    lateinit var spnCduration:Spinner
    lateinit var spnClevel:Spinner
    private lateinit var ccategoryselected:String
    private lateinit var cdurationselected:String
    private lateinit var ctypeselected:String
    private lateinit var clevelselected:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveButton.isEnabled=false
        spnCcategory=findViewById(R.id.spnCourseCategory)
        spnCtype=findViewById(R.id.spnCourseType)
        spnCduration=findViewById(R.id.spnCourseDuration)
        spnClevel=findViewById(R.id.spnNvqLevel)
        val bundle = intent.extras
        if (bundle != null) {
            binding.edtCourseName.setText(bundle.getString("Name"))
            binding.edtCourseDesc.setText(bundle.getString("Description"))
            binding.edtCourseFee.setText(bundle.getString("Fee"))
            imageUrl = bundle.getString("Image")!!
            Glide.with(this).load(bundle.getString("Image"))
                .into(binding.uploadImage)
        }


        //Spinners - coursecategory
        val categoryoptions = listOf("Select Category","ICT", "Mechanical", "Metal","Construction","Automobile")
        val categoryadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryoptions)
        categoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCcategory.adapter = categoryadapter
        spnCcategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                    ccategoryselected= parent.getItemAtPosition(position) as String
                if (isAllSpinnersSelected())
                {
                    binding.saveButton.isEnabled=true
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //Spinner = coursetype
        val typeoptions = listOf("Select Type","Full-Time", "Saturday & Sunday", "Saturday","Sunday")
        val typeadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,typeoptions)
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCtype.adapter = typeadapter
        spnCtype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                ctypeselected= parent.getItemAtPosition(position) as String
                if (isAllSpinnersSelected())
                {
                    binding.saveButton.isEnabled=true
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //Spinner- courseduration
        val durationoptions = listOf("Select Duration","3 Months", "Six Months", "12 Months","18 Months")
        val durationAdapter= ArrayAdapter(this, android.R.layout.simple_spinner_item,durationoptions)
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCduration.adapter = durationAdapter
        spnCduration.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                cdurationselected = parent.getItemAtPosition(position) as String
                if (isAllSpinnersSelected())
                {
                    binding.saveButton.isEnabled=true
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        //Spinner- nvq Level
        val nvqLeveloptions = listOf("Select NVQ Level","NVQ Level 3", "NVQ Level 4", "NVQ Level 5","Non-NVQ")
        val nvqLevelAdapter= ArrayAdapter(this, android.R.layout.simple_spinner_item,nvqLeveloptions)
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnClevel.adapter = nvqLevelAdapter
        spnClevel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                clevelselected = parent.getItemAtPosition(position) as String
                if (isAllSpinnersSelected())
                {
                    binding.saveButton.isEnabled=true
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@upload, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }



        binding.uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        binding.saveButton.setOnClickListener {
            saveData()
        }
    }
    private fun saveData(){
        val storageReference = FirebaseStorage.getInstance().reference.child("CourseImages")
            .child(uri!!.lastPathSegment!!)
        val builder = AlertDialog.Builder(this@upload)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()
        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            if (isAllSpinnersSelected())
            {
                uploadData()
                dialog.dismiss()
            }else{
                dialog.dismiss()
                Toast.makeText(this@upload, "Please select all spinner options", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            dialog.dismiss()
        }
    }
    private fun uploadData(){
        val cname = binding.edtCourseName.text.toString()
        val cdesc = binding.edtCourseDesc.text.toString()
        val cfee = binding.edtCourseFee.text.toString()
        val dataClass = CourseDataClass(cname,
                                        ccategoryselected,
                                        imageURL,
                                        cdesc,
                                        cdurationselected,
                                        ctypeselected,
                                        clevelselected,
                                        cfee)
        FirebaseDatabase.getInstance().getReference("Courses").child(cname)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@upload, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@upload, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
    private fun isAllSpinnersSelected(): Boolean {
        return spnCcategory.selectedItemPosition != 0 &&
                spnCtype.selectedItemPosition != 0 &&
                spnCduration.selectedItemPosition != 0 &&
                spnClevel.selectedItemPosition != 0
    }
    }

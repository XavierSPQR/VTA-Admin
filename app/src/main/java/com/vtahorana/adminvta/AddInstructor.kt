package com.vtahorana.adminvta

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
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.vtahorana.adminvta.databinding.ActivityAddInstructorBinding

class AddInstructor : AppCompatActivity() {
    private lateinit var binding: ActivityAddInstructorBinding
    private var imageURL: String? = null
    private var uri: Uri? = null
    var imageUrl = ""
    private lateinit var spnInsC: Spinner
    private lateinit var courseselected: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spnInsC=findViewById(R.id.spnInsCourse)
        binding.saveButtonIns.isEnabled = false

        val bundle = intent.extras
        if (bundle != null) {
            binding.edtInsName.setText(bundle.getString("Name"))
            binding.edtInsExp.setText(bundle.getString("Exp"))
            binding.edtInsQualif.setText(bundle.getString("Qual"))
            imageUrl = bundle.getString("Image")!!
            Glide.with(this).load(bundle.getString("Image"))
                .into(binding.uploadInsImage)
        }

        val categoryoptions = listOf("Select Category","ICT", "Mechanical", "Metal","Construction","Automobile")
        val categoryadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryoptions)
        categoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnInsC.adapter = categoryadapter
        spnInsC.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                courseselected= parent.getItemAtPosition(position) as String
                if(position !== 0)
                {
                    binding.saveButtonIns.isEnabled=true
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
                binding.uploadInsImage.setImageURI(uri)
            } else {
                Toast.makeText(this@AddInstructor, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
        binding.uploadInsImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        binding.saveButtonIns.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
            val storageReference = FirebaseStorage.getInstance().reference.child("InstructorImages")
                .child(uri!!.lastPathSegment!!)
            val builder = AlertDialog.Builder(this@AddInstructor)
            builder.setCancelable(false)
            builder.setView(R.layout.progress_layout)
            val dialog = builder.create()
            dialog.show()
            storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isComplete);
                val urlImage = uriTask.result
                imageURL = urlImage.toString()
                uploadData()
            }.addOnFailureListener {
                dialog.dismiss()
            }
        }

    private fun uploadData() {
        var name=binding.edtInsName.text.toString()
        var exp=binding.edtInsExp.text.toString()
        var qual=binding.edtInsQualif.text.toString()
        val dataClass=InstructorDataClass(name,
                                          imageURL,
                                          courseselected,
                                          qual,
                                          exp)
        FirebaseDatabase.getInstance().getReference("Ins").child(name)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@AddInstructor, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                    val intent = Intent(this, Instructor::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@AddInstructor, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

}

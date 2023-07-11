package com.vtahorana.adminvta

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.vtahorana.adminvta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isExpanded = false
    private lateinit var context: Context

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFabBtn.setOnClickListener {

            if (isExpanded) {
                 shrinkFab()
            } else {
                 expandFab()
            }

        }
        binding.addCourseFabBtn.setOnClickListener {
            val intent = Intent(this,addCourse::class.java)
            startActivity(intent)
        }

        binding.txtaddCourse.setOnClickListener {
            val intent = Intent(this,addCourse::class.java)
            startActivity(intent)
        }
        binding.addInstructorFabBtn.setOnClickListener {
            val intent = Intent(this,Instructor::class.java)
            startActivity(intent)
        }

        binding.addInstructorFabBtn.setOnClickListener {
            val intent = Intent(this,Instructor::class.java)
            startActivity(intent)
        }
        binding.addEventFabBtn.setOnClickListener {
            //onGalleryClicked()
        }

        binding.txtaddEvent.setOnClickListener {
            // onGalleryClicked()
        }

    }




    override fun onBackPressed() {

        if (isExpanded) {
            shrinkFab()
        } else {
            super.onBackPressed()

        }
    }
    private fun expandFab() {

        binding.transparentBg.startAnimation(fromBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateClockWiseFabAnim)
        binding.addCourseFabBtn.startAnimation(fromBottomFabAnim)
        binding.addInstructorFabBtn.startAnimation(fromBottomFabAnim)
        binding.addEventFabBtn.startAnimation(fromBottomFabAnim)
        binding.txtaddCourse.startAnimation(fromBottomFabAnim)
        binding.txtaddInstructor.startAnimation(fromBottomFabAnim)
        binding.txtaddEvent.startAnimation(fromBottomFabAnim)

        isExpanded = !isExpanded
    }
    private fun shrinkFab() {
        binding.transparentBg.startAnimation(toBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        binding.addCourseFabBtn.startAnimation(toBottomFabAnim)
        binding.addInstructorFabBtn.startAnimation(toBottomFabAnim)
        binding.addEventFabBtn.startAnimation(toBottomFabAnim)
        binding.txtaddCourse.startAnimation(toBottomFabAnim)
        binding.txtaddInstructor.startAnimation(toBottomFabAnim)
        binding.txtaddEvent.startAnimation(toBottomFabAnim)

        isExpanded = !isExpanded
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.action == MotionEvent.ACTION_DOWN) {

            if (isExpanded) {
                val outRect = Rect()
                binding.fabConstraint.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    shrinkFab()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}

package com.mahi.newtest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }
    var imagePath: Uri?=null
    private lateinit var imageData: ImageView
     var imageToStore :Bitmap?=null
    // creating variables for our edittext, button and dbhandler
    private val PICK_IMAGE_REQUEST=99
    override fun onCreate(savedInstanceState: Bundle?) {
         var courseNameEdt: EditText? = null
         var courseTracksEdt: EditText? = null
         var courseDurationEdt: EditText? = null
         var courseDescriptionEdt: EditText? = null

         var addCourseBtn: Button? = null
         var readCourseBtn: Button? = null
         var dbHandler: DBHandler? = null

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing all our variables.
        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseTracksEdt = findViewById(R.id.idEdtCourseTracks)
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration)
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription)
        addCourseBtn = findViewById(R.id.idBtnAddCourse)
        readCourseBtn = findViewById(R.id.idBtnReadCourse)
        imageData = findViewById(R.id.Profile_image)

        imageData.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

                    requestPermissions(permissions, PERMISSION_CODE)
                } else{
                    chooseImageGallery();
                }
            }else{
                chooseImageGallery();
            }
        }
// companion object


        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = DBHandler(this@MainActivity)

        // below line is to add on click listener for our add course button.
        addCourseBtn.setOnClickListener(View.OnClickListener { // below line is to get data from all edit text fields.
            val courseName = courseNameEdt.getText().toString()
            val courseTracks = courseTracksEdt.getText().toString()
            val courseDuration = courseDurationEdt.getText().toString()
            val courseDescription = courseDescriptionEdt.getText().toString()

            // validating if the text fields are empty or not.
            if (courseName.isEmpty() && courseTracks.isEmpty() && courseDuration.isEmpty() && courseDescription.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter all the data..", Toast.LENGTH_SHORT)
                    .show()
                return@OnClickListener
            }

            // on below line we are calling a method to add new
            // course to sqlite data and pass all our values to it.
            dbHandler!!.addNewCourse(courseName, courseDuration, courseDescription, courseTracks)

            // after adding the data we are displaying a toast message.
            Toast.makeText(this@MainActivity, "Course has been added.", Toast.LENGTH_SHORT).show()
            courseNameEdt.setText("")
            courseDurationEdt.setText("")
            courseTracksEdt.setText("")
            courseDescriptionEdt.setText("")
        })
        readCourseBtn.setOnClickListener(View.OnClickListener { // opening a new activity via a intent.
            val i = Intent(this@MainActivity, ViewCourses::class.java)
            startActivity(i)
        })
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(this,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun chooseImage() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent,PICK_IMAGE_REQUEST)
            startActivity(intent)

        }catch(e:Exception ){
            Toast.makeText(applicationContext,e.message ,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
             if (requestCode==PICK_IMAGE_REQUEST &&requestCode== RESULT_OK && data !=null && data.getData() !=null)
             {
                imagePath=data.getData()
                 imageToStore=MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath)
                 imageData.setImageBitmap(imageToStore)

             }
        }catch (e:Exception){
            Toast.makeText(applicationContext,e.message ,Toast.LENGTH_SHORT).show()
        }

    }
}

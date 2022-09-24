package com.mahi.newtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ViewCourses : AppCompatActivity() {
    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
     var courseModalArrayList: ArrayList<CourseModal>? = null
     var dbHandler: DBHandler? = null
    private final var coursesRV: RecyclerView? = null
     var courseRVAdapter: CourseRVAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        // initializing our all variables.
        courseModalArrayList = ArrayList()
        dbHandler = DBHandler(this@ViewCourses)

        // getting our course array
        // list from db handler class.
        courseModalArrayList = dbHandler!!.readCourses()

        // on below line passing our array lost to our adapter class.
        courseRVAdapter = CourseRVAdapter(courseModalArrayList!!, this@ViewCourses)
        coursesRV = findViewById(R.id.idRVCourses)

        // setting layout manager for our recycler view.
        val linearLayoutManager =
            LinearLayoutManager(this@ViewCourses, RecyclerView.VERTICAL, false)
        coursesRV?.setLayoutManager(linearLayoutManager)

        // setting our adapter to recycler view.
        coursesRV?.setAdapter(courseRVAdapter)
    }
}

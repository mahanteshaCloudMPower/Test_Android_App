package com.mahi.newtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CourseRVAdapter     // constructor
    (// variable for our array list and context
    private val courseModalArrayList: ArrayList<CourseModal>, private val context: Context
) :
    RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // on below line we are inflating our layout
        // file for our recycler view items.
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.course_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // on below line we are setting data
        // to our views of recycler view item.
        val modal = courseModalArrayList[position]
        holder.courseNameTV.text = modal.courseName
        holder.courseDescTV.text = modal.courseDescription
        holder.courseDurationTV.text = modal.courseDuration
        holder.courseTracksTV.text = modal.courseTracks
    }

    override fun getItemCount(): Int {
        // returning the size of our array list
        return courseModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our text views.
         val courseNameTV: TextView
         val courseDescTV: TextView
         val courseDurationTV: TextView
         val courseTracksTV: TextView

        init {
            // initializing our text views
            courseNameTV = itemView.findViewById(R.id.idTVCourseName)
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription)
            courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration)
            courseTracksTV = itemView.findViewById(R.id.idTVCourseTracks)
        }
    }
}

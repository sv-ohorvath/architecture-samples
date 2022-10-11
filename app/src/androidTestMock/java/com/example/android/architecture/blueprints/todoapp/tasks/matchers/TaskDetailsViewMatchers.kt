package com.example.android.architecture.blueprints.todoapp.tasks.matchers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.android.architecture.blueprints.todoapp.R

/**
 * A class that contains a list of matchers for the Tasks details screen
 */
class TaskDetailsViewMatchers {
    val taskDetailsTitle = onView(withId(R.id.task_detail_title_text))
    val taskDetailsDescription = onView(withId(R.id.task_detail_description_text))
    val taskDetailsCheckBox = onView(withId(R.id.task_detail_complete_checkbox))
    val editTaskButton = onView(withId(R.id.edit_task_fab))
    val deleteTaskButton = onView(withId(R.id.menu_delete))
}
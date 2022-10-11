package com.example.android.architecture.blueprints.todoapp.tasks.matchers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.android.architecture.blueprints.todoapp.R

/**
 * A class that contains a list of matchers for the Edit tasks screen
 */
class EditTaskViewMatchers {
    val editTaskTitleTextBox = onView(withId(R.id.add_task_title_edit_text))
    val editTaskDescriptionTextBox = onView(withId(R.id.add_task_description_edit_text))
    val saveTaskButton = onView(withId(R.id.save_task_fab))
}

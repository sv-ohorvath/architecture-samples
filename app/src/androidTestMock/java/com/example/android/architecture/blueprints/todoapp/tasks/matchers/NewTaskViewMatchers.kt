package com.example.android.architecture.blueprints.todoapp.tasks.matchers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.android.architecture.blueprints.todoapp.R

/**
 * A class that contains a list of matchers for the Add new task screen
 */
class NewTaskViewMatchers {
    val newTaskTitleTextField: ViewInteraction = onView(withId(R.id.add_task_title_edit_text))
    val newTaskDescriptionTextField: ViewInteraction = onView(withId(R.id.add_task_description_edit_text))
    val saveTaskButton: ViewInteraction = onView(withId(R.id.save_task_fab))
}
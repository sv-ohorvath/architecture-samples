package com.example.android.architecture.blueprints.todoapp.tasks.matchers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.android.architecture.blueprints.todoapp.R

/**
 * A class that contains a list of matchers for the Tasks screen
 */
class TasksListViewMatchers {
    val addTaskButton: ViewInteraction = onView(withId(R.id.add_task_fab))
    fun taskItem(title: String): ViewInteraction = onView(withText(title))
}
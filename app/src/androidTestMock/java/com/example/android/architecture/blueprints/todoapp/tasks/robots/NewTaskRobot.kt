package com.example.android.architecture.blueprints.todoapp.tasks.robots

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import com.example.android.architecture.blueprints.todoapp.tasks.matchers.NewTaskViewMatchers

/**
 * A robot class that contains a list of actions done on the Add new task screen
 */
class NewTaskRobot {
    fun typeInNewTaskTitle(text: String): ViewInteraction =
        NewTaskViewMatchers().newTaskTitleTextField
            .perform(typeText(text), closeSoftKeyboard())

    fun typeInNewTaskDescription(text: String): ViewInteraction =
        NewTaskViewMatchers().newTaskDescriptionTextField
            .perform(typeText(text), closeSoftKeyboard())

    fun clickSaveTaskButton(func: TasksListRobot.() -> Unit): TasksListRobot {
        NewTaskViewMatchers().saveTaskButton.perform(click())

        return TasksListRobot().apply { func() }
    }
}

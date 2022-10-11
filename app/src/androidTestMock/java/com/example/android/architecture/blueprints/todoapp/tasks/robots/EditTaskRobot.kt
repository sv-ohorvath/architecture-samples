package com.example.android.architecture.blueprints.todoapp.tasks.robots

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import com.example.android.architecture.blueprints.todoapp.tasks.matchers.EditTaskViewMatchers

/**
 * A robot class that contains a list of actions done on the Edit task screen
 */
class EditTaskRobot {
    fun editTaskTitle(newTitle: String) =
        EditTaskViewMatchers().editTaskTitleTextBox.perform(replaceText(newTitle))

    fun editTaskDescrption(newDescription: String) =
        EditTaskViewMatchers().editTaskDescriptionTextBox.perform(replaceText(newDescription))

    // Transitions to other robots:
    fun clickSaveTaskButton(func: TasksListRobot.() -> Unit): TasksListRobot {
        EditTaskViewMatchers().saveTaskButton.perform(click())

        return TasksListRobot().apply { func() }
    }
}

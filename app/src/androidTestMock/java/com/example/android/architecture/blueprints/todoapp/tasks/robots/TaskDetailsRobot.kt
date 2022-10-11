package com.example.android.architecture.blueprints.todoapp.tasks.robots

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.android.architecture.blueprints.todoapp.tasks.matchers.TaskDetailsViewMatchers

/**
 * A robot class that contains a list of actions done on the Task details screen
 */
class TaskDetailsRobot {
    fun verifyTaskDetailsTitleIsCorrect(expectedTitle: String) =
        TaskDetailsViewMatchers().taskDetailsTitle.check(matches(withText(expectedTitle)))

    fun verifyTaskDetailsDescriptionIsCorrect(expectedDescription: String) =
        TaskDetailsViewMatchers().taskDetailsDescription.check(matches(withText(expectedDescription)))

    fun verifyTaskDetailsCheckBoxChecked(checked: Boolean) {
        TaskDetailsViewMatchers().taskDetailsCheckBox
            .check(
                matches(
                    if (checked)
                        isChecked()
                    else
                        isNotChecked()
                )
            )
    }

    // Transitions to other robots:
    fun clickEditTaskButton(func: EditTaskRobot.() -> Unit): EditTaskRobot {
        TaskDetailsViewMatchers().editTaskButton.perform(click())

        return EditTaskRobot().apply { func() }
    }

    fun clickDeleteTaskButton(func: TasksListRobot.() -> Unit): TasksListRobot {
        TaskDetailsViewMatchers().deleteTaskButton.perform(click())

        return TasksListRobot().apply { func() }
    }
}

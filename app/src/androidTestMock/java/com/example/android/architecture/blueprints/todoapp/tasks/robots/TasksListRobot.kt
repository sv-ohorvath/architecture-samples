package com.example.android.architecture.blueprints.todoapp.tasks.robots

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.android.architecture.blueprints.todoapp.tasks.matchers.TasksListViewMatchers

/**
 * A robot class that contains a list of actions done on the Tasks screen
 */
class TasksListRobot {
    fun verifyTaskExists(title: String, shouldBeDisplayed: Boolean): ViewInteraction =
        TasksListViewMatchers().taskItem(title)
            .check(
                if (shouldBeDisplayed)
                    matches(isDisplayed())
                else
                    doesNotExist()
            )

    // Transitions to other robots:
    fun clickAddTaskButton(func: NewTaskRobot.() -> Unit): NewTaskRobot {
        TasksListViewMatchers().addTaskButton.perform(click())

        return NewTaskRobot().apply { func() }
    }

    fun clickOnTask(title: String, func: TaskDetailsRobot.() -> Unit): TaskDetailsRobot {
        TasksListViewMatchers().taskItem(title).perform(click())

        return TaskDetailsRobot().apply { func() }
    }
}

fun taskListScreen(func: TasksListRobot.() -> Unit): TasksListRobot {
    return TasksListRobot().apply(func)
}

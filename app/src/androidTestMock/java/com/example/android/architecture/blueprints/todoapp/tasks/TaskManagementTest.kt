/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.tasks.robots.taskListScreen
import com.example.android.architecture.blueprints.todoapp.util.DataBindingIdlingResource
import com.example.android.architecture.blueprints.todoapp.util.EspressoIdlingResource
import com.example.android.architecture.blueprints.todoapp.util.deleteAllTasksBlocking
import com.example.android.architecture.blueprints.todoapp.util.monitorActivity
import com.example.android.architecture.blueprints.todoapp.util.saveTaskBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class TaskManagementTest {

    private lateinit var repository: TasksRepository

    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private val task = object {
        val title = "title"
        val description = "description"
        val newTitle = "NEW TITLE"
        val newDescription = "NEW DESCRIPTION"
    }

    @Before
    fun init() {
        // Run on UI thread to make sure the same instance of the SL is used.
        UiThreadStatement.runOnUiThread {
            ServiceLocator.createDataBase(
                ApplicationProvider.getApplicationContext(),
                inMemory = true
            )
            repository =
                ServiceLocator.provideTasksRepository(ApplicationProvider.getApplicationContext())
            repository.deleteAllTasksBlocking()
        }
    }

    @After
    fun reset() {
        UiThreadStatement.runOnUiThread {
            ServiceLocator.resetRepository()
        }
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun createTaskTest() {
        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on the "+" button, add details, and save
        taskListScreen {
        }.clickAddTaskButton {
            typeInNewTaskTitle(task.title)
            typeInNewTaskDescription(task.description)
        }.clickSaveTaskButton {
            // Then verify task is displayed on screen
            verifyTaskExists(task.title, true)
        }
        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

    @Test
    fun editTaskTest() {
        // Creates a task before the test to eliminate some unnecessary steps
        repository.saveTaskBlocking(Task(task.title, task.description))

        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click on the task on the list and verify that all the data is correct
        taskListScreen {
        }.clickOnTask(task.title) {
            verifyTaskDetailsTitleIsCorrect(task.title)
            verifyTaskDetailsDescriptionIsCorrect(task.description)
            verifyTaskDetailsCheckBoxChecked(false)
            // Click on the edit button, edit, and save
        }.clickEditTaskButton {
            editTaskTitle(task.newTitle)
            editTaskDescrption(task.newDescription)
        }.clickSaveTaskButton {
            // Verify task is displayed on screen in the task list.
            verifyTaskExists(task.newTitle, true)
            // Verify previous task is not displayed
            verifyTaskExists(task.title, false)
        }
        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

    @Test
    fun createOneTask_deleteTask() {

        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Add active task
        taskListScreen {
        }.clickAddTaskButton {
            typeInNewTaskTitle(task.title)
            typeInNewTaskDescription(task.description)
        }.clickSaveTaskButton {
            // Open it in details view
        }.clickOnTask(task.title) {
            // Click delete task in menu
        }.clickDeleteTaskButton {
            // Verify it was deleted
            verifyTaskExists(task.title, false)
        }
        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }

    @Test
    fun createTwoTasks_deleteOneTask() {
        repository.saveTaskBlocking(Task("TITLE1", "DESCRIPTION"))
        repository.saveTaskBlocking(Task("TITLE2", "DESCRIPTION"))

        // start up Tasks screen
        val activityScenario = ActivityScenario.launch(TasksActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        taskListScreen {
        }.clickOnTask("TITLE2") {
            // Click delete task in menu
        }.clickDeleteTaskButton {
            // Verify it was deleted
            verifyTaskExists("TITLE2", false)
            verifyTaskExists("TITLE1", true)
        }
        // Make sure the activity is closed before resetting the db:
        activityScenario.close()
    }
}
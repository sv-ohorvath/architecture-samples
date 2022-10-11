# README
_This document is intended to explain the specifics and how to contribute to Android UI tests._

# Tools and test design
* This test project is written using the Kotlin programming language.
* It uses the Espresso test automation framework.
* The tests are written following the Robot design pattern for easier maintenance of code, avoiding code duplication and provide an elegant way of chaining test steps, using inline functions.
* Robot classes are organized to reflect the architecture of the app, each representing a View/Fragment.
* Matchers and actions are separated for easy maintenance.

# Tests Location & how to run
* UI tests are located in the android-samples.app.androidTest module.
* To run a test, create a new Android Instrumented Test configuration by going to Run>Edit Configurations..., select the class and test you wish to run.
* Connect a physical device or set up an AVD from Android Studio.
* Run the test and check the results in the Run window.
To run all tests:
* either do the above steps and select to run all tests in this module/package
* or, you can use gradle terminal commands:
  `./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.package=packageName` - to run an entire package
  `./gradlew app:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=packageName.className` - to run a single test class 
  `./gradlew app:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=packageName.className#testName` - to run a single test  
* TBD: add more info how to run the tests on different build variants once the testBuildTypes will be defined.

# How to use IdlingResources:
* Idling Resources are meant to monitor your app for any ongoing operations on a different thread and halt test execution until they are done.
* To use them in your tests you need to register the Idling resource you need by calling the IdlingRegistry `register` function.
* When the Idling resource is no longer needed, you need to cleanup by unregistering it, calling the `unregister` method of the same IdlingRegistry instance.
* See examples in [TasksActivityTest](https://github.com/sv-ohorvath/architecture-samples/blob/master/app/src/androidTestMock/java/com/example/android/architecture/blueprints/todoapp/tasks/TasksActivityTest.kt#L62)

# Team:
* Oana Horvath - QA Automaton Engineer
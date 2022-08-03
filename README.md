<img src="https://github.com/Bhawna1203/DigiManager/blob/master/DigiManager.gif" align="centre" width="100%"/>

# **DigiManager**💙  

**DigiManager** is app that gives you the power to do more. It manages your daily tasks and boosts your productivity.

# Application Install

***You can Install and test latest DigiManager app from below 👇***

[![DigiManager](https://img.shields.io/badge/DigiManager✅-APK-red.svg?style=for-the-badge&logo=android)](https://github.com/Bhawna1203/DigiManager/releases/tag/1.0.0)

## Setup
Clone the repository on your machine. Open the project on your IDE and connect it to firebase and add dependencies and everything will be setup

## About

 It uses firebase for Storaging Data. It uses Firebase Authentication for email based auth and Google auth, Firebase Crashlytics for crash reporting.

- Fully functionable. 
- Clean and Simple  UI so that any people can use.
- Support many tasks visible to all members under same organisation.
- You can see the total task completed or assigned to you.
- Added the dragging and dropping feature in task list🤩 
- It supports dark theme too 🌗.

### Insights into the app 🔎

**DigiManager** offers light as well as dark theme 🌓. So now you can use DigiManager in whatever theme you like the most. 🔥
**DigiManager** supports both email based and Social media authentication like Google authentication. Planning to add Facebook authentication too. 😁

Have a look at your running task, upcoming and completed tasks all in one place. Create a task by pressing the button.
DigiManager lets you start a task ⌚ otherwise you get the notification of not completing the task. Then you will be not able to achieve your long time goal 😢.

## 📸 Demo

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
 - [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [DataBinding](https://developer.android.com/topic/libraries/data-binding) - Binds data directly into XML layouts
  - [Room](https://developer.android.com/training/data-storage/room) - Room is an android library which is an ORM which wraps android's native SQLite database
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
- Backend
  - [Firebase](https://firebase.google.com)
    - Firebase Auth - To support email based authentication and google authentication
    - Firebase Crashlytics - To report app crashes
  - [Firebase Storage](https://harperdb.io) -  For storing all the data .
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [GSON](https://github.com/google/gson) - A modern JSON library for Kotlin and Java.
- [Timber](https://github.com/JakeWharton/timber) - A simple logging library for android.
- [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - A Converter which uses Moshi for serialization to and from JSON.
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling


# Package Structure 👿
    
    com.example.digimanager    # Root Package
    .
    ├── activities                # For Storing all activities
    |   ├── BaseActivity          
    |   ├── CardDetailsActivity          
    │   ├── CreateBoardActivity           
    │   └── IntroActivity  
    │   └── MainActivity
    │   └── MembersActivity
    │   └── MyProfileActivity  
    │   └── SignInActivity 
    │   └── SignUpActivity  
    │   └── TaskListActivity  
    |
    ├── adapters                # adapter for inflating all views              
    │   └── BoardItemsAdapters
    │   └── CardListItemsAdapters
    │   └── CardMemberListItemsAdapter
    │   └── LabelColorListItemsAdapter
    │   └── MemberListItemsAdapter
    │   └── TaskListItemsAdapter
    |
    ├── dailogs                   # for applying some special feature on recycler view
    |   ├── LabelColorListDialog
    |   ├── MembersListDialog
    |
    ├── fcm                       # for implementing firebase notification feature
    |   ├── MyFirebaseMessagingService
    |
    ├── firestore                 # for all firebase related stuffs like storage , auth etc
    |   ├── FirestoreClass
    |
    ├── models                    # type of view and its data
    |   ├── Board
    |   ├── Card
    |   ├── SelectedMembers
    |   ├── Task
    |   ├── User
    |
    └── utils               # Utility Classes / Kotlin extensions
    |   ├── Constants


## Future Improvement ☠️
- Add time timer feature and show graphs on the same.
- Add to see all tasks organized in the calendar.
- Adding Facebook authentication as well.
- Working on improving the UI a little bit.

## If you like my projects and want to support me to build more cool open source projects
  
<a href="https://www.buymeacoffee.com/BhawnaChauhan"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&sluBhawnaChauhan&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff"></a>

---

 ## Contact
If you need any help, you can connect with me.
  


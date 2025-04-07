**USER MANUAL REPORT**  
**University of Guelph**   
**School of Engineering** 

**ENGG\*1420 – Object-Oriented Programming**  
**Final Project \- University Management System**   
**April 6th, 2025** 

# **Table of Contents** {#table-of-contents}

[Table of Contents](#table-of-contents)

[1\. Project Overview](#1.-project-overview)

[2 Implementation Steps](#2-implementation-steps)

[2.1 Packages and Classes](#2.1-packages-and-classes)

[2.2 Functions Defined](#2.2-functions-defined)

[2.3 Class Communications](#2.3-class-communications)

[3 User Interface](#3-user-interface)

[4 Testing and Debugging](#4-testing-and-debugging)

[5 Conclusion](#5-conclusion)

[Class Documentation](#class-documentation)

[EditActions](#editactions)

[CourseEditActions](#courseeditactions)

[EventEditActions](#eventeditactions)

[SubjectEditActions](#subjecteditactions)

[UserEditActions](#usereditactions)

[Management](#management)

[CourseManagement](#coursemanagement)

[EventManagement](#eventmanagement)

[SubjectManagement](#subjectmanagement)

[UserManagement](#usermanagement)

[LoginScreen](#loginscreen)

[MainLayout](#mainlayout)

[Header](#header)

[Nav](#nav)

[MenuItem](#menuitem)

[MetricCard](#metriccard)

[Spacer](#spacer)

[Model](#model)

[Course](#course)

[Event](#event)

[Subject](#subject)

[User](#user)

[Admin](#admin)

[Faculty](#faculty)

[Student](#student)

[ExcelService](#excelservice)

[Service](#service)

[CourseService](#courseservice)

[EventService](#eventservice)

[SubjectService](#subjectservice)

[UserService](#userservice)

[Role](#role)

[Launcher](#launcher)

[MainApp](#mainapp)

# **1\. Project Overview** {#1.-project-overview}

The University Management System is designed to simplify the administrative tasks of managing students, faculty, courses, subjects, and events within a university environment. It ensures the secure handling of sensitive data through an intuitive graphical user interface developed using JavaFX.  
The system is built around role-based access control, offering two primary user roles:

* **ADMIN:** Has full permissions, including adding, editing, deleting, and viewing data across all modules.

* **USER (Students & Faculty):** Limited to viewing information, with additional functionalities such as registering for events, managing personal profiles, viewing grades, enrolled courses, and faculty-specific views.

This is represented through the User class’s “role” attribute, which is an enum consisting of ADMIN, FACULTY, STUDENT, and USER.  
The core modules of the system include:

* **Subject Management:** Manage subjects offered by the university, essential for structuring courses.

* **Course Management:** Create and manage detailed course information, including faculty assignments, schedules, and student enrollments.

* **Student Management:** Store and handle comprehensive student records, including enrollment status, academic progress, and tuition management.

* **Faculty Management:** Manage faculty profiles, course assignments, and research interests.

* **Event Management:** Schedule and oversee university events, providing registration and participant management features.

Authentication mechanisms ensure users see only relevant information and functionalities based on their role. The program incorporates validation, error handling, and data consistency checks to maintain reliability and user trust throughout the platform​.

# **2 Implementation Steps** {#2-implementation-steps}

## **2.1 Packages and Classes** {#2.1-packages-and-classes}

Our project is organized into several logical packages:

* **GUI Package:**  
   This package is responsible for the user interface. In addition to Java classes that control behavior, it includes several FXML files that define the layout of various screens. These FXML files include:

  * **LoginScreen.fxml** – Defines the login interface.

  * **CourseManagement.fxml, EventManagement.fxml, SubjectManagement.fxml, UserManagement.fxml** – Layouts for the respective management views.

  * **Header.fxml, MenuItem.fxml, MetricCard.fxml** – Define the structure and styling of the header, navigation items, and metric display cards.

* The GUI package also contains classes such as:

  * **MainLayout, Header, Nav, MenuItem, MetricCard, Spacer:** Build and manage the overall window layout and navigation.

  * **LoginScreen:** Manages user authentication.

  * **Management and its subclasses (CourseManagement, EventManagement, SubjectManagement, UserManagement):** Provide views for managing data.

  * **EditActions and its subclasses (CourseEditActions, EventEditActions, SubjectEditActions, UserEditActions):** Supply row specific actions (edit/delete) for TableViews.

* **Models Package:**  
   Contains the data entity classes:

  * **Model (abstract):** Base class for all data objects (requires an isEqual method).

  * **Course, Event, Subject, User:** Main data types.

  * **User Subclasses (Admin, Faculty, Student):** Extend User with role-specific fields.

  * **Role (enum):** Lists possible user roles: ADMIN, FACULTY, STUDENT, USER.

* **Services Package:**  
   Handles data operations and persistence:

  * **ExcelService:** Uses Apache POI to load data from “UMS\_Data.xlsx.”

  * **Service (generic):** Provides CRUD operations for models.

  * **Specific Services (CourseService, EventService, SubjectService, UserService):** Manage operations for each model type.

* **Application Package:**  
   Classes related to application startup and scene management:

  * **MainApp:** This initializes services, handles scene switching, and sets up the primary Stage.

  * **Launcher:** Contains the main method and starts the application.

## **2.2 Functions Defined** {#2.2-functions-defined}

The UMS system implements several functions:

* Data Management:

  * **Create, Retrieve, Update, and Delete (CRUD) Operations:** Each service class provides methods to create, read, update, and delete data.

  * **Excel Integration:** Data is loaded from “UMS\_Data.xlsx” via ExcelService, ensuring consistent initial data.

* User Interface Functions:

  * **Dynamic View Switching:** The MainLayout and MenuItem classes allow seamless transitions between views.

  * **Form Dialogs:** Each management view offers dialogs for adding or editing data, with proper input validation.

  * **Role-Based Display:** The interface adjusts to show additional features (like edit/delete buttons) only for ADMIN users.

* Authentication:

  * **LoginScreen:** Validates user credentials against the UserService data. On successful login, the MainApp updates the current user and displays the MainLayout with appropriate permissions.

## **2.3 Class Communications** {#2.3-class-communications}

The various components of the UMS interact in the following ways:

* **GUI and Services:**  
   Management views pull data from their corresponding service classes. When users add, edit, or delete an item, the view calls the service’s method to update the data, which then refreshes the display.

* **Data Flow:**  
   At startup, ExcelService loads data from the Excel file. This data is then distributed to the appropriate services, which feed the GUI components.

* **Authentication Flow:**  
   The LoginScreen iterates through users in the UserService to verify credentials. A successful login triggers a switch to the MainLayout, which tailors its interface based on the user’s role.

* **Navigation and Event Handling:**  
   The Nav and MenuItem classes manage the navigation through the system. When a menu item is clicked, the main content area in MainLayout is updated with the selected view. EditActions and its subclasses also manage row-specific events (such as editing or deleting) by opening dialogs to collect user input before updating the service layer.

# **3 User Interface** {#3-user-interface}

The UMS user interface is designed with simplicity and clarity in mind:

Overall Layout:  
The MainLayout class arranges the application into three main sections:

Header: Displays user information and includes a logout button.

Navigation Menu: A collapsible side pane (handled by Nav and MenuItem) provides access to different modules.

Content Area: The central pane dynamically displays management views (such as dashboards or data tables).

Navigation:  
The navigation menu lets users easily switch between different modules. The available options depend on the user’s role—for example, ADMIN users have access to additional management functions.

Management Views:  
Each module (courses, events, subjects, users) is managed via a TableView that displays current data. ADMIN users have an extra “Actions” column, which provides buttons to edit or delete entries.

Dialogs:  
Custom dialogs (which solely use javaFX) are used for adding or editing data. These dialogs are laid out using GridPane for clarity, and they include validation to ensure correct input.

Styling:  
A CSS stylesheet (style.css) is loaded at runtime, ensuring a consistent look and feel across the application.

# **4 Testing and Debugging** {#4-testing-and-debugging}

Our team performed extensive testing to ensure the system is robust and user-friendly:

Service Testing:  
Each service (CourseService, EventService, etc.) was tested to verify that its CRUD operations work correctly and that data loaded from Excel is accurate.

User Interface Testing:  
We conducted manual tests to confirm that TableViews update properly after data changes and that dialogs validate user input effectively. We also verified that error messages are displayed when expected.

Role-Based Testing:  
By simulating different user roles, we confirmed that the UI only shows the appropriate options for each role (e.g., only ADMIN users see the “Add” button and edit/delete options).

Error Handling:  
Our testing included scenarios with invalid input to ensure that the system responds with clear alerts and confirmation dialogs, helping users avoid mistakes.

Debugging:  
JavaFX debugging tools and console logs in both the GUI and service layers were used to monitor data flow and ensure that scene transitions and event handling occur smoothly.

# **5 Conclusion** {#5-conclusion}

The University Management System successfully integrates multiple modules to streamline the administrative tasks of managing university data. By separating data handling (models and services) from the presentation layer (GUI and FXML), the system is both maintainable and extendable. Its role-based access control, dynamic view switching, and robust error handling make it easy for both administrators and regular users to work efficiently.

Key strengths of the system include:

* A modular design that separates data management from the user interface.

* A user-friendly and adaptive interface that shows only the relevant functionalities based on user roles.

* Reliable validation and error handling, ensuring that data remains consistent and accurate.

### 

# **Class Documentation** {#class-documentation}

## [**EditActions**](#table-of-contents) {#editactions}

[**EditActions**](#editactions)\<**T** extends [**Model**](#model)\> extends **TableCell**\<**T**, **Void**\>  
All subclasses of [**EditActions**](#editactions): [**CourseEditActions**](#courseeditactions), [**EventEditActions**](#eventeditactions), [**SubjectEditActions**](#subjecteditactions), and [**UserEditActions**](#usereditactions).  
Superclass, creates & styles buttons.  
Subclasses add button functionality and edit dialog.

Purpose: makes an Edit and Delete Button for each row in a TableView.  
abstract static **Dialog**\<**T**\> createEditDialog(**T** existing)  
	…If we had abstract static methods.

## [**CourseEditActions**](#table-of-contents) {#courseeditactions}

[**CourseEditActions**](#courseeditactions) extends [**EditActions**](#editactions)\<[**Course**](#course)\>  
Subclass for CourseManagement edit actions.

Purpose: Manages Editing and deleting specific to “Courses”

## [**EventEditActions**](#table-of-contents) {#eventeditactions}

[**EventEditActions**](#eventeditactions) extends [**EditActions**](#editactions)\<[**Event**](#event)\>  
Subclass for EventManagement edit actions.

Purpose: Manages Editing and deleting specific to “Events”

## [**SubjectEditActions**](#table-of-contents) {#subjecteditactions}

[**SubjectEditActions**](#subjecteditactions) extends [**EditActions**](#editactions)\<[**Subject**](#subject)\>  
Subclass for SubjectManagement edit actions.

Purpose: Manages Editing and deleting specific to “Subjects”

## [**UserEditActions**](#table-of-contents) {#usereditactions}

[**UserEditActions**](#usereditactions) extends [**EditActions**](#editactions)\<[**User**](#user)\>  
Subclass for UserManagement edit actions.

Purpose: Manages Editing and deleting specific to “Users”

## [**Management**](#table-of-contents) {#management}

[**Management**](#management)\<**T** extends [**Model**](#model)\> extends **VBox**  
All subclasses of [**Management**](#management): [**CourseManagement**](#coursemanagement), [**EventManagement**](#eventmanagement), [**SubjectManagement**](#subjectmanagement), and [**UserManagement**](#usermanagement).  
Superclass creates styles and adds functionality to buttons.  
Subclasses tell superclass associated Service, EditActions, and FXML file path.

Purpose: Serves as the base class for all management views. It loads its layout from an FXML file, initializes a TableView with data from a service class, and could load other features depending on the USER (user, admin, or faculty).

## [**CourseManagement**](#table-of-contents) {#coursemanagement}

[**CourseManagement**](#coursemanagement) extends [**Management**](#management)\<[**Course**](#course)\>  
associated CourseService, CourseEditActions, and CourseManagement.fxml.

Purpose: Handles event management by interfacing with EventService and using EventEditActions, along with the “EventManagement.fxml” layout.

## [**EventManagement**](#table-of-contents) {#eventmanagement}

[**EventManagement**](#eventmanagement) extends [**Management**](#management)\<[**Event**](#event)\>  
associated EventService, EventEditActions, and EventManagement.fxml.

Purpose: Handles event management by interfacing with EventService and using EventEditActions, along with the “EventManagement.fxml” layout.

## [**SubjectManagement**](#table-of-contents) {#subjectmanagement}

[**SubjectManagement**](#subjectmanagement) extends [**Management**](#management)\<[**Subject**](#subject)\>  
associated SubjectService, SubjectEditActions, and SubjectManagement.fxml.

Purpose: Manages subjects by utilizing SubjectService and SubjectEditActions, with its interface defined in “SubjectManagement.fxml.”

## [**UserManagement**](#table-of-contents) {#usermanagement}

[**UserManagement**](#usermanagement) extends [**Management**](#management)\<[**User**](#user)\>  
associated UserService, UserEditActions, and UserManagement.fxml.

Purpose: Deals with user accounts (Admin, Faculty, and Student). It integrates UserService with UserEditActions and loads its interface from “UserManagement.fxml.”

## [**LoginScreen**](#table-of-contents) {#loginscreen}

[**LoginScreen**](#loginscreen) extends **StackPane**  
[**LoginScreen**](#loginscreen) is the class responsible for handling the login process.  
The class loads LoginScreen.fxml through the FXMLLoader fxml Loader, setting the LoginScreen as its controller and root with setController and setRoot. After successfully attempting to load, the login screen is displayed.

When the login button is pressed, the username and password inputs are stored in **username** and **password,** respectively, notably password is converted to a byte array as that is required for the **login** method of the user. Then for each [**User**](#user) (subclasses included) in [**UserService**](#userservice)**.getAll** the id of that user, from **getID**, is compared to the username which was input, if they match the **login** method of the user is called returning either true or false based on whether the password is correct. If the **login** method returns true, [**MainApp**](#mainapp)**.user** is set to that user, and the screen is set up. If there are no matches after iterating through each user, the **errorMessage** object is loaded.

## [**MainLayout**](#table-of-contents) {#mainlayout}

[**MainLayout**](#mainlayout) extends **BorderPane**  
Creates all management pages.  
The main layout includes a [**Header**](#header), [**Nav**](#nav) and the current content.

## [**Header**](#table-of-contents) {#header}

[**Header**](#header) extends **HBox**

Displays the logged-in user’s information and a logout button. Loaded from an FXML file, it updates its display based on the current user and returns to LoginScreen when logout is pressed.

## [**Nav**](#table-of-contents) {#nav}

[**Nav**](#nav) extends **SkinBase**\<**TitledPane**\>  
Holds [**MenuItem**](#menuitem)s and collapses horizontally.  
Customizes the navigation pane with a collapsible menu. It manages the animation and layout of the menu items and works with MenuItem to update the content area.

## [**MenuItem**](#table-of-contents) {#menuitem}

[**MenuItem**](#menuitem) extends **VBox**  
This acts like a button, and when pressed, it navigates you to its page. 

## [**MetricCard**](#table-of-contents) {#metriccard}

[**MetricCard**](#metriccard) extends **VBox**  
Displays statistics, title and a value

## [**Spacer**](#table-of-contents) {#spacer}

[**Spacer**](#spacer) extends **Region**  
Literally a spacer, a UI component that adds flexible space

## [**Model**](#table-of-contents) {#model}

[**Model**](#model)  
This is the base class for all data entities made  
All subclasses of [**Model**](#model): [**Course**](#course), [**Event**](#event), [**Subject**](#subject), [**User**](#user) and subclasses.  
Each subclass of [**Model**](#model) must have an isEqual method that returns a boolean.  
abstract **boolean** isEqual([**Model**](https://docs.google.com/document/d/1TsaLpf7wxHbFcYYxB5TA74ofSMi3ia1hY2qLPCi5udU/edit?pli=1&tab=t.0#heading=h.dtksbu783q6s) updated)

## [**Course**](#table-of-contents) {#course}

[**Course**](#course) Extends [**Model**](#model)  
Represents a course, the attributes being, the course code, name subject, section, capacity, location and teacher.  
get/set **String** code;  
get/set **String** courseName;  
get/set **String** subject;  
get/set **String** section;  
get/set **String** capacity;  
get/set **String** location;  
get/set **String** teacher;

## [**Event**](#table-of-contents) {#event}

[**Event**](#event) extends [**Model**](#model)  
Represents and event, the attributes being an event name, code, description, capacity and cost.  
get/set **String** code;  
get/set **String** eventName;  
get/set **String** capacity;  
get/set **String** cost;  
get/set **String** description;

## [**Subject**](#table-of-contents) {#subject}

[**Subject**](#subject) Extends [**Model**](#model)  
Represents an academic subject with a subject code and name, used for organising courses and managing enrolments  
get/set **String** code;  
get/set **String** subjectName;

## [**User**](#table-of-contents) {#user}

[**User**](#user) extends [**Model**](#model)  
All subclasses of [**User**](#user): [**Admin**](#admin), [**Faculty**](#faculty), [**Student**](#student).  
The [**User**](#user) class inherits from the abstract class [**Model**](#model), which itself sets the template that each subclass of [**Model**](#model) must have an isEqual method that returns a boolean. This method is implemented in the [**User**](#user) class ,where it checks if the Model (including subclasses) added as an argument is the same as the Model the technique is being called from. This is used as a part of verification during the login process.  
Most attributes of the [**User**](#user) class are private unless there is a reason for them to be accessed directly; this is to ensure control over what information can be acquired from the [**User**](#user) class, such as preventing passwords from being read directly.  
**Id**, **fullName**, and **email** are attributes stored as private StringProperty.  
Setting them to StringProperty is helpful as it makes binding to Labels easier in JavaFX.  
**Role** is a private enum set during construction based on the subclass being created. If the user has the ADMIN role, they have access to the admin options, likewise for STUDENT and FACULTY. By making the role an attribute instead of checking which subclass the user is, it allows for greater flexibility in permissions, such as temporarily changing the role for a certain operation that requires higher permissions (Though such operations were ultimately handled without changing the role).  
The class has typical getter and setter methods for relevant classes, with the exception being **setPasswor,d** which is different on account of the class using the salt and hash method to store passwords.  
The entire password process is implemented through the methods: **setPassword**, **newSalt**, and **hash**, as well as the attributes: **passwordSalt**,and **passwordHash**.  
**setPassword** calls the relevant methods to update the **passwordSalt** and **passwordHash** attributes. This is run once during the construction of the object and whenever the password must be updated.  
**newSalt** generates a salt (a portion of random data used as an additional input to a one-way function) by creating a 16-length byte array and then filling it with SecureRandom (a cryptographically strong random number generator.).This salt is then returned.  
**newHash** takes a byte array as input, creates a MessageDigest instance (a class that generates hashes from input), updates that instant with **newSalt**, and then computes the final hash by using the byte array input as the initial argument. This method is called when updating the passwor, or when trying to see if a certain byte array corresponds to the password.  
**Login** checks to see if the hash of the byte array passed as an argument corresponds to the stored hash.

All subclasses of [**User**](#user): [**Admin**](#admin)**, [Faculty](#faculty), [Student](#student)**, add relevant attributes to the object. Importantly, the [**Admin**](#admin) class sets the user’s role to ADMIN during construction, ensuring that other parts of the program will identify that they have admin permissions. Whereas [**Student**](#student) and **[Faculty](#faculty)** have **roles** set to STUDENT and FACULTY respectively.

get/set **String** ID  
get/set **String** fullName  
get/set **String** email  
get/set **String** username  
get/set [**Role**](#role) role  
set **byte\[\]** password

## [**Admin**](#table-of-contents) {#admin}

[**Admin**](#admin) extends [**User**](#user)  
Represents an administrator with full permissions. In addition to basic user info, it may include additional details like office location. The role is set to ADMIN.

## [**Faculty**](#table-of-contents) {#faculty}

[**Faculty**](#faculty) extends [**User**](#user)  
Represents a faculty member with details such as degree, research interest, office location, and a list of courses offered. The role is set to FACULTY.

get **String** degree;  
get **String** researchInterest;  
get **String** officeLocation;  
get **ArrayList**\<**String**\> coursesOffered;

## [**Student**](#table-of-contents) {#student}

[**Student**](#student) extends [**User**](#user)  
Represents a student with extra details including address, telephone number, academic level, current semester, photo location, enrolled courses, thesis title, and progress. The role is set to STUDENT.

get **String** Address;  
get **String** TelephoneNumber;  
get **String** AcademicLevel;  
get **String** CurrentSemester;  
get **String** photoLocation;  
get **ArrayList**\<**String**\> Courses;  
get **String** ThesisTitle;  
get **int** progress;

## [**ExcelService**](#table-of-contents) {#excelservice}

[**ExcelService**](#excelservice)  
does not extend Service, reads UMS\_Data.xlsx

## [**Service**](#table-of-contents) {#service}

[**Service**](#service)\<**T** extends [**Model**](#model)\>  
All subclasses of [**Service**](#service): [**CourseService**](#courseservice), [**EventService**](#eventservice), [**SubjectService**](#subjectservice) and [**UserService**](#userservice).  
Superclass, holds associated Models  
Generic base service class, providing common CRUD operations for any models in the model class  
**List**\<**T**\> getAll()  
**void** add(**T** thing)  
**void** delete(**T** thing)  
**void** update(**T** updated)

## [**CourseService**](#table-of-contents) {#courseservice}

[**CourseService**](#courseservice) extends [**Service**](#service)\<[**Course**](#course)\>  
holds Courses  
Manages Course objects by loading data through ExcelService and providing CRUD functions. It integrates with CourseEditActions and the CourseManagement view.

## [**EventService**](#table-of-contents) {#eventservice}

[**EventService**](#eventservice) extends [**Service**](#service)\<[**Event**](#event)\>  
holds Events  
Manages Event objects by loading data through ExcelService and providing CRUD functions. It integrates with EventEditActions and the EventManagement view.

## [**SubjectService**](#table-of-contents) {#subjectservice}

[**SubjectService**](#subjectservice) extends [**Service**](#service)\<[**Subject**](#subject)\>  
holds Subjects  
Manages Subject objects by retrieving data from ExcelService and supporting editing and deletion through SubjectEditActions and SubjectManagement.

## [**UserService**](#table-of-contents) {#userservice}

[**UserService**](#userservice) extends [**Service**](#service)\<[**User**](#user)\>  
holds Users  
Manages User objects. It is initially populated with sample users (Admin, Faculty, Student, and a generic User) to support authentication and role-based access.

## [**Role**](#table-of-contents) {#role}

[**Role**](#role)  
Used across application to enforce access controls  
enum \[ADMIN, FACULTY, STUDENT, USER\].

## [**Launcher**](#table-of-contents) {#launcher}

[**Launcher**](#launcher)  
The main class.  
[**Launcher**](#launcher) just calls [**MainApp**](#mainapp).launch.

## [**MainApp**](#table-of-contents) {#mainapp}

[**MainApp**](#mainapp) Extends **Application**  
Creates all services, controls [**MainLayout**](#mainlayout)/[**LoginScreen**](#loginscreen) switching, and loads the  stylesheet.

[**User**](#user) user;  
**Stage** stage;  
get [**ExcelService**](#excelservice) excelService;  
get [**SubjectService**](#subjectservice) subjectService;  
get [**CourseService**](#courseservice) courseService;  
get [**EventService**](#eventservice) eventService;  
get [**UserService**](#userservice) userService;

[image1]: <data:image/png;base64,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>

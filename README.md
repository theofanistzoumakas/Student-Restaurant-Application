# 🍽️Student Restaurant Menu App



## 🌐A web application design for student restaurants, where student restaurant admins can manage a week menu and customers can have a personalized experience. 

**It was developed for my thesis at the Department of Informatics of University of Piraeus** (year 2025).

> ℹ️ This project is not open source and does not grant any usage rights.
> For usage terms and legal information, see 🔒 [Code Ownership & Usage Terms](#-code-ownership--usage-terms).

## 🏗Built with:

 - Spring Boot

- Spring Security

- Java version 17 as core development language

- PgAdmin for database

- Hibernate

- Thymeleaf for dynamic UI

- HTML for UI

- Javascript for dynamic UI

- Bootstrap for UI style

- Animate and Scroll Library for UI style





## ⚡Features:

- 👤For Admins:

	- 🔑Login

	- 👀See the weekly menu

	- ➕️Insert menu

	- 🍴Add new food on the new menu

	- 🔧Edit menu

	- ➖️Delete menu

	- 📝See/Add/delete an allergen

	- 📋See the percentage of each allergen

	- 📣Add/Delete announcements

	- 📊See statistics about:

		- Rated foods 
			- (The model Baysian approximation for k scale rating is used for this statistic file in project)

                File in project -> RatingsService, countRatings method

				**You can see the source “[here](https://medium.com/tech-that-works/wilson-lower-bound-score-and-bayesian-approximation-for-k-star-scale-rating-to-rate-products-c67ec6e30060)”**

		- Vegan foods

		- Vegetarian foods

		- Allergens

	- ❌️Delete foods from the database



- 👤For Customers 

	- 🔐Sign up

 	- 🔑Log in

 	- 👀See weekly menu

 	- 🍴See if food is vegan, vegetarian or allergic personally

 	- ⭐️Rate a food

 	- 📣See announcements

 	- 📋See allergens

 	- ✅️Check allergens

 	- 📝Manage your rated saved foods

 	- 📊See statistics about foods

 	- 💻📱(Feel the experience either large or small screens through responsive UI)



## 🎯Purpose 

This goal for student restaurant web app was achieved within the framework of my thesis, which concerned the analysis, design and construction of a comprehensive and functional software, developed using Spring Boot framework with a server. **It is developed solely for academic and research purposes.**



## 🏛Structure & Architecture

The application was organized according to the Model-View-Controller architecture with Service and Repository layers.



The basic structure of the project and each layer's role is presented below:



- 🧠Back-end:

  - Controller-> http handling

  - Model-> jpa entities

  - Repository-> Hibernate repositories for CRUD features

  - Service -> business logic



- 🚀Front-end:

  - Resources

   - Static

     - Css-> styling pages

     - Js-> for dynamic UI

  - Templates

     - Home -> Login and Sign up pages

     - RestaurantAdmin-> Admin's pages

     - RestaurantCustomer -> Customer pages

  - (Some pages are common for admin and customers and they are on Templates folder)



## ❗Prerequisites
 - IntelliJ IDEA for the project
 - PgAdmin for the database
 - Internet connetion for UI (Bootstrap and Animate on Scroll Library)



## 📦 Installation



1. Clone the repository (or download the ZIP file).

```bash

git clone https://github.com/theofanistzoumakas/student-restaurant-app.git

cd student-restaurant-app



```

2. Open the project in IntelliJ IDEA

3. Download/Open the PgAdmin

4. Create a new database

5. Connect the database with the project, following the instructions below:

	3.1 In the application _dot_ properties fill your db name,usenrame and password from pgAdmin:

	```bash

		spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name

		spring.datasource.username=your_username

		spring.datasource.password=your_password

	```

6. In SignUpAdmin file, fill your password and your username for the admin. 

**The admin in this project is one and only and is created only once, when the app is first launched.**

```bash

String pass_result = ...("YOUR_PASSWORD");

RestaurantUser admin = new RestaurantUser("YOUR_USERNAME",pass_result);

```

7. Build and run the app.

8. Open a browser

9. Type in url:

```bash

localhost:8080/Home/login

```



## 🔒 Code Ownership \& Usage Terms

This project was created and maintained by:



Theofanis Tzoumakas (@theofanistzoumakas).



🚫 Unauthorized use is strictly prohibited.

No part of this codebase may be copied, reproduced, modified, distributed, or used in any form without explicit written permission from the owners.



Any attempt to use, republish, or incorporate this code into other projects—whether commercial or non-commercial—without prior consent may result in legal action.



For licensing inquiries or collaboration requests, please contact via email: theftzoumi _at_ gmail _dot_ com .



© 2025 Theofanis Tzoumakas. All rights reserved.








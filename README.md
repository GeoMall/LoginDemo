# logindemo

## Project Set Up

### Default Project Configuration
1. Download project from git repository and open in IntelliJ IDE
2. Run the project. This will start the project on `http://localhost:8080`
3. This demo will use the H2 in memory database. To access the H2 console, navigate to `http://localhost:8080/h2/`. Default Credentails are: Username: *sa* with empty password.
4. To access login page, navigate to `http://localhost:8080/login`.

### Mail Server Configuration
If mailDev is not installed on your pc, from command line perform the below:
1. Run `npm install -g maildev`
2. Once it is installed, run `maildev`
3. Navigate to `http://localhost:1080` to access the maildev inbox.
4. Login for maildev if requested: Username: `admin` Password: `admin`


## Page Guide

Once project is configured, you can start the login process.

### Admin Login

By default, an admin user is created with the credentials Username: `admin@admin.com` Password: `Admin`. 

![image](https://user-images.githubusercontent.com/23236705/155667557-511b5891-04ce-40e8-8142-ad484b949f1b.png)

Once logged in, it will navigate you to the home page:

![image](https://user-images.githubusercontent.com/23236705/155667481-86d1b20b-9d3e-4634-b753-a2d2bcaada70.png)

The admin user can view the list of users by navigating to the User List Page from the nav bar. The users will be listed as below:

![image](https://user-images.githubusercontent.com/23236705/155668107-3ab95397-5e48-4f5a-857b-045800b269df.png)

The admin user can sign out by pressing the Sign out button from the nav bar, which will redirect him to the login page.

### User Login

Once the user is on the login page `http://localhost:8080/login`, the user can sign up by clicking the sign up button. This will redirect him to the registration form:

![image](https://user-images.githubusercontent.com/23236705/155668737-cdae00c0-bbd0-4819-bc9f-9e02e56acc83.png)

Once all the details are filled in, the user will have confirmation that the registration is successful:

![image](https://user-images.githubusercontent.com/23236705/155668794-2755eb77-7753-4b5a-bb1b-c9f199b8f8de.png)

Before the user can log in, he must confirm the token sent by mail. Navigate to `http://localhost:1080` and the below email should be received: 

![image](https://user-images.githubusercontent.com/23236705/155668968-fd2113a4-c1af-4d34-83c6-bcf6d4afe7be.png)

Once the `Activate now` button is clicked, the user should have confirmation that the Token is confirmed. Once this is confirmed the user can proceed to login. Once logged in, the home page will be displayed:

![image](https://user-images.githubusercontent.com/23236705/155669370-4f10a33e-c879-48b2-99ce-118cb40cd654.png)








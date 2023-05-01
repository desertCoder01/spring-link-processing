Spring boot demo project for dealing with links generated as Api response 

Description : 
 1. Signup api : to register a user in data for which we will generate some link
        you can create a separate api (login) to check whether data is stored correctly in mongo db or not 
        For now , i am not using  any password encoder , you can create a bean for that and use it to encode 
        the password before saving it in mongodb 
2. confirmAccount api : Generally , on signup using email and password , we use to receive a link on email
        on clicking the link , user is verified and account gets activated .
3. forgetPassword api : When user clicks on forget password , we use to receive link upon clicking which a password reset
        page is opened which asks for password and upon clicking submit your password is reset

All these things are implemented in this repository

Prerequisite :
Mongodb installed in your system (localhost)
# Price Tracker
* Clara Tricot - 3rd Year at ESIEA
* Antoine Mairet - 3rd Year at ESIEA
* Stella Thammavong - 3rd Year at ESIEA
* Metagang Tabou - 3rd Year at ESIEA
* Karina Moussaoui - 3rd Year at ESIEA
* Yi-Hsuan Lee - Exchange student from Tamkang University

## Introduction
We noticed that nowadays, there are many people who shop online. However, one can
experience a change of mind due to an inconvenience in the article’s price being too high.
For this reason, the best option is often to wait for a decrease. Yet, it can really be boring
and tedious to constantly check the price of an article to track any price drop.
For this reason, we decided to develop **an Android application that notifies the user when an article’s price
drops on a website compatible with our application**.

## Getting Started
1. Download and install [Android Studio](https://developer.android.com/studio/)
2. Clone or download the [master branch](https://github.com/SilverDove/Price_Tracker.git) using the functionalities of Android Studio. 

## Features
* Nine screens: 3 fragments and 6 activities
    * Home page: the user can add a new product
    * Help page: indication to understand how to use the application
    * Settings: parameters and extra information
    * FAQ: questions and answers about the app for the users
    * Suggestions: the user can indicate to the team what should be improved
    * List of compatible websites: list of the websites the user can use with the app
    * List of products: list of products the user wants to be notify when a product price drops
    * Product details: display extra information about a specific product
* SQLite database to store the products added by the user
* Bottom Navigation to navigate between the Home page, the Settings and the list of products
* Notification
* Algorithm to find a price for any website when knowing its pattern
* Service
* Icon app
* HTTP request

 ## Overview
 
 ### Home Page
 When the user launches the application, our Home page which allows the user to add a
 product into the application is shown.
 ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/Home.PNG)
 
 As explained, if the user wants to be notified when a product price drops, they need
 to add this product into the application by giving a name and a valid URL link .
 For example, the user wants to follow the price of the stool KULLABERG from Ikea.
 After copying the URL link from the webpage, the user pastes it into the application.
 They choose “Stool KULLABERG” to be the name of the product in Price Tracker .
 
 ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/addProduct.PNG)
 
 After clicking on the button “Valider” , the product (here Stool KULLARBERG) is
 added in the list and the user is directly redirected to the List page .
 
The URL link must be compatible with the application otherwise the product will
not be added. The user can check if his/her product can be added by reading the
list of compatible shops in the Settings . If there is no internet connection when
adding a product, an error message will popup. Moreover, the user can not use
twice the same product name .
In our example, Ikea is a compatible website. As a result, the product will be added to
the application. However, if the user takes a link from an incompatible shop (for
example Darty) and clicks on the button “Valider”, an error will appear.

 ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/incorrectURL.PNG)
 
 ### Help Page
 They can also learn how to use the app by clicking
 on the button “Aide”
 
 ### List of Products
 This page contains all the products added by the user .In the list, the user can see the product’s name and price . For each product, they can see
information details and delete the selected product.

* **Frequency icon:** In the List page, the user can see a yellow bell icon at the top right of the screen .
This icon allows them to change the frequency at which the application will
update each product’s price in the list after going to each webpage .
This frequency must be at least equals to 15 minutes.
  * *When there is no product:* In this case, the user can’t change the notification frequency option because there is
    nothing in the list.
    ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/NotificationFrequencyEmpty.PNG)

  * *When there is at least one product:*
  ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/NotificationFrequency.PNG)
  
  In our example, the user changes the notification frequency from 15 minutes to 25
  minutes. It means that Price Tracker will update each product price every 25 minutes.
  If during this refresh process, a product price drops, the user will get a notification.
  
  * **Refresh Manually each product information:** As said before, the application updates the price of each product at least every 15
    minutes after going to each product webpage. However, it’s possible to do this
    operation manually.
    The user must go to the List page and must swipe down the screen to refresh the list.
    
    In our example, the price of “Product X” was 50.0 euros. After refreshing manually
    the list, the price is 45.99 euros.
    
    ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/RefreshProduct.PNG)
    
    Note: If the user checks the details of the products in the list, they will see some values
    changed: the date of the refresh and the current price (if they check the details of “Product X”)
    
    * **Delete:**
    The user can delete one product of the list by clicking on the red bin icon.
    In our example, the user deleted the product “Lenovo PC”.
    
    ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/Delete.PNG)
    
    The user can delete all the products from the list by clicking on the button “Delete
    All”.
    
    ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/DeleteAll.PNG)
 
 ### Product details
 The user has the possibility to get more details about a
 specific product by clicking on the icon.
In the details page, the user can see the product’s name, the
last date the application went to the webpage to refresh the
product’s price, the product’s initial price, the current price, the
date when the product was added in Price Tracker.
The user also has the possibility to activate or deactivate an
option .

![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/detailsProduct.PNG)

* **Notification Under:** In the details page, the user can activate or inactivate an option for the chosen
product. This option allows the user to be notified only if the product’s price is below
a specific amount (given by the user).

The user must select a price and click on the button “Appliquer” to activate and set
the option. Here, the option is activated for 25 euros. It means that the user will receive a
notification only when the product price is below 25 euros.

![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/NotificationBelowOption.PNG)

 ### Notification
 At a specific frequency, Price Tracker is going to each product’s webpage to get the
price and refresh each product’s price from the list.
During this process, the application will send a notification to the user if one of the
products price’s drops . This notification gives the name of the product and its new price.

![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/Notification.PNG)
 
 ### Settings
 The user has access to the settings of the application. They can read the frequently asked
 questions (FAQ ), send suggestions to the Price Tracker team, read the lists of compatible
 shops and change the language of the application.
 
 ![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/Settings.PNG)
 
 ### FAQ
 On this page, the user can read all the answers to questions that can be frequently
asked.

![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/FAQ.PNG)
 
 ### Suggestions
 On this page, the user can send suggestions to the Price Tracker team. If the user
wants a reply, they must give their email address at the end of what they want to
send.

![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/Suggestions.PNG)
 
 ### List of compatible website
 On this page, the user can see the list of all the compatible shops. It means that they
can only add products from these shops. At this moment, the Price Tracker implemented a few shops: electro depot, grosbill,
IKEA, footlocker and decathlon.

![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/listeEnseignes.PNG)

## Language
On this page, the user can change the language of the application from French to
English and vice-versa. Unfortunately, this option is not activated

![](https://github.com/SilverDove/Price_Tracker/blob/master/Price_Tracker%20Screen/Language.PNG)
 
  ## Authors
  * **Clara Tricot** - *Price Tracker* - [SilverDove](https://github.com/SilverDove)
  * **Antoine Mairet** - *Price Tracker* - [Antoine](https://github.com/antoinemairet)
  * **Stella Thammavong** - *Price Tracker* - [StellaTham](https://github.com/StellaTham)
  * **Metagang Tabou** - *Price Tracker* - [TMetagang](https://github.com/TMetagang)
  * **Karina Moussaoui** - *Price Tracker* [Karinamsw](https://github.com/karinamsw)
  * **Yi-Hsuan Lee** - *Price Tracker* - [Yihsuanlee1112](https://github.com/Yihsuanlee1112)

 
 ## License
 This project is licensed under the MIT license.

 Copyright (c) 2020 Price Tracker
 
 ## Acknowledgments
 Helped by our teachers [Eunice Martins](https://www.linkedin.com/in/eunice-martins/), [Nadine Khodor](https://www.linkedin.com/in/nadine-khodor-b71aa9161/) and by the [Android Codelabs](https://www.linkedin.com/in/nadine-khodor-b71aa9161/). Inspired by the Youtuber [Coding in Flow](https://codinginflow.com/) and others 
  
  

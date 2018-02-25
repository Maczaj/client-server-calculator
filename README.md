# client-server-calculator

## Introduction

Hello! This application was initially created for purposes of recruitment process, but since I found this task to be quite interesting, I decided to share it publicly. Of course, I've put limited time resources into testing so if you find a bug, feel free to report and issue (which I may fix if find time :) ) or submit pull request containing fix. 

## Overview
    
 Client is created using AngularJS (in the future I may create implementation in Angular4), but actual calculation is performed on the server created with Spring-Boot. Goal was not to allow user to insert illegal element and simply throw exception by server in case something illegal is found.
 
 Application allows user to compute complex expressions composed of basic arithmetic operators and additionally root and power of two. Also user can group expression using parentheses and brackets. For calculation, [Shunting-Yard algorithm] (https://en.wikipedia.org/wiki/Shunting-yard_algorithm) has been used.
 
 Another function is to compute definite integral of e^x using number of threads chosen by user and number of ranges in that computation will be split.  
 
 ## Demo
 
 Live version of application is deployed on Heroku, you can play with it [here] (https://playground-calculator.herokuapp.com).
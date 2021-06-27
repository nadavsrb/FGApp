# FlightGear Controller Android-App

In this project, we created an android application that can connect to a FlightGear server and controll the plane.

[A demonstration of the project](link)


## Project Structure:
we used the MVVM architecture in order to create the app.
this means we divided the project into three main parts: `View`, `ViewModel` and `Model`.
#### the model:
is responsible for connecting to the FlightGear and send it commands.
#### the ViewModel:
is mainly responsible for linking these two parts,  and in some cases for converting the data from one representation to another.
#### the view:
is responsible for the visual part, and for invoking commands as response to the user actions.
[for more info of the RESTful api look here](Structure.md)


## Download and Set-Up Instruction:
-FlightGear

## Building and running instructions:
##### open the FlightGear:
- choose the `Browse .exe` option after choosing `Browse Files`;  then you should select the `fgfs.exe` file in the bin folder. its default path would be: `C:\Program Files\FlightGear 2020.3.6\bin\fgfs.exe`

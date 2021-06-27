![uml](readme-resources/uml.jpg?raw=TRUE "view-uml")


## The View:

#### The View side classes:
**Joystick:** This class is in charge of creating the Joystick. The Joystick is a different part from the activity because it can be used anyWhere.
It implements SurfaceView, Callback, View.OnTouchListener and has an inner interface: JoystickListener.
**MainActivity:** This class is in charge of the first activity. It uses `activity_main.xml` to set the layout and afterwards refers the user to the next activity.
In this activity the user set the port and the IP in order to connect to the `Flight-Gear`.
**ControllersActivity:** This class is in charge of the second activity. It uses `activity_controllers.xml` to set the layout.
In this activity the user controlls the plane by the joystick and the seek bars.


## The View-Mode:

#### The View-Model classes:
**ViewModel:** This class implements our intedfaces: `ConnectionVM` and `ControllersVM`. its purpose is to connect the view and the model.
This class is an `object` because we wanted to use the singelton pattern.

## The Model:
#### The Model classes:
**ClientManager:** This class implements our interface:  `Model`. It in charge of communicate with the server and send it commands.



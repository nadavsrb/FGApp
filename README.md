# Flight Gear Controller Android-App

In this project, we created an android application that can connect to a Flight Gear server and controll the plane.

[A demonstration of the project](link)

## The Project Structure:
There are 3 main parts of the project:
#### the model:
it includes the `manager.py` which acts as a facade for all the model part, and the `db_manager.py` which responsoble for managing the database.
we used ZODB (database for python objects) in order to make detectors not limited for the current run of the server. we save pairs that maps the model_id (int) to a tuple contains the [Model data structure](RESTful-api.md#model-structure) and the Anomaly detector object.
All the anomaly detectors implement the same interface even though it is not declared in the code.
#### the controller:
that is the `app.py` file - it connects the HTTP requests to the model in the backend.
we used flask framework, in order the create the controller - each uri in the RESTful API is handled by a dedicated function, which uses the model to respond to the request.
the uri of the static resources are handled automatically, and the main page (`/`) returns the `index.html` file.

UML diagram of the backend (model + controller):
![web-app-view](readme-resources/backend.png?raw=TRUE "web-app")

#### the view:
it includes the `index.html` for the content of the web page, the `js` files for functionality and the `css` files for the design of the web page.
the `index.html` includes (refers to) the `js` files and the `css` files in order to use there methods and design.
[for more info of the view look here](View.md)

## Download and Set-Up Instruction:
the following tools are required, make sure you first install them:
- python - 3.5 and above
- flask framework
- ZODB - python library

the recommended way is to install python and pip. and then install the packages via pip:
```sh
pip install flask
pip install ZODB
```



To run the server run the `app.py` file:
```sh
python app.py
```
or: 
```sh 
python3 app.py
```
(depending on the way you run python 3)

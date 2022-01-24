# 18.10.2021

Finding team members

get Lego Box

try 'Hello World' Program on Brick

start basic wheel setup

try the different sensors and motors, read the data and try to control them.

switch to chains because we get a smaller turn radius

get basic program running that allows us to controll both tracks individually and start driving around (only manual control)

start attaching the sensors to the base

start cleaning up the code

# 25.10.2021

Finish major design decisions for robot

Experimented with phone holder

# 08.11.2021

Started writing code
 - Menu
 - Abstraction of Ultrasound Motor, Movement
 - BridgeCrossing, FindAndPush

At home:
 - Abstracted more hardware classes
 - Improve Algorithm selection

# 15.11.2021

Improved follow the line code
Started implementation of gap skipping
Switched from Pilot to own control of motors
Improved rigidity of roboter

# 22.11.2021

Implemented Color detection
Added rolling average to line following and fine tuned configuration value
Added motor stalled check to find and push
Improved construction of roboter further by adding a third wheel per side

# 29.11.2021

Implemented improved Find and Push
Experiments with sensors
Reduced friction on both chains
Fixed stop problem of the roboter
Added some weight at theback of the roboter

# 06.12.2021

Implemented Yolo find Crosses
Hardcoded Bridge cross
Experiment in find and push (didn't Work)

# 13.12.2021

Simplified Color detection by using colorid
Implemented Zic-Zac find Crosses
Further fine tuned find and push
Added bumper to roboter

# 20.12.2021

Implemented auto correcting foreward method
Implemented condition system instead of check loops
Added new Bridge Crosssing algorithm
Added circumnavigate box in line following

# 10.01.2022

Trouble with robot and lejos => couldnt compile, wlan not working, brick not starting
Bridge Crossing => different exits depending if we bump the walls or perfectly hit the exit
Exit codes => codes for subroutines to easily share the exit condition
Line following => reimplemented with better setup for easier subroutine switching

# 17.01.2022
Rewrote FindAndPush with Conditions
More technical issues with the bricks
Increased Speed on Bridge Crossing
Improved End detection of Bridge Crossing
Improved Line Following

# 24.01.2022
Lots of small improvements
Continued improving Line Following
Ported Documentation to Ilias

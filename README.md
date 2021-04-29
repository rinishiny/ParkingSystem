# ParkingSystem
Developed a parking system for Round 2
-----------------------------------------------------------------------------
The following code has been written using Java 1.8.0_281.

Please do make sure the supported java version exists in the target machine.

-----------------------------------------------------------------------------
To execute the .java on Windows/Ubuntu/Mac, follow the commands as given below:

javac Parking.java

java Parking

-----------------------------------------------------------------------------
Input parameters:
Please provide the file name as the first input.
EG : F:\ParkingSystem

Please provide the name of the input file as the second input.
EG: input.txt

Rest of the input is taken from the input.txt.

-----------------------------------------------------------------------------
--- Logic used --
I have used hashmaps to store and retrieve the slots and its corresponding vehicle number.
To go further, I have used hashmaps to also create a link between the vehicle number(key) and the age of the driver (value).
Whenever user enters or leaves the parking lot, the hashmap is updated accordingly.

I have added comments for every function's purpose.


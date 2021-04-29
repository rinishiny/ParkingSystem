import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.*;  
public class Parking
{	
	public static Map<Integer,String> slotStatus=new HashMap<Integer,String>();  //slot and its availability is stored
	public static Map<String,Integer> vehicleDriver=new HashMap<String,Integer> (); //reg no along with the driver's age
	public static Map<Integer,String> slotReg=new HashMap<Integer,String>();  //The slot number with corresponding reg no
	
//This method is used to park the vehicles in the parking lot
public static void parkVehicle(String regno)
{
	ArrayList<Integer> sortedKeys = new ArrayList<Integer>(); 
 
        for (Map.Entry<Integer,String> en : slotStatus.entrySet()) {
			if(en.getValue() == "Available")
			{
				sortedKeys.add(en.getKey());
			}            
        }
		//sort the slots to get the nearest available spot near the entry 
		Collections.sort(sortedKeys); 
		if(sortedKeys.isEmpty())
		{
			System.out.print("Parking lot is full");
		}
		else 
		{
			slotStatus.put(sortedKeys.get(0), "Occupied");
			System.out.println("Car with vehicle registration number \"" + regno + "\" has been parked at slot number " + sortedKeys.get(0));
			slotReg.put(sortedKeys.get(0),regno); 
			sortedKeys.remove(0);
		}
}

public static void LeaveVehicle(int slotNumber)
{
	//Mark the slot available once a vehicle leaves the parking lot
	for (Map.Entry<Integer,String> en : slotStatus.entrySet()) {
			if(en.getKey() == slotNumber)
			{
				slotStatus.put(slotNumber, "Available");
			}            
        }

	for (Map.Entry<Integer,String> en1 : slotReg.entrySet()) {
			if(en1.getKey() == slotNumber)
			{			  
				 for (Map.Entry<String,Integer> en2 : vehicleDriver.entrySet()) {
					if(en2.getKey() == en1.getValue())
					{
						 System.out.println("Slot number " + slotNumber + " vacated " + ", the car with vehicle registration number \"" + en1.getValue()  + "\" left the space, the driver of the car was of age " + en2.getValue());
					}            
				}
			}            
        }
		
	
}
//This method is used to find the slot for a given reg number
public static void FindTheSlotNumber(String vehicleNo)
{
	boolean isFound = false;
	for (Map.Entry<Integer,String> en : slotReg.entrySet()) {
			String str = en.getValue();
			if(str.equals(vehicleNo))
			{
			isFound = true;
			System.out.println(en.getKey());
			}         
        }
	if (!isFound)
	{
		System.out.println("No parked car matches the query");
	}
}
//This method is used to get the reg number given a particular age of a driver
public static void FindTheRegNumber(int dage)
{
ArrayList<String> listOfRegNumber = new ArrayList<String >(); 	
 
        for (Map.Entry<String,Integer> en : vehicleDriver.entrySet()) {
			if(en.getValue() == dage)
				{
					listOfRegNumber.add(en.getKey());
				}           
        }
	if (listOfRegNumber.isEmpty())
		{
			System.out.println("No parked car matches the query");
		}
	else
		{
			System.out.println("The vehicles parked by a driver with age " + dage + "are ");
			for(int i=0;i<listOfRegNumber.size();i++)
			{
				System.out.print(listOfRegNumber.get(i) + ",");
			}
		}
	
}

//driver function
public static void main(String args[])
{
	int n = 0;
	int slotToLeave = 0;
	int dAge = 0;
 	
	Scanner scan = new Scanner(System.in);
	System.out.println("Enter file path with the file name");
	String path = scan.nextLine();
	String filename = scan.nextLine();
	try {
	  //Reading the input file
	  File myObj = new File(filename);
	  if(path.contains("\\"))
	  {
		 path = path.replaceAll("\\\\", "/");
		 path = path.concat("/" + filename);	
	  }
	  else
	  {
		 path = path.concat("/" + filename);	
	  }
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
		
		//Create the parking lot
		if (data.startsWith("Create_parking_lot"))
		{
			String[] splitStr = data.split("\\s+");
			n = Integer.parseInt(splitStr[1]);  
			System.out.println("Created parking of " + n + " slots");
			
			for(int i = 1; i<=n; i++)
			{
				slotStatus.put(i,"Available");
			}
		}
		
		//Park the vehicle
		if (data.startsWith("Park"))
		{
			String[] splitStr = data.split("\\s+");
			String regno = splitStr[1];
			int driver_age = Integer.parseInt(splitStr[3]); 
			vehicleDriver.put(regno, driver_age);		
			parkVehicle(regno);		
		}
		
		//Vehicle leaves the parking lot
		if (data.startsWith("Leave"))
		{
			String[] splitStr = data.split("\\s+");
			slotToLeave = Integer.parseInt(splitStr[1]);
			LeaveVehicle(slotToLeave);
		}
		
		//Retrieve the slot number for a particular age
		if (data.startsWith("Slot_numbers_for_driver_of_age"))
		{
			String[] splitStr = data.split("\\s+");
			dAge = Integer.parseInt(splitStr[1]);  
			ArrayList<String> reg = new ArrayList<String>();
			ArrayList<Integer> slot = new ArrayList<Integer>();
			
			for (Map.Entry<String,Integer> en : vehicleDriver.entrySet()) 
			{
				if(en.getValue() == dAge)
				{
					reg.add(en.getKey());
				}
			}
					for (Map.Entry<Integer,String> en1 : slotReg.entrySet()) 
						{
							for(int i=0;i<reg.size();i++)
							{
								if(en1.getValue() == reg.get(i))
									{
										slot.add(en1.getKey()); 
									} 
							}							
						}
				
				if(reg.isEmpty())
				{
					System.out.println("No parked car matches the query");
				}
				else
				{
					for(int i=0;i<slot.size();i++)
					{
						System.out.print(slot.get(i) + ",");
					}
					System.out.print("\n");
				}
				
		}
		//To get the slot number of a given reg no
		if (data.startsWith("Slot_number_for_car_with_number"))
		{
			String[] splitStr = data.split("\\s+");
			String vehicleNo = splitStr[1];
			FindTheSlotNumber(vehicleNo);
		}
		//To get all the reg no given a specific age
		if (data.startsWith("Vehicle_registration_number_for_driver_of_age"))
		{
			String[] splitStr = data.split("\\s+");
			int dage = Integer.parseInt(splitStr[1]);
			FindTheRegNumber(dage);
		}		
		}
			
      }
	
	  catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
}
}

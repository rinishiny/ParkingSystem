import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.*;  
public class Parking
{
public static Map<Integer,String> parkVehicle(Map<String,Integer> vehicleDriver, 
String regno, Map<Integer,String> slotStatus)
{
	ArrayList<Integer> sortedKeys = new ArrayList<Integer>();
	Map<Integer,String> slotReg=new HashMap<Integer,String>();  	

	Map<Integer, Map<String,Integer>> slotVehicle = new HashMap<Integer,Map<String,Integer>>();
 
        for (Map.Entry<Integer,String> en : slotStatus.entrySet()) {
			if(en.getValue() == "Available")
			{
				sortedKeys.add(en.getKey());
			}            
        }
		Collections.sort(sortedKeys); 
		if(sortedKeys.isEmpty())
		{
			System.out.print("Parking lot is full");
		}
		else 
		{
			int nextSlot = sortedKeys.get(0);
			slotStatus.put(nextSlot, "Occupied");
			slotVehicle.put(nextSlot, vehicleDriver);
			
			for (Map.Entry<String,Integer> en : vehicleDriver.entrySet()) {
			if(en.getKey() == regno)
			{
				int dAge = en.getValue();
			}            
        }
			System.out.println("Car with vehicle registration number " + regno +" has been parked at slot number " + sortedKeys.get(0));
			sortedKeys.remove(0);
			slotReg.put(sortedKeys.get(0),regno); 
		}
		
		return slotReg;
}

public static Map<Integer,String> LeaveVehicle(Map<Integer,String> slotStatus, int slotNumber, Map<Integer,String> slotReg, Map<String,Integer> vehicleDrive)
{
String reg = null;
int dage = 0;
	
	for (Map.Entry<Integer,String> en : slotStatus.entrySet()) {
			if(en.getKey() == slotNumber)
			{
				slotStatus.put(slotNumber, "Available");
			}            
        }
	
	for (Map.Entry<Integer,String> en1 : slotReg.entrySet()) {
			if(en1.getKey() == slotNumber)
			{
				  reg = en1.getValue();				  
				 for (Map.Entry<String,Integer> en2 : vehicleDrive.entrySet()) {
					if(en2.getKey() == reg)
					{
						  dage = en2.getValue();
						 
						 System.out.println("Slot number " + slotNumber + " vacated " + ", the car with vehicle registration number" + reg + " left the space, the driver of the car was of age " + dage);
					}            
				}
			}            
        }
		
	return slotStatus;
}

//driver function
public static void main(String args[])
{
	int n = 0;
	int slotToLeave = 0;
	int driver_age = 0;
	String regno = null;
	int dAge = 0;
	
	Map<Integer,String> slotStatus=new HashMap<Integer,String>();  
	Map<String,Integer> vehicleDriver=new HashMap<String,Integer> (); 
	Map<Integer,String> slotReg=new HashMap<Integer,String>(); 
 	
	Scanner scan = new Scanner(System.in);
	System.out.println("Enter file path with the file name");
	String filename = scan.nextLine();
	try {
	  //Reading the input file
	  File myObj = new File(filename);
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
			regno = splitStr[1];
			driver_age = Integer.parseInt(splitStr[3]); 
			vehicleDriver.put(regno, driver_age);		
			slotReg = parkVehicle(vehicleDriver,regno, slotStatus);		
		}
		
		//Vehicle leaves the parking lot
		if (data.startsWith("Leave"))
		{
			String[] splitStr = data.split("\\s+");
			slotToLeave = Integer.parseInt(splitStr[1]);
			slotStatus = LeaveVehicle(slotStatus, slotToLeave, slotReg, vehicleDriver);
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
				System.out.println("Slot numbers of the age" + dAge);
				for(int i=0;i<slot.size();i++)
					{
						System.out.println(slot.get(i) + ",");
					}
		}            
		}
			
      }
	
	  catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
}
}

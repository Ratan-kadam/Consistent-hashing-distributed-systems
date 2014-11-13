package edu.sjsu.cmpe.cache.client.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;


public class Client {

    public static void main(String[] args) throws Exception {
    	System.out.println("Starting Cache Client...");
  //******************************************************************************************************** 
  //                          Creation of 3 cache clients  
  //*********************************************************************************************************
    	CacheServiceInterface Server3000 = new DistributedCacheService("http://localhost:3000");
    	System.out.println("Starting server 1...");
    	CacheServiceInterface Server3001 = new DistributedCacheService("http://localhost:3001");
    	System.out.println("Starting server 2...");
    	CacheServiceInterface Server3002 = new DistributedCacheService("http://localhost:3002");
    	System.out.println("Starting server 3...");
   //******************************************************************************************************** 
   // putting all athe servers in arraylist - this will help to get the total number of servers 
   //******************************************************************************************************** 
    	
    	List<CacheServiceInterface> listOfServers = new ArrayList<CacheServiceInterface>();
    	listOfServers.add(Server3000);
    	listOfServers.add(Server3001);
    	listOfServers.add(Server3002);
    	
    //******************************************************************************************************** 
  	// Creation of Cache data which need to be distribute among server3000,3001,3002
    //******************************************************************************************************** 	
    
    	HashMap< Integer, String> dataCollection = new HashMap<Integer, String>();
    	dataCollection.put(1, "A");
    	dataCollection.put(2, "B");
    	dataCollection.put(3, "C");
    	dataCollection.put(4, "D");
    	dataCollection.put(5, "E");
    	dataCollection.put(6, "F");
    	dataCollection.put(7, "G");
    	dataCollection.put(8, "H");
    	dataCollection.put(9, "I");
    	dataCollection.put(10, "J");
    
    	
    	// For obtaining the message digest we have used hashing Method = SHA-1.
    	
    	MessageDigest digestType= MessageDigest.getInstance("SHA-1");
    	final ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE);
    	
   //******************************************************************************************************** 
   // Logic for calculating the hash and putting into particular server cache
   //******************************************************************************************************** 
    	int i;
    	//String j;
    	for ( i=1; i<= 10; i++ ) 
    		
    	{
    	
    	buffer.clear();	
    	buffer.putLong(i);
    
        byte [] digestKeyValue = digestType.digest(buffer.array());
        BigInteger digestKeyValueInt = new BigInteger(1,digestKeyValue);
		BigInteger serverSelectionCount = digestKeyValueInt.mod(new BigInteger(String.valueOf(listOfServers.size())));
		System.out.println(" ");
		System.out.print("Putting Element :");
		System.out.print(dataCollection.get(i));
		System.out.println(" & Key:" + i );
		System.out.println("**To cache bucket no :"+ serverSelectionCount);
		
		int CheckBucket = serverSelectionCount.intValue();
		//System.out.println("$$$CheckBucket=" + CheckBucket);
        if (CheckBucket ==  0)
        { 
        	System.out.println("Sending data to server Cache - Server 3000");
        }
        if (CheckBucket == 1)
        { 
        	System.out.println("Sending data to server Cache - Server 3001");
        }
        if (CheckBucket ==  2 )
        { 
        	System.out.println("Sending data to server Cache - Server 3002");
        }
        
        listOfServers.get(serverSelectionCount.intValue()).put(i,dataCollection.get(i));;
        
        System.out.print("________________________________________________");
    	}
   //******************************************************************************************************** 
  // logic for locating the object stored at previous stage.
  //******************************************************************************************************** 
    	     for(i = 1;  i <= 10 ; i++ ) {
    	    	 buffer.clear();	
    	     	 buffer.putLong(i);
    	    	 byte [] digestKeyValue = digestType.digest(buffer.array());
    	    	 BigInteger digestKeyValueInt = new BigInteger(1,digestKeyValue);
    	 		BigInteger serverSelectionCount = digestKeyValueInt.mod(new BigInteger(String.valueOf(listOfServers.size())));
    	 		 System.out.println(" ");
    	 		 System.out.print("###retriving  element :");
    	         System.out.print( listOfServers.get(serverSelectionCount.intValue()).get(i));
    	         System.out.println(" & Key:" + i );
    	         System.out.println("from server Bucket cache no. :" + serverSelectionCount.intValue());
    	        // System.out.println("from server :" +  listOfServers.get(serverSelectionCount.intValue()));
    	         int CheckBucket1 = serverSelectionCount.intValue();
    	 		
    	         if (CheckBucket1 ==  0)
    	         { 
    	         	System.out.println("Retriving data from server Cache - Server 3000");
    	         }
    	         if (CheckBucket1 == 1)
    	         { 
    	         	System.out.println("Retriving data from server Cache - Server 3001");
    	         }
    	         if (CheckBucket1 ==  2 )
    	         { 
    	         	System.out.println("Retriving data from server Cache - Server 3002");
    	         }
    	         System.out.println("________________________________________________");
    	     }
    	
    	
 
      System.out.println("Existing Cache Client...");
    
    }

}

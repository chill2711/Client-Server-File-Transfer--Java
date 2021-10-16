/**
 * CLient side code will send file 100 times to server side and calculate the time for each file to send and a total file send time
 *
 * @author Coleman Hill
 * @version September 30, 2021
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.FileInputStream;
//import java.io.BufferedReader;
//import java.io.FileReader;

public class TCPClient {

    public static void main(String argv[]) throws Exception
    {
        // declare file to be sent
        //String file = "/Users/ColeHill/Documents/Computer Networks CPSC 4157/Programming Proejct/Test1 - large.txt";
        String file = "Test1 - large.txt";
        //System.out.println("Looking for " + file + " file."); testing

        //creating file object
        File clientFile = new File(file);

        //total time for calculation
        long totalTime = 0;
        //loop to send file 100 times
        for(int i = 0; i < 100; i++){
            //time for file
            long fileTime = 0;
            //current time
            long startTime = System.currentTimeMillis();

            Scanner clientScanner = new Scanner(clientFile);

            //creating socket
            Socket clientSocket = new Socket("192.168.4.33",6000);

            System.out.println("\nI am connecting to the server side: " + clientSocket.getLocalAddress());
            System.out.println("I am sending " + clientFile + " file for the " + (i+1) + "th time.");

            //System.out.println("File transmission start time: " + startTime);

            DataOutputStream sentToServer = new DataOutputStream(clientSocket.getOutputStream());

            while(clientScanner.hasNextLine()){
                sentToServer.writeBytes(clientScanner.nextLine() + "\n");

            }
            clientScanner.close();
            clientSocket.close();
            System.out.println("I am finishing sending file " + clientFile + " for the " + (i+1) + " time. ");
            long endTime = System.currentTimeMillis();

            //System.out.println("File transmission end time: " + endTime);
            fileTime = endTime - startTime;
            System.out.println("The time used in milliseconds to send "+ clientFile + " for " + (i+1) + "th time is: " + fileTime);
            totalTime += fileTime;
            System.out.println("The "+ clientFile+ " file sent " + (i+1) + " times.");
        }
        //System.out.println("\nThe total time to send the file 100 times: " + totalTime + " milliseconds.");
        System.out.println("\nThe average time to send the file: " + clientFile + " in milliseconds is " + totalTime / 100 + "."+ totalTime%100+ " milliseconds");
        System.out.println("\nI am done.");

    }
}
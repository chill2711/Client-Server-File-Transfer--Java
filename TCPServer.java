
/**
 * Server side code will receive file from client side 100 times and calculate the milliseconds it takes to receive the file, the average receving time.
 * a total receive file time and calculate an error rate
 *
 * @author Coleman Hill
 * @version September 30, 2021
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;

public class TCPServer {
    public static void main(String argv[]) throws IOException
    {
        String clientSentence;
        Socket connectionSocket;
        BufferedReader receivedFromClient;
        long totalTime = 0;

        //String receivedFile = "/Users/ColeHill/Documents/Computer Networks CPSC 4157/Programming Proejct/Test1 - large.txt";
        //received file
        String receivedFile = "Test1 - large.txt";
        //where file will be copied
        String outputFile = "Test1 - copied.txt";

        //ready for connection
        ServerSocket serverSocket = new ServerSocket(6000);
        System.out.println("I am ready for any client side request.");
        //loop to receive file 100 times
        for(int i = 0; i < 100; i++){
            long fileTime = 0;
            //start time
            long startTime = System.currentTimeMillis();

            connectionSocket = serverSocket.accept();

            System.out.println("\nI am starting to receive the " + receivedFile + " for the " + (i+1) + "th time.");

            receivedFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            PrintWriter output = new PrintWriter(outputFile + (i+1) + ".txt");

            int lineCount = 0;
            //writing file 
            while(true){
                clientSentence = receivedFromClient.readLine();

                if(clientSentence == null){
                    break;
                }

                output.println(clientSentence);
                lineCount++;
                //System.out.println("I have received: " + lineCount + "lines.");

            }
            output.close();

            connectionSocket.close();
            //end time
            long endTime = System.currentTimeMillis();
            
            //time to transfer file
            fileTime = endTime - startTime;
            System.out.println("I am finishing receiving the " + receivedFile + " for the " + (i+1) + "th time.");

            System.out.println("The time used in milliseconds to receive " + receivedFile + " in milliseconds is :" + Math.abs(fileTime));
            totalTime += fileTime;

            //System.out.println("I am finishing receiving the " + receivedFile + "for the " + (i+1) + "th time.");

        }
        //System.out.println("I am done receiving files.");
        serverSocket.close();

        int errorCount = 0;

        File serverFile = new File(receivedFile);
        //checking files have same contents
        for(int i = 0; i < 100; i++){
            File clientFile = new File(outputFile + (i+1) + ".txt");
            Scanner serverScanner = new Scanner(serverFile);
            Scanner clientScanner = new Scanner(clientFile);

            boolean success;

            while(serverScanner.hasNextLine() && clientScanner.hasNextLine()){
                success = serverScanner.nextLine().equals(clientScanner.nextLine());

                if(!(success)){
                    System.out.println("File" + outputFile + (i+1) + ".txt has error");
                    errorCount++;
                    break;
                }
            }
            serverScanner.close();
            clientScanner.close();

        }
        System.out.println("\nThe failure rate is " + errorCount + "/100");
        System.out.println("\nThe average time to receive the file: " + (totalTime / 100) + " milliseconds");
        System.out.println("\nI am done.");

    }
}
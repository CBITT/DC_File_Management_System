import javax.swing.*;
import java.io.*;
import java.net.SocketException;
import java.util.Scanner;

/**
 * This module contains the application logic of an echo server
 * which uses a connectionless datagram socket for interprocess
 * communication.
 * @author Csaba Bango
 * @author M. L. Liu
 */

public class EchoServer1 {


    public static int portNum = 7;


    public static void main(String[] args) {
        try {
            MyServerDatagramSocket mySocket = new MyServerDatagramSocket(portNum);
            System.out.println("Server is listening...");
            while (true) {  // forever loop
                DatagramMessage request =
                        mySocket.receiveMessageAndSender();
                System.out.println("Request received");
                String message = request.getMessage( );
                System.out.println("message received: "+ message);

                String newMessage = generateResponse(message);
                mySocket.sendMessage(request.getAddress( ),
                        request.getPort( ), newMessage);
            } //end while
        } // end try
        catch (Exception ex) {
            ex.printStackTrace( );
        } // end catch
    } //end main

    public static String generateResponse( String message)
            throws SocketException, IOException {

        String response = "";
        //split up string message
        String[] parts = message.split(" ");
        String msg = parts[0];
        String code = parts[1];
        String username = parts[2];
        String psw = parts[3];
        String fileContent = parts[4];

        if(msg.equals("login")) {
                response = authPsw(psw);
        }
        if (msg.equals("logout")){
            try{
                code = "113";
                response = code + " Logged out";
            }
            catch (Exception e){
                code = "910";
                response = code + " Error while shutting down server.";
            }
        }
        if (msg.equals("upload")){
            try{
            code = "202";
            response = code + " Upload successful.\nClose both windows to see updated Uploads folder.";

            //create folder
            File uploadsFolder = new File("uploads");

            if (!uploadsFolder.exists()) {
                System.out.println("creating directory: " + uploadsFolder.getName());
                boolean found = false;

                try{
                    uploadsFolder.mkdir();
                    found = true;
                    System.out.println("Uploads folder already created.");
                }
                catch(SecurityException se){
                    System.out.println("cannot create new Uploads file.");
                }
                if(found) {
                    System.out.println("Uploads folder newly created.");

                    //put content of message in file
                    createFileInFolder(username, fileContent);
                }
            }
            else {
                    createFileInFolder(username,fileContent);
            }
            }//end try
            catch (Exception e){
                code = "920";
                response = code + " Upload failed to execute.";
            }
        }
        if (msg.equals("download")){

            try {
                String userfile2 = username;
                String content = new Scanner(new File("path/uploads/"+userfile2+".txt")).useDelimiter("\\src").next();
                System.out.println(content);
                code = "302";
                response = code + " Downloaded"+" "+ username+" "+ content;
            }
           catch (Exception e){
               code = "930";
               response = code + " Failed to download.";
           }

        }
        return response;
    } //end getResponse

    private static String authPsw(String psw) {
        String response;
        String code;
        if (psw.trim().length() < 8){
            code = "900";

            response = code + " Incorrect password. \n\nMust have minimum 8 characters.";
        }
        else {
            code = "102";
            response = code + " Login successful" ;
        }
        return response;
    }

    private static void createFileInFolder(String username, String fileContent) throws IOException {
        String userfile = username+".txt";
        File file = new File("path/uploads/"+userfile);
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(fileContent);

        writer.close();
    }


} // end class

//references:
//create directory: https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java


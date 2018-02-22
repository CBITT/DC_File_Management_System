
import javax.swing.*;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;


public class MyEchoClient {
    static final String endMessage = ".";
    public static  String hostName = "localhost";
    public static String portNum = "7";
    public static EchoClientHelper helper;
    public static String username;

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    } //end main

    public void login(boolean checked, String username, String psw) throws IOException {

        try {

            this.username = username;
            String loginMsg = "login";
            String loginCode = "100";
            String uploadMsg = "upload";
            String uploadCode = "200";

            //create a file if login with upload checked
            if (checked==true){

                String body = "username:" + username.toLowerCase() + "\npassword:" + psw;
                String nameOfFile = username + ".txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(nameOfFile));
                writer.write(body);
                writer.close();

                //convert file into a string
                String content = new Scanner(new File(nameOfFile)).useDelimiter("\\src").next();
                System.out.println(content);

                //put this content into the message
                String message = uploadMsg + " " + uploadCode + " " + username + " " + psw + " " + content;
                EchoClientHelper helper = new EchoClientHelper(hostName, portNum);
                String response = helper.getResponse(message);
                JOptionPane.showMessageDialog(null, response ,"Uploaded", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                String message = loginMsg + " " + loginCode + " " + username + " " + psw + " " + "";
                EchoClientHelper helper = new EchoClientHelper(hostName, portNum);
                String response = helper.getResponse(message);
                JOptionPane.showMessageDialog(null, response ,"Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Login failed","Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }
    public void logout() throws IOException {
        try {
            EchoClientHelper helper = new EchoClientHelper(hostName, portNum);
            String logoutMsg = "logout";
            String code = "110";
            String message = logoutMsg + " " + code + " " + "" + " " + "" + " " +"";

            String response = helper.getResponse(message);
            JOptionPane.showMessageDialog(null, response, "Server stopped.", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Logout failed","Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    public void logoutWithDownload(String username) throws IOException {
        String downloadMsg = "download";
        String downloadCode = "300";
        String message = downloadMsg + " " + downloadCode + " " + username + " " + "" + " " + "";

        EchoClientHelper helper = new EchoClientHelper(hostName, portNum);
        String response = helper.getResponse(message);

        //break down respose to pieces
        String[] parts = response.split(" ");
        String code = parts[0];
        String msg = parts[1];
        String  uname= parts[2];
        String contentDownload = parts[3];

        JOptionPane.showMessageDialog(null, code + " " + msg ,"Server message", JOptionPane.INFORMATION_MESSAGE);

        //create folder to receive the file to
        File downloadsFolder = new File("downloads");
        if (!downloadsFolder.exists()) {
            System.out.println("creating directory: " + downloadsFolder.getName());
            boolean found = false;

            try{
                downloadsFolder.mkdir();
                found = true;
                System.out.println("Downloads folder created.");
            }
            catch(SecurityException se){
                System.out.println("Downloads create new users file.");
            }
            if(found) {
                System.out.println("Downloads folder newly created.");
                createFileInFolder(uname, contentDownload);
            }
        }
        else {
                createFileInFolder(uname,contentDownload);
        }
    }
    private static void createFileInFolder(String username, String fileContent) throws IOException {
        String userfile = username+".txt";

        File file = new File("path/Downloads/"+userfile);
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(fileContent);
        writer.close();
    }
}

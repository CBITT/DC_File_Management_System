import javax.swing.*;
import java.net.*;
import java.io.*;

/**
 * This class is a module which provides the application logic
 * for an Echo client using connectionless datagram socket.
 * @author M. L. Liu
 */
public class EchoClientHelper {
    private MyClientDatagramSocket mySocket;
    private InetAddress serverHost;
    private int serverPort;
public static String response;

    EchoClientHelper(String hostName, String portNum)
            throws SocketException, UnknownHostException {
        this.serverHost = InetAddress.getByName(hostName);
        this.serverPort = Integer.parseInt(portNum);
        // instantiates a datagram socket for both sending
        // and receiving data
        this.mySocket = new MyClientDatagramSocket();
    }

    public String getResponse( String message)
            throws SocketException, IOException {
       try{
        mySocket.sendMessage( serverHost, serverPort, message);
        // now receive the response
        this.response = mySocket.receiveMessage();
        }
       catch (SocketException e){
           System.out.print(e);
           String code = "999";
           response = code + "Server down.";
           this.response = mySocket.receiveMessage();
       }
        return response;
    }

    public void done( ) throws SocketException {
        mySocket.close( );
    }  //end done
} //end class

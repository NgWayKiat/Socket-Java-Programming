package Classes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class clsServerSocket {
    private clsLogger objLogger = new clsLogger();
    private Socket s = null;
    private ServerSocket srvSok = null;
    private Socket sok = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    private String m = "";
    private String sMsg = "";
    private final int iPort = 6000;
    byte[] bs = new byte[DEFAULT_BUFFER_SIZE];
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public clsServerSocket() {
        try {
            sMsg = "Server Port(" + iPort +"). Socket connection in progress...";
            srvSok = new ServerSocket(iPort);
            objLogger.getLogger("clsServerSocket>Connect", sMsg, 1,1);
            System.out.println("Server Socket is Started");
            sok = srvSok.accept();

            in = new DataInputStream(new BufferedInputStream(sok.getInputStream()));
            String m = "";

            // Reads message from client until "STOP_COMM" is sent
            while (!m.equals("STOP_COMM"))
            {
                try
                {
                    //m = in.readUTF(); DEFAULT_BUFFER_SIZE
                    bs  = new byte[DEFAULT_BUFFER_SIZE];
                    in.read(bs);
                    String sMsg = "";
                    for (byte b : bs)
                    {
                        if(b == 0) break;
                        char c = (char)b;
                        sMsg += c;
                    }
                    System.out.println(sMsg);
                    out = new DataOutputStream(sok.getOutputStream());
                    byte[] result = getMessage().getBytes();
                    sMsg = "";
                    for (byte b : result)
                    {
                        if(b == 0) break;
                        char c = (char)b;
                        sMsg += c;
                    }
                    System.out.println(sMsg);
                    out.write(result);
                    //out.writeUTF(getMessage());
                    //out.writeUTF("Hello Client: Do not disturb!!!!");
                }
                catch(IOException i)
                {
                    System.out.println(i);
                    objLogger.getLogger("clsServerSocket>Connect", i.toString(), 1,1);
                    // Reset Connection: Close connection and reinitialize connection setting
                    srvSok.close();
                    sok.close();
                    in.close();
                    new clsServerSocket();
                }
            }

            System.out.println("Closing connection");

            // Close connection
            sok.close();
            in.close();

        } catch (UnknownHostException u) {
            System.out.println(u);
            objLogger.getLogger("clsServerSocket>Connect", u.toString(), 1,1);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            objLogger.getLogger("clsServerSocket>Connect", i.toString(), 1,1);
            return;
        }
    }
    
    public String getMessage()
    {
        String sRplMsg = "";
        int iCount = 0;
        
        for(int i = 0; i < 100; i++)
        {
            iCount += i;
        }
        
        sRplMsg = "Get the count: " + Integer.toString(iCount);

        return sRplMsg;
    }

    public static void main(String args[])
    {
        clsServerSocket s = new clsServerSocket();
    }
}

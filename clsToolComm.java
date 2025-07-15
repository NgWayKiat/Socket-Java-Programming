package Classes;

import main.Default;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class clsToolComm {
    private clsLogger objLogger = new clsLogger();

    private Default dfg;
    private Socket sok = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private String m = "";
    private String sMsg = "";
    private final String sIPAddress = "127.0.0.1";
    private final int iPort = 5000;
    public void Connect() {
        try {
            sMsg = "IPAddress(" + sIPAddress + ") Port(" + iPort +"). Socket connection in progress...";
            sok = new Socket("localhost", iPort);
            objLogger.getLogger("clsToolComm>Connect", sMsg, 1,1);
            System.out.println("Socket connection in progress...");

            if (sok.isConnected())
            {
                objLogger.getLogger("clsToolComm>Connect", "Socket is connected.", 1,1);
                System.out.println("Socket is connected.");
                in = new DataInputStream(System.in);
                out = new DataOutputStream(sok.getOutputStream());

                while (!m.equals("STOP_COMM")) {
                    try {
                        m = in.readLine();
                        objLogger.getLogger("clsToolComm>Connect", m, 1,1);
                        out.writeUTF(m);
                        System.out.println(m);
                        dfg = new Default();
                        System.out.println(dfg.txtAreaLog.getText());
                        dfg.setGUIText(m.toString());
                    }
                    catch (IOException i) {
                        objLogger.getLogger("clsToolComm>Connect", i.toString(), 1,1);
                        System.out.println(i);
                    }
                }

                // Close the connection
                try {
                    in.close();
                    out.close();
                    sok.close();
                }
                catch (IOException i) {
                    System.out.println(i);
                }
            }
            else
            {
                objLogger.getLogger("clsToolComm>Connect", "Socket is failed to connected.", 1,1);
                System.out.println("Socket is failed to connect.");
                return;
            }

        } catch (UnknownHostException u) {
            System.out.println(u);
            objLogger.getLogger("clsToolComm>Connect", u.toString(), 1,1);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            objLogger.getLogger("clsToolComm>Connect", i.toString(), 1,1);
            return;
        }
    }
}

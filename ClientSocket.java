package ToolComm;

import Interface.iSocket;
import java.io.*;
import java.net.Socket;

public class ClientSocket implements iSocket {

    private String sRemoteIPAddress = "";
    private int iRemotePort = 0;

    private String sCommand = "";
    private Socket sok = null;

    //Declare In (Input) and Out (Output) stream from socket
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public ClientSocket(String IPAddress, int iPort)
    {
        this.sRemoteIPAddress = IPAddress;
        this.iRemotePort = iPort;
    }

    public void setRemoteIPAddress(String IPAddress)
    {
        this.sRemoteIPAddress = IPAddress;
    }

    public void setRemotePort(int Port)
    {
        this.iRemotePort = Port;
    }

    public String getRemoteIPAddress()
    {
        return this.sRemoteIPAddress;
    }

    public int getRemotePort()
    {
        return this.iRemotePort;
    }

    public int Connect()
    {
        int iConnect = 0;

        try {
            sok = new Socket(sRemoteIPAddress, iRemotePort);

            if(sok.isConnected())
            {
                //Reply Success Socket Connection
                iConnect = 1;

                //Initiate In (Input) and Out (Output) stream from socket
                out = new DataOutputStream(sok.getOutputStream());
                in =  new DataInputStream(new BufferedInputStream(sok.getInputStream()));
            }
            else
            {
                System.out.println("ClientSocket: Client Connection Failure");
                iConnect = -1;
            }

        }catch (Exception e)
        {
            iConnect = -1;
        }

        return iConnect;
    }

    public void Disconnect()
    {
        try {
            in.close();
            out.close();
            sok.close();
        }catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public String sendSocketMessage(String sSendMessage)
    {
        int iErr = 0;
        String sReplyMessage = "";

        if (sok == null || !sok.isConnected())
        {
            iErr = Connect();
        }

        if (iErr < 0)
        {
            System.out.println("ClientSocket: Socket Connection Failure.");
            return sReplyMessage;
        }

        try {
            sReplyMessage = "";
            while(sReplyMessage == "") {
                out.writeUTF(sSendMessage);
                System.out.println("Send To Server: " + sSendMessage);
                sReplyMessage = in.readUTF();
                if (sReplyMessage != null || !(sReplyMessage).equals(""))
                {
                    System.out.println("Reply From Server: " + sReplyMessage);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            iErr = -1;
        }

        return sReplyMessage;
    }
}

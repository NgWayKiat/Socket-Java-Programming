package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Classes.*;
import ToolComm.ClientSocket;

public class Default {

    //Declare Object Customs Class
    private clsLogger objLogger = new clsLogger();
    private clsToolComm objToolCom = new clsToolComm();

    private ClientSocket objClientSocket;


    //Declare Form Variables
    public JButton btnSend;
    public JPanel frmMain;
    public JTextArea txtAreaLog;
    private JTextField txtSendMsg;

    public Default() {
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String replyMsg = "";
                setGUIText("ActionSend: " + txtSendMsg.getText());
                System.out.println("ActionSend: " + txtSendMsg.getText() );
                objLogger.getLogger("Send_Click_Action", txtSendMsg.getText(), 1,1);
                replyMsg = sendMsg(txtSendMsg.getText());
                setGUIText("ActionReply: " + replyMsg);
                System.out.println("ActionReply: " + replyMsg );
                //JOptionPane.showMessageDialog(null, objClsCommon.getDisplayName());
                objLogger.getLogger("Send_Click_Action", replyMsg, 1,1);
            }
        });
    }

    public void InitGUI()
    {
        JFrame frame = new JFrame("CIM APP");
        frame.setContentPane(frmMain);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setMinimumSize(new Dimension(500, 500));
        txtAreaLog.setPreferredSize(new Dimension(400, 400));
        txtAreaLog.setMinimumSize(new Dimension(400, 400));
        txtAreaLog.setBounds(0, 0, 400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setGUIText(String sTextArea)
    {
        String sDisplayMessage = "";
        String sLogTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        sDisplayMessage = sLogTimeStamp + " :" + sTextArea + "\n";
        txtAreaLog.append(sDisplayMessage);
    }

    public int ClientSocketConnect()
    {
        objClientSocket = new ClientSocket("localhost", 5000);
        return objClientSocket.Connect();
    }

    public String sendMsg(String Message)
    {
       return objClientSocket.sendSocketMessage(Message);
    }

    public static void main(String[] args) {
        int iErr = 0;
        Default dfg = new Default();
        dfg.InitGUI();
        dfg.setGUIText("Socket Connection Started....");
        iErr = dfg.ClientSocketConnect();
        if(iErr > 0)
        {
            dfg.setGUIText("Socket Connection Success....");
        }
        else
        {
            dfg.setGUIText("Socket Connection Failed....");
        }
    }

}

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.*;

public class ClientFrame extends JFrame {

    private static String username;
    private static Boolean isConnected = false;

    private static Socket sock;
    private static PrintWriter writer;              // for broadcasting the messages

    // GUI's objects
    private JButton b_anonymous;
    private static JButton b_connect;
    private JButton b_disconnect;
    private static JButton b_send;
    private JScrollPane jScrollPane1;
    private JLabel lb_address;
    private JLabel lb_name;
    private JLabel lb_password;
    private JLabel lb_port;
    private JLabel lb_username;
    static JTextArea ta_chat;
    private JTextField tf_address;
    static JTextField tf_chat;
    private JTextField tf_password;
    private JTextField tf_port;
    static JTextField tf_username;
    private GroupLayout layout;

    // constructor
    ClientFrame() {
        initComponents();
    }

    // this is to listen to threads
    private void ListenThread() {
        Thread thread = new Thread(new clientStart());
        thread.start();
    }

    // when the client connects
    private class clientStart implements Runnable {
        @Override
        public void run() {
            // we start by displaying this to the client
            ta_chat.append("Server: Hello "+tf_username.getText()+"....It's very nice to see you here...\n");
            ta_chat.append("Server: Here are the terms of reference. Do you accept? yes or no");
        }
    }

    // to initiate GUI's components
    private void initComponents() {

        lb_address = new JLabel();
        tf_address = new JTextField();
        lb_port = new JLabel();
        tf_port = new JTextField();
        lb_username = new JLabel();
        tf_username = new JTextField();
        lb_password = new JLabel();
        tf_password = new JTextField();
        b_connect = new JButton();
        b_disconnect = new JButton();
        b_anonymous = new JButton();
        jScrollPane1 = new JScrollPane();
        ta_chat = new JTextArea();
        tf_chat = new JTextField();
        b_send = new JButton();
        lb_name = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Client");
        setName("client"); // NOI18N
        setResizable(false);

        lb_address.setText("Address : ");
        tf_address.setText("localhost");
        lb_port.setText("Port :");
        tf_port.setText("9999");
        lb_username.setText("Username :");
        lb_password.setText("Password : ");
        b_connect.setText("Connect");

        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });

        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });

        b_anonymous.setText("Anonymous Login");


        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        ta_chat.setEditable(false);
        jScrollPane1.setViewportView(ta_chat);

        b_send.setText("SEND");
        b_send.setEnabled(false);
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        lb_name.setText("© rabia-soft.com ©");
        lb_name.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                                                        .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(tf_address)
                                                                        .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(lb_port)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(29, 29, 29)
                                                                .addComponent(b_connect, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(b_disconnect)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_name)
                                .addGap(236, 236, 236))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(b_disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(b_connect, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(lb_address)
                                                                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(tf_username, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lb_username)))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lb_port)
                                                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(b_send, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_name))
        );

        pack();

    }

    // to disconnect, it's called when DISCONNECT button is pressed
    private void disconnect(int ope){
        try {
            if (ope == 0){
                writer.println(username + ": is disconnected.:Disconnect");     // we broadcast this message so the server
                writer.flush();                                                 // can listen to it
                sock.close();
            }

            ta_chat.setText("");                                            // clear the chat box
            b_connect.setEnabled(true);                                     // enable CONNECT button
            b_send.setEnabled(false);                                       // disable SEND button
            isConnected = false;
            tf_username.setEditable(true);                                  // enable username textField

            state = TERMS_ACCEPT_OR_NOT;                                    // reset state to TERMS_ACCEPT_OR_NOT
        } catch (Exception ex) {
            ta_chat.append("Failed to disconnect. \n");                     // in case of an exception
        }
    }

    // when DISCONNECT button is pressed
    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {
        disconnect(0);
    }

    // when CONNECT button is pressed
    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {

        if (!isConnected) {
            // if not connected and username is not entered, and he tried to connect we display a dialog box telling
            // him/her to enter a name
            if (tf_username.getText().isEmpty())
                JOptionPane.showMessageDialog(getContentPane(), "Enter a user name please");

            // if username is entered
            else {
                username = tf_username.getText();               // get username
                tf_username.setEditable(false);                 // disable username textField
                b_send.setEnabled(true);                        // enable SEND button
                try {
                    sock = new Socket("localhost", 9999);       // initiate sock with specific address and port number
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ": is connected.:Connect");       // broadcast the message
                    writer.flush();                                             // flush it
                    isConnected = true;
                    b_connect.setEnabled(false);                                // disable CONNECT button
                } catch (Exception ex) {
                    ta_chat.append("Cannot Connect! Try Again. \n");
                    tf_username.setEditable(true);
                }

                ListenThread();
            }

        }
    }

    // when SEND button is pressed
    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {
        String inputTextField;                      // input from client
        if ((tf_chat.getText()).equals("")) {       // check if input field is empty (we cannot send empty string)
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
                inputTextField = tf_chat.getText();             // get the input from the client
                ta_chat.append("\n" + tf_username.getText() + ": " + inputTextField + "\n");

                processInput(inputTextField);                   // process the input and do what's necessary

                switch (inputTextField) {                       // this is in case the client chose to download something
                    case "1":                                   // download iTune.zip
                        writer.println(username + ":iTune:" + "Server Chosen");
                        writer.flush(); // flushes the buffer
                        break;
                    case "2":                                   // download ZoneAlarm.zip
                        writer.println(username + ":ZoneAlarm:" + "Server Chosen");
                        writer.flush(); // flushes the buffer
                        break;
                    case "3":                                   // download WinRar.zip
                        writer.println(username + ":WinRar:" + "Server Chosen");
                        writer.flush(); // flushes the buffer
                        break;
                    case "4":                                   // download Audacity.zip
                        writer.println(username + ":Audacity:" + "Server Chosen");
                        writer.flush(); // flushes the buffer
                        break;
                }
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }

    // those final variable are used in processInput method to keep track of the state
    private static final int TERMS_ACCEPT_OR_NOT = 1;
    private static final int TERMS_ACCEPTED = 2;
    private static final int DOWNLOADED_OR_NOTDOWNLOADED = 3;
    private static final int TERMINATED = 4;

    private static int state = TERMS_ACCEPT_OR_NOT;             // initially, the question to accept the terms or not
                                                                // is already displayed, thus state = TERMS_ACCEPT_OR_NOT

    // this to process the input from tf_chat when SEND button is pressed
    private void processInput(String theInput) throws MalformedURLException {
        theInput = theInput.toLowerCase();                  // ignoring lower and upper case
        switch (state) {
            case TERMS_ACCEPT_OR_NOT:
                switch (theInput) {
                    case "yes":
                        ta_chat.append("Select One of The Following Resources To Download From: \n\t1. iTune \n\t2. ZoneAlarm \n\t3. WinRar \n\t4. Audacity ");
                        state = TERMS_ACCEPTED;
                        break;
                    case "no":
                        ta_chat.append("Enter \"bye\" to exist, and \"enter\" to continue");
                        state = TERMINATED;
                        break;
                    default:
                        ta_chat.append("You're supposed to enter yes or no");
                        state = TERMS_ACCEPT_OR_NOT;
                        break;
                }
                break;

            case TERMS_ACCEPTED:
                switch (theInput) {
                    case "1":
                        ta_chat.append(" You are downloading iTune.zip ...");
                        downloadFile("iTune.zip");
                        break;
                    case "2":
                        ta_chat.append(" You are downloading ZoneAlarm.zip ...");
                        downloadFile("ZoneAlarm.zip");
                        break;
                    case "3":
                        ta_chat.append(" You are downloading WinRar.zip ...");
                        downloadFile("WinRar.zip");
                        break;
                    case "4":
                        ta_chat.append(" You are downloading Audacity.zip ...");
                        downloadFile("Audacity.zip");
                        break;
                    default:
                        ta_chat.append("you're supposed to enter either 1, 2, 3, or 4....Try again");
                        state = TERMS_ACCEPTED;
                        break;
                }
                break;

            case DOWNLOADED_OR_NOTDOWNLOADED:
                switch (theInput) {
                    case "bye":
                        dispose();
                        break;
                    case "enter":
                        ta_chat.append("Select One of The Following Resources To Download From: \n\t1. iTune \n\t2. ZoneAlarm \n\t3. WinRar \n\t4. Audacity ");
                        state = TERMS_ACCEPTED;
                        break;
                    default:
                        state = DOWNLOADED_OR_NOTDOWNLOADED;
                        break;
                }
                break;

            case TERMINATED:
                dispose();
                break;
        }
    }

    // this is to download a file
    private void downloadFile(String fileName) {
        // source link
        String sourceURL = "https://github.com/bismarabia/Calculator_app/archive/master.zip";

        try {
            URL website = new URL(sourceURL);
            Path targetPath = new File("E://" + File.separator + fileName).toPath();            // path where to save
            Files.copy(website.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);  // save the file
            ta_chat.append("\nThe File Has Been Downloaded Successfully...");                   // display this message
        } catch (IOException e) {
            // in case of no internet connection, interruption
            ta_chat.append("\nOoooops Sorry....Download Process Has Been Interrupted or Incomplete...\n");
        }

        // ask the client what to do, bye to exit and enter to continue
        ta_chat.append("\nEnter \"bye\" to exist, and \"enter\" to continue");

        // reset state to DOWNLOADED_OR_NOTDOWNLOADED
        state = DOWNLOADED_OR_NOTDOWNLOADED;

    }

    // main method
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }

}

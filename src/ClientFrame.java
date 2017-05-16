import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
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

    // those final variable are used in processInput method to keep track of the state
    private static final int TERMS_ACCEPT_OR_NOT = 1;
    private static final int TERMS_ACCEPTED = 2;
    private static final int DOWNLOADED_OR_NOTDOWNLOADED = 3;
    private static final int TERMINATED = 4;

    private static int state = TERMS_ACCEPT_OR_NOT;             // initially, the question to accept the terms or not
    // is already displayed, thus state = TERMS_ACCEPT_OR_NOT

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
            ta_chat.append("Server: Hello " + tf_username.getText() + "....It's very nice to see you here...\n");
            ta_chat.append("Server: Here Are The Terms of Service. Do you accept? yes or no");
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
        setName("client");
        setResizable(false);

        lb_address.setText("Address : ");
        tf_address.setText("172.16.13.238");
        lb_port.setText("Port :");
        tf_port.setText("9999");
        lb_username.setText("Username :");
        lb_password.setText("Password : ");
        b_connect.setText("Connect");

        b_connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });

        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
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
        b_send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        lb_name.setText("© rabia-soft.com ©");
        lb_name.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(tf_chat, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(b_send, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING)
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(lb_username, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                                                        .addComponent(lb_address, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(tf_address)
                                                                        .addComponent(tf_username, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(lb_port)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(tf_port, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(29, 29, 29)
                                                                .addComponent(b_connect, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(b_disconnect)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_name)
                                .addGap(236, 236, 236))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                .addComponent(b_disconnect, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(b_connect, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(lb_address)
                                                                        .addComponent(tf_address, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(tf_username, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lb_username)))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lb_port)
                                                        .addComponent(tf_port, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tf_chat, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(b_send, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_name))
        );

        pack();

    }

    // when CONNECT button is pressed
    private void b_connectActionPerformed(ActionEvent evt) {

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
                    sock = new Socket("172.16.13.238", 9999);               // initiate sock with specific address and port number
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ": is connected.:Connect");   // broadcast the message
                    writer.flush();                                         // flush it
                    isConnected = true;
                    b_connect.setEnabled(false);                            // disable CONNECT button
                } catch (Exception ex) {
                    ta_chat.append("Cannot Connect! Try Again. \n");
                    tf_username.setEditable(true);
                }

                ListenThread();
            }

        }
    }

    // when DISCONNECT button is pressed
    private void b_disconnectActionPerformed(ActionEvent evt) {
        try {
            writer.println(username + ": is disconnected. :Disconnect");     // we broadcast this message so the server
            writer.flush();                                                 // can listen to it
            sock.close();

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

    // when SEND button is pressed
    private void b_sendActionPerformed(ActionEvent evt) {
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
        try {
            // source link
            String sourceURL = "https://github.com/bismarabia/Calculator_app/archive/master.zip";
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
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }

}

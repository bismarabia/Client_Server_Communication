import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ClientFrame extends JFrame {

    private static String username;
    private static Boolean isConnected = false;

    private static Socket socket;
    private static PrintWriter writer;              // for broadcasting the messages

    // GUI's objects
    private static JButton connect_button;
    private JButton disconnect_button;
    private static JButton send_button;
    private JScrollPane jScrollPane1;
    private JLabel lb_address;
    private JLabel name_label;
    private JLabel lb_password;
    private JLabel lb_port;
    private JLabel lb_username;
    static JTextArea chat_box_client;
    private JTextField tf_address;
    static JTextField input_client;
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
        initGUIClient();
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
            chat_box_client.append("Server: Hello " + tf_username.getText() + "....It's very nice to see you here...\n");
            chat_box_client.append("Server: Here Are The Terms of Service. Do you accept? yes or no..type display to read the terms");
        }
    }

    // to initiate GUI's components
    private void initGUIClient() {

        lb_address = new JLabel();
        tf_address = new JTextField();
        lb_port = new JLabel();
        tf_port = new JTextField();
        lb_username = new JLabel();
        tf_username = new JTextField();
        lb_password = new JLabel();
        tf_password = new JTextField();
        connect_button = new JButton();
        disconnect_button = new JButton();
        jScrollPane1 = new JScrollPane();
        chat_box_client = new JTextArea();
        input_client = new JTextField();
        send_button = new JButton();
        name_label = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Client");
        setResizable(false);

        lb_address.setText("Address : ");
        tf_address.setText("172.16.13.238");
        lb_port.setText("Port :");
        tf_port.setText("9999");
        lb_username.setText("Username :");
        lb_password.setText("Password : ");
        connect_button.setText("Connect");

        connect_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });

        disconnect_button.setText("Disconnect");
        disconnect_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });

        chat_box_client.setColumns(20);
        chat_box_client.setRows(5);
        chat_box_client.setEditable(false);
        chat_box_client.setLineWrap(true);
        chat_box_client.setWrapStyleWord(true);
        jScrollPane1.setViewportView(chat_box_client);

        send_button.setText("SEND");
        send_button.setEnabled(false);
        send_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        name_label.setText("© rabia-soft.com ©");
        name_label.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(input_client, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(send_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                                .addComponent(connect_button, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(disconnect_button)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(name_label)
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
                                                                .addComponent(disconnect_button, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(connect_button, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
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
                                        .addComponent(input_client, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(send_button, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(name_label))
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
                send_button.setEnabled(true);                        // enable SEND button
                try {
                    socket = new Socket("172.16.13.238", 9999);               // initiate socket with specific address and port number
                    writer = new PrintWriter(socket.getOutputStream());
                    writer.println(username + ": is connected.:Connect");   // broadcast the message
                    writer.flush();                                         // flush it
                    isConnected = true;
                    connect_button.setEnabled(false);                            // disable CONNECT button
                } catch (Exception ex) {
                    chat_box_client.append("Cannot Connect! Try Again. \n");
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
            socket.close();

            chat_box_client.setText("");                                            // clear the chat box
            connect_button.setEnabled(true);                                     // enable CONNECT button
            send_button.setEnabled(false);                                       // disable SEND button
            isConnected = false;
            tf_username.setEditable(true);                                  // enable username textField

            state = TERMS_ACCEPT_OR_NOT;                                    // reset state to TERMS_ACCEPT_OR_NOT
        } catch (Exception ex) {
            chat_box_client.append("Failed to disconnect. \n");                     // in case of an exception
        }
    }

    // when SEND button is pressed
    private void b_sendActionPerformed(ActionEvent evt) {
        String inputTextField;                      // input from client
        if ((input_client.getText()).equals("")) {       // check if input field is empty (we cannot send empty string)
            input_client.setText("");
            input_client.requestFocus();
        } else {
            try {
                inputTextField = input_client.getText();             // get the input from the client
                chat_box_client.append("\n" + tf_username.getText() + ": " + inputTextField + "\n");

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
                chat_box_client.append("Message was not sent. \n");
            }
            input_client.setText("");
            input_client.requestFocus();
        }

        input_client.setText("");
        input_client.requestFocus();
    }

    // this to process the input from input_client when SEND button is pressed
    private void processInput(String theInput) throws MalformedURLException {
        theInput = theInput.toLowerCase();                  // ignoring lower and upper case
        switch (state) {
            case TERMS_ACCEPT_OR_NOT:
                switch (theInput) {
                    case "yes":
                        chat_box_client.append("Select One of The Following Resources To Download From: \n\t1. iTune \n\t2. ZoneAlarm \n\t3. WinRar \n\t4. Audacity ");
                        state = TERMS_ACCEPTED;
                        break;
                    case "no":
                        chat_box_client.append("Enter \"bye\" to exist, and \"enter\" to continue");
                        state = TERMINATED;
                        break;
                    case "display":
                        chat_box_client.append("Server: This is Client-Server desktop application, it has access to your internet connection, " +
                                "it allows to initiate a connection between you (client) and the server....If you accept this type " +
                                "yes; otherwise, type no...feel free to choose whatever you want.....");
                        state = TERMS_ACCEPT_OR_NOT;
                        break;
                    default:
                        chat_box_client.append("You're supposed to enter yes or no");
                        state = TERMS_ACCEPT_OR_NOT;
                        break;
                }
                break;

            case TERMS_ACCEPTED:
                switch (theInput) {
                    case "1":
                        chat_box_client.append(" You are downloading iTune.zip ...");
                        downloadFile("iTune.zip");
                        break;
                    case "2":
                        chat_box_client.append(" You are downloading ZoneAlarm.zip ...");
                        downloadFile("ZoneAlarm.zip");
                        break;
                    case "3":
                        chat_box_client.append(" You are downloading WinRar.zip ...");
                        downloadFile("WinRar.zip");
                        break;
                    case "4":
                        chat_box_client.append(" You are downloading Audacity.zip ...");
                        downloadFile("Audacity.zip");
                        break;
                    default:
                        chat_box_client.append("you're supposed to enter either 1, 2, 3, or 4....Try again");
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
                        chat_box_client.append("Select One of The Following Resources To Download From: \n\t1. iTune \n\t2. ZoneAlarm \n\t3. WinRar \n\t4. Audacity ");
                        state = TERMS_ACCEPTED;
                        break;
                    default:
                        chat_box_client.append("please enter \"bye\" or \"enter\"");
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
            chat_box_client.append("\nThe File Has Been Downloaded Successfully...");                   // display this message
        } catch (IOException e) {
            // in case of no internet connection, interruption
            chat_box_client.append("\nOoooops Sorry....Download Process Has Been Interrupted or Incomplete...\n");
        }

        // ask the client what to do, bye to exit and enter to continue
        chat_box_client.append("\nEnter \"bye\" to exist, and \"enter\" to continue");

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

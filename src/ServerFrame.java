import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerFrame extends JFrame {

    // Variables declaration
    ArrayList clientOutputStreams;
    private ArrayList<String> users;                // hold the users who're online
    private JButton clear_button;                        // clear the chat box
    private JButton end_button;                          // stop the server
    private JButton start_button;                        // start the server
    private JButton users_button;                        // to show online users
    private JScrollPane jScrollPane1;
    private JLabel name_label;                         // signature
    private JTextArea chat_box_server;                      // chat box

    private Socket clientSock;

    // the following are counters to count how many times we download from a specific server
    private static int countDownload_iTune = 0;
    private static int countDownload_ZoneAlarm = 0;
    private static int countDownload_WinRar = 0;
    private static int countDownload_Audacity = 0;

    // constructor
    private ServerFrame() {
        initGUIServer();
    }

    // the method is to initiate components of the GUI
    private void initGUIServer() {

        jScrollPane1 = new JScrollPane();
        chat_box_server = new JTextArea();
        start_button = new JButton();
        end_button = new JButton();
        users_button = new JButton();
        clear_button = new JButton();
        name_label = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Server");
        setResizable(false);

        chat_box_server.setColumns(20);
        chat_box_server.setRows(5);
        chat_box_server.setEditable(false);
        chat_box_server.setLineWrap(true);
        chat_box_server.setWrapStyleWord(true);
        jScrollPane1.setViewportView(chat_box_server);

        start_button.setText("START");
        start_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        end_button.setText("END");
        end_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        users_button.setText("Online Users");
        users_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });

        clear_button.setText("Clear");
        clear_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        name_label.setText("© rabia-soft.com ©");
        name_label.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(name_label)
                                .addGap(209, 209, 209))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(start_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(users_button, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                        .addComponent(clear_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(end_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(start_button, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(users_button, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(clear_button, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(end_button, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(name_label, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }

    // to start the server
    private class ServerStart implements Runnable {

        @Override
        public void run() {
            clientOutputStreams = new ArrayList();
            users = new ArrayList<>();

            try {
                // create socket for server and initialize
                ServerSocket serverSock = new ServerSocket(9999);
                while (true){
                    clientSock = serverSock.accept();
                    PrintWriter writerOut = new PrintWriter(clientSock.getOutputStream(), true);
                    clientOutputStreams.add(writerOut);

                    // activate the thread so it can listen to multiple users
                    Thread listener = new Thread(new ClientHandler(clientSock, writerOut));
                    listener.start();
                    chat_box_server.append("Connection Is Well Set Up... \n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                chat_box_server.append("Error making a connection. \n");
            }
        }
    }

    // this is to handle the clients' requests
    private class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                chat_box_server.append("Unexpected error while handling client... \n");
            }
        }

        // whenever SEND is clicked by the client this one is executed
        @Override
        public void run() {
            String message;
            String[] data;

            try {
                while ((message = reader.readLine()) != null) {
                    data = message.split(":");
                    /*  data will have three elements
                    * data[0] == username
                    * data[1] == message or status for connected/disconnected
                    * data[2] == operation (chat/connect/disconnect)
                    */

                    switch (data[2]) {
                        case "Connect":
                            chat_box_server.append(data[0] + data[1] + "\n");
                            userAdd(data[0]);
                            break;
                        case "Disconnect":
                            chat_box_server.append(data[0] + data[1] + "\n");
                            userRemove(data[0]);
                            break;
                        case "Server Chosen":
                            switch (data[1]) {
                                case "iTune":
                                    countDownload_iTune++;
                                    chat_box_server.append("(" + countDownload_iTune + ") times downloads from " + data[1] + " Server \n");
                                    break;
                                case "ZoneAlarm":
                                    countDownload_ZoneAlarm++;
                                    chat_box_server.append("(" + countDownload_ZoneAlarm + ") times downloads from " + data[1] + " Server \n");
                                    break;
                                case "WinRar":
                                    countDownload_WinRar++;
                                    chat_box_server.append("(" + countDownload_WinRar + ") times downloads from " + data[1] + " Server \n");
                                    break;
                                case "Audacity":
                                    countDownload_Audacity++;
                                    chat_box_server.append("(" + countDownload_Audacity + ") times downloads from " + data[1] + " Server \n");
                                    break;
                            }
                    }
                }
            } catch (Exception ex) {
                chat_box_server.append("Lost a connection. \n");
                clientOutputStreams.remove(client);
                System.out.println(ex.toString());
                ex.printStackTrace();
            }
        }

    }

    // this is when START button is clicked
    private void b_startActionPerformed(ActionEvent evt) {
        Thread starter = new Thread(new ServerStart());             // create the thread
        starter.start();                                            // start the thread

        chat_box_server.append("Server Has Started...\n");
    }

    // This is when ONLINE USERS button is clicked
    // It displays the online users
    private void b_usersActionPerformed(ActionEvent evt) {
        chat_box_server.append("Online users : \n");
        for (int i = 0; i < users.size(); i++)
            chat_box_server.append("\t" + (i + 1) + ") " + users.get(i) + "\n");


    }

    // this is when CLEAR button is clicked
    private void b_clearActionPerformed(ActionEvent evt) {
        chat_box_server.setText("");
    }

    // when END button is clicked
    private void b_endActionPerformed(ActionEvent evt) {
        try {
            chat_box_server.setText("Server Has Stopped....Bye!!!\n");
            Thread.sleep(2000);                 //2000 milliseconds is two second.
            clientSock.close();                 // close client's socket

            countDownload_iTune = 0;
            countDownload_ZoneAlarm = 0;
            countDownload_WinRar = 0;
            countDownload_Audacity = 0;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chat_box_server.setText("");                    // clear the chat box
    }

    // this is to add a client when he joined the server
    private void userAdd(String user) {
        users.add(user);
    }

    // this is to remove a client when s/he disconnect
    private void userRemove(String data) {
        users.remove(data);
    }

    // main method
    public static void main(String args[]) {

        Splash splash = new Splash();           // start the Splash
        splash.setVisible(true);                // displays the splash screen

        try {
            //sleep 4 seconds
            Thread.sleep(4000);
            splash.dispose();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new ServerFrame().setVisible(true);    // after some delay, it starts a ServerFrame
    }
}

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerFrame extends JFrame {

    // Variables declaration
    private ArrayList<String> users;                // hold the users who're online
    private JButton b_clear;                        // clear the chat box
    private JButton b_end;                          // stop the server
    private JButton b_start;                        // start the server
    private JButton b_users;                        // to show online users
    private JScrollPane jScrollPane1;
    private JLabel lb_name;                         // signature
    private JTextArea ta_chat;                      // chat box

    private Socket clientSock;

    // the following are counters to count how many times we download from a specific server
    private static int countDownload1 = 0;
    private static int countDownload2 = 0;
    private static int countDownload3 = 0;
    private static int countDownload4 = 0;

    // constructor
    private ServerFrame() {
        initGUI();
    }

    // the method is to initiate components of the GUI
    private void initGUI() {

        jScrollPane1 = new JScrollPane();
        ta_chat = new JTextArea();
        b_start = new JButton();
        b_end = new JButton();
        b_users = new JButton();
        b_clear = new JButton();
        lb_name = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Server");
        setResizable(false);

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        ta_chat.setEditable(false);
        jScrollPane1.setViewportView(ta_chat);

        b_start.setText("START");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });

        b_end.setText("END");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });

        b_users.setText("Online Users");
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });

        b_clear.setText("Clear");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });

        lb_name.setText("© rabia-soft.com ©");
        lb_name.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_name)
                                .addGap(209, 209, 209))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(b_start, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(b_users, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                        .addComponent(b_clear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(b_end, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(b_start, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(b_users, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(b_clear, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(b_end, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_name, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }

    // to start the server
    private class ServerStart implements Runnable {

        @Override
        public void run() {
            users = new ArrayList<>();

            try {
                // create socket for server and initialize
                ServerSocket serverSock = new ServerSocket(9999);
                clientSock = serverSock.accept();
                PrintWriter writerOut = new PrintWriter(clientSock.getOutputStream(), true);

                // activate the thread so it can listen to multiple users
                Thread listener = new Thread(new ClientHandler(clientSock, writerOut));
                listener.start();
                ta_chat.append("Connection Is Well Set Up... \n");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ta_chat.append("Error making a connection. \n");
            }
        }
    }

    // this is to handle the clients' requests
    private class ClientHandler implements Runnable {
        BufferedReader reader;

        PrintWriter client;

        ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                ta_chat.append("Unexpected error while handling client... \n");
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
                            ta_chat.append(data[0] + data[1] + "\n");
                            userAdd(data[0]);
                            break;
                        case "Disconnect":
                            ta_chat.append(data[0] + data[1] + "\n");
                            userRemove(data[0]);
                            break;
                        case "Server Chosen":
                            switch (data[1]) {
                                case "iTune":
                                    countDownload1++;
                                    ta_chat.append("(" + countDownload1 + ") times downloads from " + data[1] + " Server \n");
                                    break;
                                case "ZoneAlarm":
                                    countDownload2++;
                                    ta_chat.append("(" + countDownload2 + ") times downloads from " + data[1] + " Server \n");
                                    break;
                                case "WinRar":
                                    countDownload3++;
                                    ta_chat.append("(" + countDownload3 + ") times downloads from " + data[1] + " Server \n");
                                    break;
                                case "Audacity":
                                    countDownload4++;
                                    ta_chat.append("(" + countDownload4 + ") times downloads from " + data[1] + " Server \n");
                                    break;
                            }
                    }
                }
            } catch (Exception ex) {
                ta_chat.append("Lost a connection. \n");
                System.out.println(ex.toString());
                ex.printStackTrace();
            }
        }

    }

    // reset client's window when END buttin is clicked
    private void resetClientWindow() {
        //ClientFrame clientFrame = new ClientFrame();
        new ClientFrame().dispose();
        //clientFrame.disconnect(1);
    }

    // this is when START button is clicked
    private void b_startActionPerformed(ActionEvent evt) {
        Thread starter = new Thread(new ServerStart());             // create the thread
        starter.start();                                            // start the thread

        ta_chat.append("Server Has Started...\n");
    }

    // This is when ONLINE USERS button is clicked
    // It displays the online users
    private void b_usersActionPerformed(ActionEvent evt) {
        ta_chat.append("Online users : \n");
        for (int i = 0; i < users.size(); i++)
            ta_chat.append("\t" + (i + 1) + ") " + users.get(i) + "\n");


    }

    // this is when CLEAR button is clicked
    private void b_clearActionPerformed(ActionEvent evt) {
        ta_chat.setText("");
    }

    // when END button is clicked
    private void b_endActionPerformed(ActionEvent evt) {
        try {
            ta_chat.setText("Server Has Stopped....Bye!!!\n");
            Thread.sleep(2000);                 //2000 milliseconds is two second.
            clientSock.close();                 // close client's socket
            System.out.println("before");
            new ClientFrame().dispose();                // reset client's window
            System.out.println("after");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ta_chat.setText("");                    // clear the chat box
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

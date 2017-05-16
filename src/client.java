import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by BISMA on 13/05/2017.
 */
public class client extends javax.swing.JFrame {

    private String username, address = "localhost";
    private ArrayList<String> users = new ArrayList<>();
    int port = 2222;
    Boolean isConnected = false;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;

    String inputTextField;

    private javax.swing.JButton b_anonymous;
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_name;
    private javax.swing.JLabel lb_password;
    private javax.swing.JLabel lb_port;
    private javax.swing.JLabel lb_username;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_password;
    private javax.swing.JTextField tf_port;
    private javax.swing.JTextField tf_username;

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public class IncomingReader implements Runnable{

        @Override
        public void run() {

        }
    }

    //--------------------------//

    public void userAdd(String data) {
        users.add(data);
    }

    //--------------------------//

    public void userRemove(String data) {
        ta_chat.append(data + " is now offline.\n");
    }

    //--------------------------//

    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        tempList = users.toArray(tempList);
        for (String token : tempList) {
            //users.append(token + "\n");
            System.out.println("token == "+ token);
        }
    }

    //--------------------------//

    public void sendDisconnect() {
        String bye = (username + ": :Disconnect");
        try {
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }

    //--------------------------//

    public void Disconnect() {
        try {
            ta_chat.append("Disconnected.\n");
            sock.close();
        } catch (Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
        tf_username.setEditable(true);

    }

    public client() {
        initComponents();
    }

    //--------------------------//

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lb_address = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField();
        lb_port = new javax.swing.JLabel();
        tf_port = new javax.swing.JTextField();
        lb_username = new javax.swing.JLabel();
        tf_username = new javax.swing.JTextField();
        lb_password = new javax.swing.JLabel();
        tf_password = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        b_anonymous = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        lb_name = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - Client's frame");
        setName("client"); // NOI18N
        setResizable(false);

        lb_address.setText("Address : ");

        tf_address.setText("localhost");
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });

        lb_port.setText("Port :");

        tf_port.setText("2222");
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });

        lb_username.setText("Username :");

        tf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });

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
        b_anonymous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_anonymousActionPerformed(evt);
            }
        });

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_send.setText("SEND");
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });

        lb_name.setText("TechWorld3g");
        lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                                        .addComponent(lb_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(tf_address, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                        .addComponent(tf_username))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lb_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lb_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(tf_password)
                                                        .addComponent(tf_port, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(b_connect)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(b_disconnect)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(b_anonymous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_name)
                                .addGap(201, 201, 201))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lb_address)
                                        .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lb_port)
                                        .addComponent(tf_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(b_anonymous))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tf_username)
                                        .addComponent(tf_password)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lb_username)
                                                .addComponent(lb_password)
                                                .addComponent(b_connect)
                                                .addComponent(b_disconnect)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tf_chat)
                                        .addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_name))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed
        sendDisconnect();
        Disconnect();
    }//GEN-LAST:event_b_disconnectActionPerformed

    private void b_anonymousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_anonymousActionPerformed
        tf_username.setText("");
        if (isConnected == false) {
            String anon = "anon";
            Random generator = new Random();
            int i = generator.nextInt(999) + 1;
            String is = String.valueOf(i);
            anon = anon.concat(is);
            username = anon;

            tf_username.setText(anon);
            tf_username.setEditable(false);

            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                //writer = new PrintWriter(sock.getOutputStream());
                //writer.println(anon + ":has connected.:Connect");
                //writer.flush();
                isConnected = true;
            } catch (Exception ex) {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }

            ListenThread();

        } else if (isConnected == true) {
            ta_chat.append("You are already connected. \n");
        }
    }//GEN-LAST:event_b_anonymousActionPerformed

    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_connectActionPerformed
        if (!isConnected) {
            username = tf_username.getText();
            tf_username.setEditable(false);

            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
                b_connect.setEnabled(false);
                //ta_chat.append("Here are the terms of reference. Do you accept? yes or no\n");
            } catch (Exception ex) {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_username.setEditable(true);
            }

            ListenThread();

        }
    }//GEN-LAST:event_b_connectActionPerformed

    String ServerAnswer;
    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_sendActionPerformed
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
                inputTextField = tf_chat.getText();
                ta_chat.append("\nClient: " + inputTextField);
                ServerAnswer = processInput(inputTextField);
                ta_chat.append("Server: "+ServerAnswer+"\n");

                writer.println(username+ ":" + tf_chat.getText() + ":" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }//GEN-LAST:event_b_sendActionPerformed

    private static final int WAITING = 0;
    private static final int ACKNOWLEDGMENTSENT = 1;
    private static final int ACKNOWLEDGED = 2;
    private static final int TERMSNOTACCEPTED = 3;
    private static final int DOWNLOADING = 4;

    private static final int DONE = 5;
    private static final int TERMINATED = 5;

    private int state = WAITING;

    String processInput(String theInput) {
        String theOutput = null;

        switch(state){
            case WAITING:
                theOutput = "Here are the terms of reference. Do you accept? yes or no";
                state = ACKNOWLEDGMENTSENT;
                break;
            case ACKNOWLEDGMENTSENT:
                switch (theInput){
                    case "yes":
                        theOutput = " 1. iTune 2. ZoneAlarm 3. WinRar 4. Audacity Select a resource for downloading\n";
                        state = ACKNOWLEDGED;
                        break;
                    case "no":
                        theOutput = "Enter \"bye\" to exist, and \"enter\" to continue";
                        state = TERMINATED;
                        break;
                    default:
                        theOutput = "\nyou're supposed to enter yes or no";
                        state = ACKNOWLEDGMENTSENT;
                        break;
                }
                break;
            case ACKNOWLEDGED:
                switch (theInput){
                    case "1":
                        theOutput = " You are downloading iTune.zip ...";
                        state = DOWNLOADING;
                        break;
                    case "2":
                        theOutput = " You are downloading ZoneAlarm.zip ...";
                        state = DOWNLOADING;
                        break;
                    case "3":
                        theOutput = " You are downloading WinRar.zip ...";
                        state = DOWNLOADING;
                        break;
                    case "4":
                        theOutput = " You are downloading Audacity.zip ...";
                        state = DOWNLOADING;
                        break;
                    default:
                        theOutput = "you're supposed to enter either 1, 2, 3, or 4";
                        state = ACKNOWLEDGED;
                        break;
                }
                break;
            case DOWNLOADING:
                switch (theInput) {
                    case "<CR>":
                        theOutput = "Enter \"bye\" to exist, and \"enter\" to continue";
                        state = TERMINATED;
                        break;
                    default:
                        theOutput = "Enter \"bye\" to exist, and \"enter\" to continue";
                        state = TERMINATED;
                        break;
                }
                break;
            case TERMINATED:
                switch (theInput) {
                    case "bye":
                        state = DONE;
                        break;
                    case "enter":
                        state = DOWNLOADING;
                        break;
                    default:
                        theOutput = "Enter \"bye\" to exist, or \"enter\" to continue";
                        state = TERMINATED;
                        break;
                }
                break;
        }

        return theOutput;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }


}

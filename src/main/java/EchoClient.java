import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoClient extends JFrame {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;

    private JTextField msgInputField;
    private JTextArea chatArea;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public EchoClient(){
        try {
            openConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
        prepareGUI();
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
                        }
                        chatArea.append(strFromServer);
                        chatArea.append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        if (!msgInputField.getText().trim().isEmpty()) {
            try {
                out.writeUTF(msgInputField.getText());
                msgInputField.setText("");
                msgInputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "???????????? ???????????????? ??????????????????");
            }
        }
    }

    public void prepareGUI() {
        // ?????????????????? ????????
        setBounds(600, 300, 500, 500);
        setTitle("????????????");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ?????????????????? ???????? ?????? ???????????? ??????????????????
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // ???????????? ???????????? ?? ?????????? ?????? ?????????? ?????????????????? ?? ?????????????? ???????????????? ??????????????????
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("??????????????????");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);
        btnSendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        msgInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // ?????????????????????? ???????????????? ???? ???????????????? ????????
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("/end");
                    closeConnection();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EchoClient();
            }
        });
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        EchoClient that = (EchoClient) o;
//
//        if (SERVER_PORT != that.SERVER_PORT) return false;
//        if (SERVER_ADDR != null ? !SERVER_ADDR.equals(that.SERVER_ADDR) : that.SERVER_ADDR != null) return false;
//        if (msgInputField != null ? !msgInputField.equals(that.msgInputField) : that.msgInputField != null)
//            return false;
//        if (chatArea != null ? !chatArea.equals(that.chatArea) : that.chatArea != null) return false;
//        if (socket != null ? !socket.equals(that.socket) : that.socket != null) return false;
//        if (in != null ? !in.equals(that.in) : that.in != null) return false;
//        return out != null ? out.equals(that.out) : that.out == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = SERVER_ADDR != null ? SERVER_ADDR.hashCode() : 0;
//        result = 31 * result + SERVER_PORT;
//        result = 31 * result + (msgInputField != null ? msgInputField.hashCode() : 0);
//        result = 31 * result + (chatArea != null ? chatArea.hashCode() : 0);
//        result = 31 * result + (socket != null ? socket.hashCode() : 0);
//        result = 31 * result + (in != null ? in.hashCode() : 0);
//        result = 31 * result + (out != null ? out.hashCode() : 0);
//        return result;
//    }
//
//
//
//    @Override
//    public String toString() {
//        return "EchoClient{" +
//                "SERVER_ADDR='" + SERVER_ADDR + '\'' +
//                ", SERVER_PORT=" + SERVER_PORT +
//                ", msgInputField=" + msgInputField +
//                ", chatArea=" + chatArea +
//                ", socket=" + socket +
//                ", in=" + in +
//                ", out=" + out +
//                '}';
//    }
}




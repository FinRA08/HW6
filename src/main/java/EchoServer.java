import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {//сервер
    public static void main(String[] args) {
        Socket socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String str = in.readUTF();
                if (str.equals("/end")) {
                break;
                }
                System.out.println("Поступило сообщение от клиента:  " + str);
                String message = sc.nextLine();
                out.writeUTF("Сообщение с сервера: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scanner sc = new Scanner(System.in);

//    public void sendMessage(String message) {
//        try {
//            System.out.println("Send message: " + message);
//            out.writeUTF(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
}


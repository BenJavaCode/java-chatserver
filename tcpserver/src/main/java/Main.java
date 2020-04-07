import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main{


    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket serverSocket = new ServerSocket(7777);
        Socket socket = null;


        while (true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (socket != null){
                EchoListener echoListener = new EchoListener(socket);
                Thread thread = new Thread(echoListener);
                thread.start();
            }

        }

    }
}

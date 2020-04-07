import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadHandler {

    private Socket socket;
    private BufferedReader br;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;



    public ThreadHandler() throws IOException {
        socket = new Socket(InetAddress.getLocalHost(), 7777);
        br = new BufferedReader(new InputStreamReader(System.in));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }


    public void hear() throws IOException {
            String msg = bufferedReader.readLine();
            System.out.println(msg);
    }

    public void speak() throws IOException, InterruptedException {
        String x = br.readLine();
        printWriter.println(x);

        if (x.startsWith("close")){
            br.close();
            bufferedReader.close();
            printWriter.close();
            socket.close();
            System.exit(0);
        }
    }

    public void chooseName() throws IOException, InterruptedException {
        String x = br.readLine();
        printWriter.println(x);
    }

}

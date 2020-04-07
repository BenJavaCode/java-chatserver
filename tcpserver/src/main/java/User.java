import java.io.PrintWriter;
import java.net.Socket;

public class User {

    private PrintWriter printWriter;
    private String alias;
    private Socket socket;

    public User(Socket echoSocket){
        this.socket = echoSocket;
        try {
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

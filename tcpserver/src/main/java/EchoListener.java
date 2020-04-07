import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EchoListener implements Runnable {

    private Socket socket;
    private Random random = new Random();
    private static ArrayList<User> addresses = new ArrayList<>();


    EchoListener(Socket providedSocket){
        this.socket = providedSocket;
    }


    @Override
    public void run() {

        BufferedReader bufferedReader = null;

        try {
            System.out.println("a new connection has been established");
            User user = new User(socket);
            addresses.add(user);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            assert socket != null; //inteliJ anbefalede dette for at sikre at der ikke bliver smidt en NullpointerE
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                assert bufferedReader != null; //inteliJ anbefalede dette for at sikre at der ikke bliver smidt en NullpointerE
                String msg = null;
                try {
                    msg = bufferedReader.readLine();
                } catch (IOException ignored) {

                }

                if (msg != null) {
                    System.out.println("this is a echo of a msg: " + msg);

                    if (msg.startsWith("close")) {
                        //Using iterator to avoid ConcurrentModificationException
                        try {
                            List<User> toRemove = new ArrayList<User>();
                            for (User user : addresses) {
                                if (user.getSocket() == socket) {
                                    user.getSocket().close();
                                    user.getPrintWriter().close();
                                    bufferedReader.close();
                                    toRemove.add(user);
                                    System.out.println("connection to " + user.getAlias() + " terminated");
                                    System.out.println("There are now: "+ addresses.size() +" connections open");
                                }
                            }
                            addresses.removeAll(toRemove);
                        } catch (SocketException ignored) {
                        }

                    }
                }

                for (User user : addresses) {

                    if (user.getSocket() == socket && user.getAlias() != null) {

                        String alias = user.getAlias();

                        for (User userX : addresses) { // send message to all
                            PrintWriter printWriter1 = userX.getPrintWriter();
                            printWriter1.println(msg + " from " + alias);
                        }

                    }
                    if (user.getSocket() == socket && user.getAlias() == null) { // if alias is null, user initiated conn = mesage is alias name
                        System.out.println("new user selected the name " + msg);
                        user.setAlias(msg);
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(random.nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static BufferedOutputStream bos;

// C:\\Users\\vlada\\Documents\\al.jpg

    public static void main(String[] args) throws Exception {
        serverSocket = new ServerSocket(7777);
        System.out.println("Server turned on");

        while (true) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message = reader.readLine();
            File file = new File(message);
            String ex = file.toPath().getFileName().toString();

            FileInputStream fis = new FileInputStream(file);

            socket = serverSocket.accept();
            bos = new BufferedOutputStream(socket.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(ex);
            writer.newLine();
            writer.flush();

            while (fis.available() > 0) {
                byte[] bytes = fis.readNBytes(1024);
                bos.write(bytes);
            }
            bos.flush();

            bos.close();
            fis.close();
            socket.close();
        }
    }
}

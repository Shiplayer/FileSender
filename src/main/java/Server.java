import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static BufferedOutputStream bos;

// C:\\Users\\vlada\\Documents\\atom.exe

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
            System.out.println("client connected");
            bos = new BufferedOutputStream(socket.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(ex);
            writer.newLine();
            writer.flush();

//            while (fis.available() > 0) {
//                byte[] bytes = fis.readNBytes(1024);
//                bos.write(bytes);
//            }

            byte[] buffer = new byte[15 * 1024];

            int c = 0;

            while (c > -1) {
                c = fis.read(buffer, 0, 1024);
                System.out.println(c);
                if (c != -1)
                    bos.write(buffer, 0, c);
            }
            bos.flush();

            bos.close();
            fis.close();
            socket.close();
        }
    }
}

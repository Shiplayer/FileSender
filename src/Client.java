import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static BufferedInputStream bis;

    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 7777);
        System.out.println("Client is connected");

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String fileEx = reader.readLine();

        bis = new BufferedInputStream(socket.getInputStream());

        File file = new File("C:\\Users\\vlada\\Desktop\\" + fileEx);
        FileOutputStream fos = new FileOutputStream(file);

        while (bis.available() > 0) {
            byte[] bytes = bis.readNBytes(1024);
            fos.write(bytes);
        }


        fos.flush();

        bis.close();
        fos.close();
        socket.close();

    }
}

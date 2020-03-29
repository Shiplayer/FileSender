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

//        while (bis.available() > 0) {
//            byte[] bytes = bis.readNBytes(1024);
//            fos.write(bytes);
//        }

        int c = 0;

        byte[] buffer = new byte[15 * 1024];

        while (c > -1) {
            c = bis.read(buffer, 0, 1024);
            System.out.println(c);
            if (c != -1)
                fos.write(buffer, 0, c);
        }

        fos.flush();

        bis.close();
        fos.close();
        socket.close();

    }
}

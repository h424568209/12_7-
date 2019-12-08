package echo;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class TCPServer {

    private static class TalkRunnable implements Runnable {
        //侦听要连接到此套接字并接受它
        private Socket clientSocket;

        TalkRunnable(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                //返回此套接字所连接的地址
                InetAddress clientAddress = clientSocket.getInetAddress();
                int clientPort = clientSocket.getPort();
                System.out.printf("有客户端连接上来 %s:%d%n",
                        clientAddress.getHostAddress(), clientPort);
                // 获取 字节流
                InputStream is = null;
                is = clientSocket.getInputStream();
                // 字节流转换为字符流
                InputStreamReader isReader = null;
                isReader = new InputStreamReader(is, "UTF-8");
                // 字符流转换缓冲字符流
                BufferedReader reader = new BufferedReader(isReader);

                // 获取输出字节流
                OutputStream os = clientSocket.getOutputStream();
                PrintStream out = new PrintStream(os, true, "UTF-8");

                while (true) {
                    String line = reader.readLine();
                    System.out.println("好友说: " + line);
                  //  String response = "我已经收到了，" + System.currentTimeMillis();
                   // out.println(response);
                    System.out.println("Please Response : ");
                    Scanner scanner = new Scanner(System.in);
                    String respond = scanner.nextLine();
                    out.println(respond);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        //创建绑定到指定端口的服务器套接字
        ServerSocket tcpServerSocket = new ServerSocket(20201);
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ExecutorService pool = new ThreadPoolExecutor(
                100,
                100,
                0,
                TimeUnit.MILLISECONDS,
                queue
        );

        while (true) {
            Socket clientSocket = tcpServerSocket.accept();
            pool.execute(new TalkRunnable(clientSocket));
        }
    }
}

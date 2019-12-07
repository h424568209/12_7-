package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UDPServer {
    private static final Map<String,String> dict = new HashMap<>();
    static {
        dict.put("123","321");
        dict.put("12","21");
        dict.put("1234","4321");
    }

    public static void main(String[] args) throws IOException {
        //新建一个DatagramSocket
        DatagramSocket udpServerSocket = new DatagramSocket(1234);
        Scanner scanner = new Scanner(System.in);
        while(true){
            byte[]receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer,
                    receiveBuffer.length
            );
            //等待客户端
            udpServerSocket.receive(receivePacket);
            InetAddress clientAddress = receivePacket.getAddress();
            System.out.printf("我从%s：%d 收到消息%n",
                    clientAddress.getHostAddress(),
                    receivePacket.getPort());
            System.out.printf("我一共收到了%d 字节的数据 %n",
                    receivePacket.getLength());
            String message = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    "UTF-8"
            );
            System.out.println(message);
            String respondMessage = dict.getOrDefault(message,scanner.nextLine());

            byte[] sendBuffer = respondMessage.getBytes("UTF-8");
            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    clientAddress,
                    receivePacket.getPort()
            );
            udpServerSocket.send(sendPacket);
        }
    }
}

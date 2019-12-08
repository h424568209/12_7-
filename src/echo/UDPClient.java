package echo;


import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 *  UDP是传输层协议
 *  无连接
 *  不可靠传输
 *  面向数据报
 */
public class UDPClient {
    public static void main(String[] args) throws IOException {
        //创建一个数据报套接字
        DatagramSocket udpClientSocket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);
        while(true){
            //获取接下来要输出的信息  准备封装UDP包的东西
            String message = scanner.nextLine();
            // 消息转为字符流
            byte[] sendBuffer = message.getBytes("UTF-8");
            //将ip地址转为字符
            byte[]serverIP = new byte[4];
            serverIP[0] = (byte)10;
            serverIP[1] = (byte)188;
            serverIP[2] = (byte)35;
            serverIP[3] = (byte)78;
            InetAddress serverAddress = InetAddress.getByAddress(serverIP);


            //封装UDP包
            DatagramPacket sendPacket = new DatagramPacket(

                    sendBuffer,
                    sendBuffer.length,
                    serverAddress,
                    1234
            );
            //将信息发送出去
            udpClientSocket.send(sendPacket);


            //接收对方回应的消息
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer,receiveBuffer.length
            );

            //解包
            udpClientSocket.receive(receivePacket);
            String respondMessage = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    "UTF-8"
            );
            System.out.println(respondMessage);
        }
    }
}

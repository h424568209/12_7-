package echo;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket udpClientSocket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);
        while(true){
            String message = scanner.nextLine();
            byte[] sendBuffer = message.getBytes("UTF-8");
            byte[]serverIP = new byte[4];
            serverIP[0] = (byte)10;
            serverIP[1] = (byte)188;
            serverIP[2] = (byte)35;
            serverIP[3] = (byte)78;
            InetAddress serverAddress = InetAddress.getByAddress(serverIP);


            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    serverAddress,
                    1234
            );
            udpClientSocket.send(sendPacket);
            //接收对方回应的消息
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer,receiveBuffer.length
            );
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

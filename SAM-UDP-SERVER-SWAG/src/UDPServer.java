import java.awt.event.InputEvent;
import java.net.*;
import java.util.Enumeration;
import java.awt.Robot;

class UDPServer
{
    public static void main(String args[]) throws Exception
    {
        int PORT = 7777;
        String addr = "error";
        DatagramSocket socket = new DatagramSocket(PORT);
        socket.setBroadcast(true);

        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            if (n.getName().equals("wlan0")) {
                addr = ((InetAddress) ee.nextElement()).getHostAddress();
            }
        }
        System.out.println("Listening on " + addr + " on port " + PORT + "...");

        Robot r = new Robot();
        boolean pressing = false;
        while (true) {
            byte[] buf = new byte[1];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            pressing = Integer.parseInt(new String(packet.getData())) == 1 ? true : false;
            if (pressing) {
                r.mousePress(InputEvent.BUTTON1_MASK);
                r.mouseRelease(InputEvent.BUTTON1_MASK);
                System.out.println("Pressing!");
            } else {
                System.out.println("Not pressing!");
            }
        }
    }
}
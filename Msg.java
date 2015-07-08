import gnu.io.*;
import java.util.*;
import java.io.*;

public class Msg {
    
    public static final char ready = 0x55;
    public static final char[] startpart = { 0x30, 0x32, 0x30, 0x30, 0x33, 0x30 };
    public static final char messageDelimiter = 0x02;
    public static final char endpart = 0xf3;
    public static final char space = 0x20;
    public static final char[] bereit = {0x3c ,0x3c ,0x3c ,0x42 ,0x45 ,0x52 ,0x45 ,0x49, 0x54 ,0x21 ,0x3e ,0x3e ,0x3e};
    public static final char[] ok = {0x3c ,0x3c ,0x3c ,0x4f, 0x4b,0x21 ,0x3e ,0x3e ,0x3e};

        public static void main(String[] args) throws Exception {
        SerialPort serialPort = null;
        byte[] buf = new byte[100];

        CommPortIdentifier port = CommPortIdentifier.getPortIdentifier("/dev/cu.usbserial");
        System.out.println(port.getName());
        serialPort = (SerialPort) port.open("ListPortClass", 3000);
        int b = serialPort.getBaudRate();
        serialPort.setSerialPortParams(b, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
        OutputStream out = serialPort.getOutputStream();
        final InputStream in = serialPort.getInputStream();

        System.out.println("Ready? \r\n");
        out.write(ready);
        out.flush();
        System.out.println("Waiting for Reply \r\n");
        String s = "";
        while(true) {
            int i = in.read();
            s += (char) i;
            System.out.println(s);
            if(s.contains("BEREIT!>>>"))
               break;
        }

        //        byte[] sendarray = "\u0030\u0032\u0030\u0030\u0033\u0030\u0046\u0030\u0035\u0030\u0031\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0073\u0075\u006e\u0065\u0002\u00f3\u0020\u0020\u0020\u0020\u0032\u0032\u0030\u0039".getBytes("ASCII");

        byte[] sendarray = new byte[] {
            (byte)0x30, (byte)0x32, (byte)0x30, (byte)0x30, (byte)0x33, (byte)0x30, (byte)0x42, (byte)0x30, (byte)0x33, (byte)0x41, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x02, (byte)0x46, (byte)0x30, (byte)0x35, (byte)0x30, (byte)0x31, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x54, (byte)0x65, (byte)0x73, (byte)0x74, (byte)0x02, (byte)0xF3, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x20, (byte)0x34, (byte)0x33, (byte)0x30, (byte)0x32
                };

        /*        byte[] bytes = new byte[] {
            (byte) 30, (byte) 0x32, (byte) 0x30, (byte) 0x30, (byte) 0x33, (byte) 0x30, (byte) 0x46, (byte) 0x30, (byte) 0x35, (byte) 0x30, (byte) 0x31, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x53, (byte) 0x75, (byte) 0x6E, (byte) 0x65, (byte) 0x02, (byte) 0x46, (byte) 0x30, (byte) 0x35, (byte) 0x30, (byte) 0x31, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x53, (byte) 0x75, (byte) 0x6E, (byte) 0x65, (byte) 0x20, (byte) 0x69, (byte) 0x67, (byte) 0x65, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x6E, (byte) 0x02, (byte) 0xF3, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x20, (byte) 0x35, (byte) 0x36, (byte) 0x39, (byte) 0x39
            };*/

        //        sendarray = bytes;

        //        byte[] sendarray = "30 32 30 30 33 30 42 30 33 41 20 20 20 20 20 20      020030B03A
        //20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20
        //20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20
        //20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20
        //20 20 20 20 20 02 46 30 35 30 31 20 20 20 20 20           .F0501
        //20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20
        //20 20 20 20 20 20 20 20 20 54 65 73 74 02 F3 20               Test..
        //20 20 20 34 33 30 32".getBytes("US-ASCII");

        System.out.println(new String(sendarray));
        out.write(sendarray);
        out.flush();

                while(true) {
            int i = in.read();
            s += (char) i;
            System.out.println(s);
            if(s.contains("OK!>>>"))
               break;
        }

                out.close();

    }
}

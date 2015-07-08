import gnu.io.*;
import java.util.*;

public class Msg {
    
    public static final char ready = 0x55;
    public static final char[] startpart = { 0x30, 0x32, 0x30, 0x30, 0x33, 0x30 };
    public static final char messageDelimiter = 0x02;
    public static final char endpart = 0xf3;
    public static final char space = 0x20;
    public static final char[] bereit = {0x3c ,0x3c ,0x3c ,0x42 ,0x45 ,0x52 ,0x45 ,0x49, 0x54 ,0x21 ,0x3e ,0x3e ,0x3e};
    public static final char[] ok = {0x3c ,0x3c ,0x3c ,0x4f, 0x4b,0x21 ,0x3e ,0x3e ,0x3e};
    
    public static void main(String[] args) throws Exception {
        java.io.OutputStream mOutputToPort = null;
        java.io.InputStream mInputFromPort = null;
        SerialPort serialPort = null;

        CommPortIdentifier port = CommPortIdentifier.getPortIdentifier("/dev/cu.usbserial");
        System.out.println(port.getName());
        serialPort = (SerialPort) port.open("ListPortClass", 3000);
        int b = serialPort.getBaudRate();
        System.out.println(Integer.toString(b));
        serialPort.setSerialPortParams(b, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
        mOutputToPort = serialPort.getOutputStream();
        mInputFromPort = serialPort.getInputStream();
        System.out.println("Ready? \r\n");
        mOutputToPort.write(ready);
        mOutputToPort.flush();
        System.out.println("Waiting for Reply \r\n");
        Thread.sleep(1000);
        byte mBytesIn [] = new byte[200];
        mInputFromPort.read(mBytesIn);
        String value = new String(mBytesIn).trim();
        System.out.println("Response from Serial Device: "+value);
        System.out.print("Response from Serial Device in hex: ");
        System.out.println("");
        for (char ch : value.toCharArray()) {
            System.out.print(Integer.toHexString(ch) + " ");
        }

            byte[] sendarray = "\0030\0032\0030\0030\0033\0030\0046\0030\0035\0030\0031\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0020\0073\0075\006e\0065\0002\00f3\0020\0020\0020\0020\0032\0032\0030\0039".getBytes();

            System.out.println("Writing message");
            mOutputToPort.write(sendarray);
            mOutputToPort.flush();
            System.out.println("Waiting for Reply \r\n");
            Thread.sleep(1000);
            mInputFromPort.read(mBytesIn);
            mInputFromPort.read(mBytesIn);
            value = new String(mBytesIn).trim();
            System.out.println("Response from Serial Device: "+value);
            System.out.print("Response from Serial Device in hex: ");
            for (char ch : value.toCharArray()) {
                System.out.print(Integer.toHexString(ch) + " ");
            }
    }
}

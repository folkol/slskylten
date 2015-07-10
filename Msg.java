import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.*;
import java.io.*;

public class Msg {
    
    public static final char ready = 0x55;
    public static final byte[] startpart = { 0x30, 0x32, 0x30, 0x30, 0x33, 0x30 };
    public static final byte messageDelimiter = 0x02;
    public static final byte endpart = (byte) 0xf3;
    public static final byte space = (byte) 0x20;
    public static final byte[] bereit = {0x3c ,0x3c ,0x3c ,0x42 ,0x45 ,0x52 ,0x45 ,0x49, 0x54 ,0x21 ,0x3e ,0x3e ,0x3e};
    public static final byte[] ok = {0x3c ,0x3c ,0x3c ,0x4f, 0x4b,0x21 ,0x3e ,0x3e ,0x3e};

    static int checksum(byte[] buf) {
        int sum = 0;
        for(byte b : buf)
            sum += (int) b & 0xFF;
	return sum;
    }

    /**
020030B03A                                                           F0501                              Test�    4302
    */
    static byte[] createMessage(String msg) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(startpart, 0, startpart.length);
        baos.write("F".getBytes("ASCII"));
        baos.write("0".getBytes("ASCII"));
        baos.write("2".getBytes("ASCII"));
        baos.write("0".getBytes("ASCII"));
        baos.write("1".getBytes("ASCII"));

	// 30 spaces. Are these needed? Seems so...
        	for(int i = 0; i < 30; i++)
        	    baos.write(space);
        baos.write(msg.getBytes("ASCII"));
        baos.write(messageDelimiter);
        baos.write(endpart);

	int sum = checksum(baos.toByteArray());
        String checksum = String.format("%8s", Integer.toString(sum));
        baos.write(checksum.getBytes());

	for(byte b : baos.toByteArray())
	    System.err.write(b);

        return baos.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        byte[] buf = new byte[100];

        SerialPort serialPort = new SerialPort("/dev/cu.usbserial");
	serialPort.openPort();
	serialPort.setParams(9600, 8, 1, 0);
        
        System.out.println("Ready? \r\n");
	serialPort.writeByte((byte)0x55);
        System.out.println("Waiting for Reply \r\n");
        String s = "";
        while(true) {
            byte[] i = serialPort.readBytes(1);
            s += new String(i);
            System.out.println(s);
            if(s.contains("BEREIT!>>>"))
		break;
	}

        byte[] sendarray = createMessage("Welcome to Polopoly! <3");

        serialPort.writeBytes(sendarray);

	while(true) {
	    byte[] i = serialPort.readBytes(1);
            s += new String(i);
            System.out.println(s);
            if(s.contains("OK!>>>"))
		break;
        }
    }
}

package com.yykj.base.socket_listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 */
public class test {

    public static boolean login(String host, int port) throws Exception {
        try {
        	Socket socket = new Socket(host, port);
            //socket.setSoTimeout(1000);
//            String sbhead = ReadXmlFileUtil.getXml("zl_cardtype_request.xml");
            
            String sbhead ="<Request><TransCode>5001</TransCode><PatientID>242781</PatientID><ReceiptNo></ReceiptNo><UserId>null</UserId></Request>";
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(sbhead.getBytes());

            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
            // 读取每一行的数据.
            String str;
            while ((str = rd.readLine()) != null) {
                System.out.println(str);
            }
            System.out.println("aaaaaaaaaaaaaaaa");
            socket.close();
            rd.close();

        } catch (IOException ex) {//连接不成功
            System.out.println("异常：" + ex.toString());
        }
        return false;
    }
    
    
    public static boolean login1(String host, int port) throws Exception {
        try {
        	Socket socket = new Socket(host, port);
            //socket.setSoTimeout(1000);
           // String sbhead = ReadXmlFileUtil.getXml("zl_cardtype_request.xml");
            String sbhead ="<Request><TransCode>5001</TransCode><PatientID>242781</PatientID><ReceiptNo></ReceiptNo><UserId>null</UserId></Request>";
            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(sbhead.getBytes());
            
            Thread.sleep(100);
            
            //获取返回值
            InputStream input = socket.getInputStream();
            int count=0;
            while(count==0){
            	count = input.available();
            }
            byte [] b = new byte[count];
            input.read(b);
            System.out.println("aaaaaaaaa:"+count);
            System.out.println(new String(b,"GBK"));
            socket.close();
        } catch (IOException ex) {//连接不成功
            System.out.println("异常：" + ex.toString());
        }
        return false;
    }
    
    
    

    public static void main(String[] args) throws Exception {
        boolean result = login1("127.0.0.1", 2014);
    }
}
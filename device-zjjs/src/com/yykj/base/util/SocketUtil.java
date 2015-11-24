package com.yykj.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.commons.codec.binary.Hex;

/**
 * 类功能: Socket读写公共类
 * @author QinShuJin
 * 2015年5月13日 下午12:31:54
 */
public class SocketUtil {
	//连接超时时间
	public static final int CONNECT_TIME_OUT = 30*1000;
	/**
	 * 按规定长度读取报文
	 * @param is
	 * @param length
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static byte[] readLenContent(InputStream is, int length) throws IOException{
		byte[] retData = new byte[length];
		int readCount = 0; // 已经成功读取的字节的个数  
		while (readCount < length) {  
		    readCount += is.read(retData, readCount, length - readCount); 
		}
		return retData;
	}
	
	
	/**
	 * 按规定长度读取报文
	 * @param is
	 * @param length
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static byte[] readLenContent_ava(InputStream is, int length)throws IOException, InterruptedException {
		byte[] b = new byte[length];  
		int size = 0;
		int count = 0;
		while (size == 0) {
			count++;
			size = is.available();
			if(size==length){
				break;
			}
			Thread.sleep(100);
			int seconds = count*200;
			if(seconds==CONNECT_TIME_OUT){ //五秒后退出
				break;
			}
		}
		
		is.read(b);
		return b;
	}

	
	
	/**
	 * 写消息
	 * @param os
	 * @param buff
	 * @throws IOException
	 */
	public static void writeMessage(OutputStream os, byte[] buff)throws IOException {
		os.write(buff);
		os.flush();
	}

	/**
	 * 关闭连接
	 * 
	 * @param socket
	 * @param os
	 * @param is
	 */
	public static void closeResources(OutputStream os,InputStream is) {
		try {
			if (is != null) {
				is.close();
				is = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (os != null) {
					os.flush();
					os.close();
					os = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭资源
	 * @param socket
	 * @param os
	 * @param is
	 */
	public static void closeConnect(Socket socket, OutputStream os,InputStream is) {
		try {
			if (is != null) {
				is.close();
				is = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (os != null) {
					os.close();
					os = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if (socket != null) {
						socket.close();
						socket = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 连接
	 * 
	 * @param request
	 * @param port
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static Socket getConnect(String host, int port, int timeOut)throws IOException {
		Socket socket = new Socket();
		//关闭socket时，立即释放socket绑定端口以便端口重用，默认为false  
        socket.setReuseAddress(true);  
        //关闭传输缓存，默认为false  
        socket.setTcpNoDelay(true);  
        //如果输入流等待1000毫秒还未获得服务端发送数据，则提示超时，0为永不超时  
        socket.setSoTimeout(timeOut);  
        //关闭socket时，底层socket不会直接关闭，会延迟一会，直到发送完所有数据  
        //等待10秒再关闭底层socket连接，0为立即关闭底层socket连接  
        socket.setSoLinger(true, 10);  
        //设置性能参数，可设置任意整数，数值越大，相应的参数重要性越高（连接时间，延迟，带宽）  
        socket.setPerformancePreferences(0, 1, 0);  
        SocketAddress address = new InetSocketAddress(host, port);  
        //socket创建超时时间为1000毫秒  
        socket.connect(address, CONNECT_TIME_OUT);  
		socket.setTrafficClass(0x04 | 0x10);
		return socket;
	}

	/**
	 * 未指定长度方式读取报文
	 * 
	 * @param client
	 * @param length
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static byte[] readContent(InputStream is) throws IOException, InterruptedException {
		/*byte[] retData = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int availableLength = 0;
		try {
			while ((availableLength = is.read(buffer)) != -1) {
				baos.write(buffer, 0, availableLength);
			}
			retData = baos.toByteArray();
		} finally {
			if (baos != null) {
				baos.close();
			}
		}*/
		
		int time=200;
		int count = 0;  
	    while (count == 0) {  
	        count = is.available();
	        if(count==0){
	        	Thread.sleep(200);
	        	time += 200;
	        }
	        if(time >= CONNECT_TIME_OUT){
	        	break;
	        }
	    }
	    
	    if(time >= CONNECT_TIME_OUT){
	    	System.out.println("连接超时..");
	    	return null; //连接超时返回空
	    }
	    byte[] retData = new byte[count];  
	    is.read(retData);
	    
		return retData;
	}

	/**
	 * 发送接收数据
	 * 
	 * @param reqBytes
	 * @return
	 * @throws Exception 
	 */
	public static byte[] sendMessage(byte[] reqBytes, String address, int port,int timeOut) throws Exception {

		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] rspBytes = null;
		try {
			socket = getConnect(address, port, timeOut);
			// 将请求数据写出到Socket输出流
			os = new BufferedOutputStream(socket.getOutputStream());
			writeMessage(os, reqBytes);

			// 从Socket输入流中读取响应数据
			is = new BufferedInputStream(socket.getInputStream());
			rspBytes = readContent(is);
		} catch (Exception e) {
			throw new IOException("与业务系统通讯异常");
		} finally {
			// 关闭连接
			SocketUtil.closeConnect(socket, os, is);
		}
		return rspBytes;
	}

	/**
	 * 添加返回文件头
	 * 
	 * @param byteData
	 * @param len
	 * @param encoding 
	 * @return
	 */
	public static byte[] addLenHead(byte[] byteData, int len, String encoding) {
		if (len != 0) {
			byte[] byteSize = new byte[len];
			String strSize = new Integer(byteData.length).toString();
			for (int i = strSize.length(); i < len; i++) {
				strSize = "0" + strSize;
			}
			try {
				byteSize = strSize.getBytes(encoding);
			} catch (Exception e) {
				e.printStackTrace();
			}

			byte[] byteOut = new byte[byteSize.length + byteData.length];
			System.arraycopy(byteSize, 0, byteOut, 0, byteSize.length);
			System.arraycopy(byteData, 0, byteOut, byteSize.length,byteData.length);

			return byteOut;
		} else {
			return byteData;
		}
	}

}

package com.sample.tcpreceiver;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String...args) {
		if ( args.length != 1) {
			System.out.println("사용법 : tcpreceiver [포트번호] <엔터>");
			System.out.println("   예 : tcpreceiver 9999 <엔터>");
			return;
		}
		int portNumber = Integer.parseInt(args[0]);

		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		try {
			System.out.printf("서버를 생성합니다 (포트번호:%d)..\n", portNumber);
		    serverSocket = new ServerSocket(portNumber);
			System.out.println("접속을 기다립니다...");

		    clientSocket = serverSocket.accept();
			System.out.println("접속되었습니다.");
			System.out.printf("접속소스 : %s\n", clientSocket.getInetAddress().toString() );
		    
			System.out.println("데이터의 수신을 기다립니다.(종료는 CTRL-C)");
		    int newline = 0;
		    while ( true ) {
		    	int read = clientSocket.getInputStream().read();
		    	if ( read < 0 ) {
		    		break;
		    	}
		    	
		    	System.out.printf("%02X ", (byte)read);
		    	newline++;
		    	if ( newline >= 16 ) {
		    		newline = 0;
		    		System.out.print("\n");
		    	}
		    }
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			close(clientSocket);
			close(serverSocket);
		}
	}
	
	public static void close(Closeable closeable) {
		if ( closeable != null ) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}
}

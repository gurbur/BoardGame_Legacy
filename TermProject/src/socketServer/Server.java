package socketServer;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import board.BoardExample;
import marker.Marker;

public class Server {
	private final static int BUFFER_SIZE = 1024;
	public static final int PORT = 6077;
	
	public static void main(String[] args) {
	//public void serverStart() {
		try (ServerSocket server = new ServerSocket();
				/*System.out.println("Server Closed");*/) {
			InetAddress inetAddress = InetAddress.getByName("25.40.13.10");
			String localhost = inetAddress.getHostAddress();
			InetSocketAddress ipep = new InetSocketAddress(localhost, PORT);
			
			server.bind(ipep);
			
			System.out.println("Initialize complete");
			
			ExecutorService receiver = Executors.newCachedThreadPool();
			
			List<Socket> list = new ArrayList<>();
			
			while(true) {
				try {
					Socket client = server.accept();
					
					list.add(client);
					
					System.out.println("Client connected IP address =" + client.getRemoteSocketAddress().toString());
					receiver.execute(() -> {
						try(Socket thisClient = client;
								OutputStream send = client.getOutputStream();
								InputStream recv = client.getInputStream();) {
							String msg = "Welome server!\r\n>";
							
							byte[] b = msg.getBytes();
							
							send.write(b);
							StringBuffer sb = new StringBuffer();
							
							BoardExample board = new BoardExample();
							
							board = boardMaker(board);
							//Scanner in = new Scanner(System.in);
							
							//System.out.println(board.toString());
							//System.out.println("----------------");
							//System.out.println("움직이고 싶은 말의 위치를 고르세요.");
							//int markerToMove = in.nextInt();
							//System.out.println("그 말을 어디로 옮길지 위치를 고르세요.");
							//int targetPosition = in.nextInt();
							//board.move(markerToMove, targetPosition);
							
							//System.out.println("\n" + board.toString());
							
							msg = board.toString();
							
							b = msg.getBytes();
							
							send.write(b);
							
							
							/*while (true) {
								b = new byte[BUFFER_SIZE];
								
								recv.read(b, 0, b.length);
								msg = new String(b);
								sb.append(msg.replace("", ""));
								
								if(sb.length() > 2 && sb.charAt(sb.length() - 2) == '\r' && sb.charAt(sb.length() - 1) == '\n') {
									msg = sb.toString();
									sb.setLength(0);
									
									System.out.println(msg);
									if("exit\r\n".equals(msg)) {
										break;
									}
									
									msg = "echo : " + msg + ">";
									
									b = msg.getBytes();
									send.write(b);
								}
							}*/
						} catch (Throwable e) {
							e.printStackTrace();
						} finally {
							System.out.println("Client disconnected IP address =" + client.getRemoteSocketAddress().toString());
						}
					});
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	private static BoardExample boardMaker(BoardExample board) {
		for(int i = 0; i < 8; i++) {
			board.onBoard(new Marker(0, 0), i, i);// Object, position, index
			board.onBoard(new Marker(1, 0), i + 24, i + 8);
		}
		return board;
	}
	
}

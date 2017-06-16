package controller.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SokobanServer implements Server {

	// Data members
	private volatile boolean stop = false;
	private int port = 0;
	private ClientHandler ch = null;
	private ExecutorService executor = Executors.newCachedThreadPool();
	ServerSocket listeningSocket = null;

	// C'tor
	public SokobanServer(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
	}

	@Override
	public void startServer() throws Exception {
		if (ch == null)
			throw new Exception("NULL client handler.");

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {runServer(); } catch (Exception e) {e.printStackTrace();}
			}
		}).start();
	}

	private void runServer() throws Exception {
		listeningSocket = new ServerSocket(port); // The server socket
		System.out.println("The server is alive.");

		while (stop != true) {
			Socket connectionSocket = listeningSocket.accept(); // blocking call

			System.out.println("Waiting for connection...");
			System.out.println("A client is connected to server on port :: " + connectionSocket.getPort());

			executor.submit(new Runnable() {				
				@Override
				public void run() {
					try {
						ch.handleClient(connectionSocket.getInputStream(), connectionSocket.getOutputStream());
					} catch (Exception e) {e.printStackTrace();} 	
				}
			});
		}	
		listeningSocket.close();
	}

	@Override
	public void stopServer() throws Exception {
		stop = true;
		listeningSocket.close();
		executor.shutdown();
		System.out.println("Server is closed");
	}

}

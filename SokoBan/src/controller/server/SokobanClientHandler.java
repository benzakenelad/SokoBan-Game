package controller.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;

public class SokobanClientHandler extends Observable implements ClientHandler{
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception // handle a client by a well defined protocol
	{

		// Streams initialization
		BufferedReader fromClient = new BufferedReader( new InputStreamReader(inFromClient));
		PrintWriter toClient = new PrintWriter(outToClient, true);
		
		// Data Declaration
		String inputLine = null;
		String outputLine = new String("Enter the next command please.");	
		boolean stopPlay = false;
		
		// Sending terms to the client
		toClient.println("Enter 'menu' for menu display.");
		toClient.println("Enter 'stop' for stop playing.");
		toClient.println("Enter 'exit' to close the server.");
		
		// Protocol
		while(stopPlay != true)
		{
			toClient.println(outputLine);
			inputLine = fromClient.readLine();
			if(inputLine.equals("menu")){
				toClient.println("Enter 'move <up,down,left,right>' to move the character.");
				toClient.println("Enter 'open <levelname>' to open a level.");
				toClient.println("Enter 'save <levelname>' to save the level.");
				toClient.println("Enter 'stop' for stop playing.");
				toClient.println("Enter 'exit' to close the server.");
			}else if(inputLine.compareTo("stop") == 0 || inputLine.compareTo("exit") == 0)
			{	
				stopPlay = true;
				continue;
			}else if(inputLine.split(" ")[0].equals("move")){
				setChanged();
				notifyObservers(inputLine);
			}else if(inputLine.split(" ")[0].equals("open")){
				String[] strs = inputLine.split(" ");
				setChanged();
				notifyObservers("load levels/" + strs[1]);
			}else if(inputLine.split(" ")[0].equals("save")){
				String[] strs = inputLine.split(" ");
				setChanged();
				notifyObservers("save savedLevels/" + strs[1]);
			}
			
		}
		
		fromClient.close();
		toClient.close();

		if(inputLine.compareTo("exit") == 0)
		{
			setChanged();
			notifyObservers(inputLine);
		}
		
	}
}

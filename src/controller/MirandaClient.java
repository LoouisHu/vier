package controller;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Rogier on 19-12-16. Edited by Louis
 */
public class MirandaClient {
	
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter out;
    private static int nameCount;

    public MirandaClient() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 8080);
            reader = new BufferedReader(
            		new InputStreamReader(
            				new DataInputStream(
            						socket.getInputStream())));
            out = new PrintWriter(
            		new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public void receiveMessage() {
		try {
			while (true) {
				while (reader.ready()) {
					handleMessage(reader.readLine());
				}
			}
		} catch (IOException e) {
			
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    private void handleMessage(String readLine) {
		// TODO Auto-generated method stub
		
	}
    
    private void sendMessage(JSONObject msg) {
    	out.write(msg.toString());
		out.flush();
    }

	public void connect() {
        JSONObject obj = new JSONObject();
        obj.put("action", "connect");
        obj.put("name", "Rogier" + nameCount);
        nameCount++;
        out.println(obj.toString());
        out.flush();
    }

    public void join() {
        JSONObject obj1 = new JSONObject();
        obj1.put("action", "join");
        obj1.put("room number", 0);
        out.println(obj1.toString());
        out.flush();
    }

    public void startGame() {
        JSONObject obj1 = new JSONObject();
        obj1.put("action", "start");
        out.println(obj1.toString());
        out.flush();
    }


    public void disconnect() {
        JSONObject obj1 = new JSONObject();
        obj1.put("action", "disconnect");
        out.println(obj1.toString());
        out.flush();
    }

    public BufferedReader getReader() {
        return reader;
    }


    public static void main(String[] args) {
        MirandaClient client = new MirandaClient();
        client.connect();
        client.join();
        client.startGame();
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        client.disconnect();

    }
}
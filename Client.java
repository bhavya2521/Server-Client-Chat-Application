import java.io.*;
import java.net.*;
import javax.swing.*;
import java.io.IOException;

class ClientSetup implements User {
    
    private OutputStream output;
    private InputStream input; 
    private ChatFrame clientChatFrame;

    public ClientSetup() {
        clientChatFrame = new ChatFrame(this, "Client", "You are chatting with the Server!!!!!", true);
    }

    public void run() throws IOException {
	try{
            Socket socket = new Socket("localhost", 9999);
            System.out.println("CLIENT:- Connected to Server!");

            input = socket.getInputStream();
            output = socket.getOutputStream();        
	}
	catch(Exception e){
		System.out.println("Not Connected to Server.Try again!!");
		System.exit(0);
		
	}
    }


    @Override
    public void sendMessage() throws IOException {
	try{

        String send = clientChatFrame.getMessage();
        if (send != null && !send.equals("")) {
            output.write(send.getBytes());
            clientChatFrame.addMessage("YOU", send);
            System.out.println("CLIENT:- Message sent to Server: " + send);
        }
	}
	catch(Exception e){System.out.println(e);}
    }

    @Override
    public int receiveMessage() throws IOException {
	try{
        byte[] response = new byte[1000];
        int status = input.read(response);
        String received = new String(response).trim();

        if (received != null && !received.equals("")) {
            clientChatFrame.addMessage("SERVER", received);
            System.out.println("CLIENT:- Received message from server: " + received);
        }
        return status;
	
	}
	catch(Exception e){System.out.println(e);}
	return 1;

}

public class Client {
    
    public void main(String[] args) throws IOException {
        ClientSetup client = new ClientSetup();
        client.run();

        while (true) {
            int status = client.receiveMessage();
            if (status == -1) {
		System.out.println("Connection Lost!! Try again!!");
                System.exit(0);
            }
        }
    }
}
}


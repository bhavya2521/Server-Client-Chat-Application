import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.text.*;
import java.awt.Color;  
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTextPane;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.SimpleAttributeSet;  
import javax.swing.text.StyleConstants;  

public class ChatFrame extends JFrame implements ActionListener {

    private JPanel chatPanel; 
    private JPanel contactPanel; 

    private JLabel offlineLabel;
	private JLabel onlineLabel;

    private JButton sendButton;

    private JScrollPane textPane;
    private JScrollPane messagePane;

    private JTextPane textBox;
    private JTextPane messageBox;

    private final Font f = new Font("Serif",Font.BOLD,20);
	private final Font msgboxf = new Font("Serif",Font.PLAIN,  15);

    private User user;

    private final static int WIDTH = 600;
    private final static int HEIGHT = 700;

    public ChatFrame(User user, String from, String to, boolean centre) {
        this.user = user;

        chatPanel = new JPanel();
        chatPanel.setLayout(null);
        chatPanel.setBackground(new Color(233, 219, 232));
        chatPanel.setBounds(1, 50, WIDTH, HEIGHT - 50);
        chatPanel.setVisible(true);

        contactPanel = new JPanel();
        contactPanel.setLayout(null);
        contactPanel.setBackground(new Color(0, 204, 102));
        contactPanel.setBounds(1, 1, WIDTH, 50);
        contactPanel.setVisible(true);

        offlineLabel = new JLabel(to);
        offlineLabel.setFont(f);
        offlineLabel.setForeground(Color.WHITE);
        offlineLabel.setBounds(10, 1, WIDTH, 40);
        contactPanel.add(offlineLabel);

		onlineLabel = new JLabel("Client is Online");
    	onlineLabel.setFont(f);
    	onlineLabel.setForeground(Color.WHITE);
        onlineLabel.setBounds(10, 1, WIDTH, 40);
        contactPanel.add(onlineLabel);
		onlineLabel.setVisible(false);

        textBox = new JTextPane();
        textBox.setBounds(1, 1, WIDTH, HEIGHT - 200);
        textBox.setBackground(new Color(233, 219, 232));
		textBox.setFont(msgboxf);       
		textBox.setEditable(false);


        textPane = new JScrollPane(textBox);
        textPane.setBounds(1, 1, WIDTH, HEIGHT - 220);
        textPane.setBackground(new Color(233, 219, 232));
        chatPanel.add(textPane);

        messageBox = new JTextPane();
        messageBox.setBounds(1, HEIGHT - 216, 400, 130);
		messageBox.setFont(msgboxf); 
        messagePane = new JScrollPane(messageBox);
        messagePane.setBounds(1, HEIGHT - 216,400, 130);
		messagePane.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
        chatPanel.add(messagePane);


		sendButton = new JButton(new ImageIcon("send.png"));
		sendButton.setBackground(new Color(253, 253, 253 ));
        sendButton.setOpaque(true);
        sendButton.setBounds(WIDTH - 180, HEIGHT - 216, 134, 128);
		sendButton.setBorder(BorderFactory.createLineBorder(new Color(77, 136, 255), 2));
		sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendButton.addActionListener(this);
        chatPanel.add(sendButton);

        setTitle(from);
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(chatPanel);
        add(contactPanel);

        if (centre) {
            this.setLocationRelativeTo(null);
        }
        this.setVisible(true);
    }

    public void setActive(boolean active) {
		if(active){
			offlineLabel.setVisible(false);
			onlineLabel.setVisible(true);
			ImageIcon img = new ImageIcon("online.png");
			onlineLabel.setIcon(img);
			messageBox.setEditable(true);
			
		}
		else{
			ImageIcon img = new ImageIcon("offline.png");
			offlineLabel.setIcon(img);
			offlineLabel.setVisible(true);
			onlineLabel.setVisible(false);
			textBox.setText("");
			messageBox.setEditable(false);
		
		}
    }

    public String getMessage() {
        return messageBox.getText();
    }

    public void addMessage(String from, String message)  throws BadLocationException  {
		try{
			StyledDocument doc = textBox.getStyledDocument();
			if(from=="YOU"){
				SimpleAttributeSet right = new SimpleAttributeSet();
				StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
				StyleConstants.setForeground(right, Color.BLUE);
       			StyleConstants.setFontFamily(right, "Serif");
				doc.setParagraphAttributes(doc.getLength(), 1, right, true);
				doc.insertString(doc.getLength(), from + ": " + message + "\n", right );
			}
			else{
				SimpleAttributeSet left = new SimpleAttributeSet();
				StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
				StyleConstants.setForeground(left, new Color(255, 102, 0));
				StyleConstants.setFontFamily(left, "Serif");
				doc.setParagraphAttributes(doc.getLength(), 1, left, true);	 
				doc.insertString(doc.getLength(), from + ": " + message + "\n", left );
				
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
    }

    public void resetChat() {
        textBox.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            try {
                user.sendMessage();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            messageBox.setText("");
        }
    }

}

package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class GUI extends JFrame {

	private static final long serialVersionUID = 6367197118064801882L;
	private static final String TITLE = "ConnectFour 3D - Lobby";
	private final JTextPane chatLog;
	private JTextField fieldInput;
	private DefaultListModel userList;
	private JList listUsers;
	private JMenuBar menubar;
	private JMenu options;
	private JMenuItem menuItem;
	private JPanel p;
	private JScrollPane scrollPane;
	private JScrollPane listUsersSP;
	private StyledDocument doc;
	
	
	public GUI() {
		chatLog = new JTextPane();
		fieldInput = new JTextField(20);
		userList = new DefaultListModel();
		listUsers = new JList(userList);
		menubar = new JMenuBar();
		options = new JMenu("Options");
		menuItem = new JMenuItem("Connect");
		p = new JPanel();
		scrollPane = new JScrollPane(chatLog);
		listUsersSP = new JScrollPane(listUsers);
		doc = chatLog.getStyledDocument();
		
		setJFrame();
		setJPanel();
		menubar.add(options);
		options.add(menuItem);
		setChatLog();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setFieldInput();
		setListUser();
		
	}
	
	
	private void setJPanel() {
		p.setLayout(new FlowLayout());
		p.add(fieldInput);
		p.setBackground(new Color(221, 221, 100));

	}
	
	private void setJFrame() {
		setTitle(TITLE);
		setLocation(0, 0);
		setSize(800, 800);
		setResizable(true);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menubar);
		setVisible(true);
		getContentPane().add(p, "South");
		
	}
	
	private void setChatLog() {
		chatLog.setSize(new Dimension(400, 400));
		chatLog.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		chatLog.setText("Hello and welcome to Connect Four 3D!");
		chatLog.setFont(new Font("Arial", Font.PLAIN, 16));
	}
	
	private void setListUser() {
		listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUsers.setLayoutOrientation(JList.VERTICAL);
		listUsers.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				check(e);
			}

			public void mouseReleased(MouseEvent e) {
				check(e);
			}

			public void check(MouseEvent e) {
				if (e.isPopupTrigger()) {
					listUsers.setSelectedIndex(listUsers.locationToIndex(e.getPoint()));

				}
			}

		});
	}
	
	private void setFieldInput() {
		fieldInput.setFont(new Font("Arial", Font.PLAIN, 12));
		fieldInput.requestFocus();
		fieldInput.setHorizontalAlignment(SwingConstants.LEFT);
		fieldInput.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {

				String fromUser = fieldInput.getText();
				String noField = "";
				if (!fromUser.equals(noField) && e.getKeyCode() == KeyEvent.VK_ENTER) {
					int hour = LocalDateTime.now().getHour();
					int minute = LocalDateTime.now().getMinute();

					if (hour > 9 && minute > 9) {
						try {
							chatLog.getStyledDocument().insertString(doc.getLength(),
									"[" + hour + ":" + minute + "] " + 
											"You say: " + fromUser + "\n", null);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
					if (hour < 10 && minute > 9) {
						try {
							chatLog.getStyledDocument().insertString(doc.getLength(),
									"[0" + hour + ":" + minute + "] " + 
											"You say: " + fromUser + "\n", null);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
					if (hour > 9 && minute < 10) {
						try {
							chatLog.getStyledDocument().insertString(doc.getLength(),
									"[" + hour + ":0" + minute + "] " + 
											"You say: " + fromUser + "\n", null);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
					if (hour < 10 && minute < 10) {
						try {
							chatLog.getStyledDocument().insertString(doc.getLength(),
									"[0" + hour + ":0" + minute + "] " + 
											"You say: " + fromUser + "\n", null);
						} catch (BadLocationException e1) {
							e1.printStackTrace();
						}
					}
					chatLog.setCaretPosition(chatLog.getDocument().getLength());
					fieldInput.setText("");

				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
	}
	
	public void displayMessage(String message, String username) {

		int hour = LocalDateTime.now().getHour();
		int minute = LocalDateTime.now().getMinute();

		if (hour > 9 && minute > 9) {
			try {
				chatLog.getStyledDocument().insertString(doc.getLength(),
						"[" + hour + ":" + minute + "] " 
								+ username + " says: " + message + "\n", null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		if (hour < 10 && minute > 9) {
			try {
				chatLog.getStyledDocument().insertString(doc.getLength(),
						"[0" + hour + ":" + minute + "] " 
								+ username + " says: " + message + "\n", null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		if (hour > 9 && minute < 10) {
			try {
				chatLog.getStyledDocument().insertString(doc.getLength(),
						"[" + hour + ":0" + minute + "] " 
								+ username + " says: " + message + "\n", null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		if (hour < 10 && minute < 10) {
			try {
				chatLog.getStyledDocument().insertString(doc.getLength(),
						"[0" + hour + ":0" + minute + "] " 
								+ username + " says: " + message + "\n", null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}

		chatLog.setCaretPosition(chatLog.getDocument().getLength());
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
	

}

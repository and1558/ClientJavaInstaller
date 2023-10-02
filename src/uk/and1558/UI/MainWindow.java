package uk.and1558.UI;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class MainWindow extends JFrame{
	public MainWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("Minecraft Client Installer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(565, 386);
		JTextPane textPane = new JTextPane();
		textPane.setBounds(204, 185, 230, 20);
		getContentPane().add(textPane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(450, 313, 89, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Minecraft Directory:");
		lblNewLabel.setBounds(93, 185, 101, 20);
		getContentPane().add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(204, 150, 230, 22);
		getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Client Version          : ");
		lblNewLabel_1.setBounds(92, 150, 102, 18);
		getContentPane().add(lblNewLabel_1);
	}
}

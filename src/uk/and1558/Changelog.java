package uk.and1558;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Changelog extends JFrame{
	int Count = 0;
	public Changelog() {
		setSize(703,508);
		JTextArea textArea = new JTextArea();
		getContentPane().add(textArea, BorderLayout.CENTER);
		try {
            
            URL url = new URL("https://raw.githubusercontent.com/aydenfurr/ClientUpdates/main/Changelogs.txt");
             
            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
             
            String line;
            while ((line = in.readLine()) != null) {
            	if(Count == 0) {
            		setTitle(line);
            		Count++;
            	}else {
            		textArea.append(line + "\n");
            		Count++;
            	}
            }
            in.close();
             
        }
        catch (Exception e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
		textArea.setEditable(false);
        setVisible(true);
        pack();
	}

}

package uk.and1558;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.util.SystemInfo;

import uk.and1558.WinNative.dwmapi;
import uk.and1558.gui.MotionPanel;

public class MainGUI extends JFrame{
	public JTextField textField;
	public MainGUI() {
		System.setProperty( "flatlaf.useWindowDecorations", "true" );
		getContentPane().setForeground(new Color(255, 255, 255));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		final FlatDarkLaf laf = new FlatDarkLaf();
		//Theme Setup
		FlatLaf.setup(laf);
		if( SystemInfo.isLinux ) {
		    // enable custom window decorations
		    JFrame.setDefaultLookAndFeelDecorated( true );
		    JDialog.setDefaultLookAndFeelDecorated( true );
		}
		
		setUndecorated(true);
		setTitle("Cross-Platform Installer");
		getContentPane().setBackground(new Color(35, 35, 35));
		setBackground(new Color(0, 0, 0,0.1f));
		
		setSize(686, 393);
		getContentPane().setLayout(null);
		
		JButton btnNewButton_2 = new JButton("...");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMinecraftDir();
			}
		});
		btnNewButton_2.setBounds(534, 198, 27, 20);
		getContentPane().add(btnNewButton_2);
		
		JLabel label = new JLabel("New label");
		getContentPane().add(label, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Welcome to the Installer!");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Segoe UI Variable", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 650, 25);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(240, 198, 295, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel versionLabel = new JLabel("Version                      :");
		versionLabel.setForeground(new Color(255, 255, 255));
		versionLabel.setBounds(121, 170, 119, 14);
		getContentPane().add(versionLabel);
		
		comboBox = new JComboBox();
		comboBox.setBounds(240, 166, 321, 22);
		getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Minecraft Directory :");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(121, 201, 119, 14);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Install");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkMinecraftDirandInstall(textField.getText());
			}
		});
		btnNewButton.setBounds(587, 360, 89, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Version 0.4");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(12, 36, 364, 14);
		getContentPane().add(lblNewLabel_2);
		
		JButton btnLatestChangelogs = new JButton("Latest Changelogs");
		btnLatestChangelogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Changelog();
			}
		});
		btnLatestChangelogs.setBounds(438, 360, 139, 23);
		getContentPane().add(btnLatestChangelogs);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(10, 360, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		JPanel panel = new MotionPanel(this);
		panel.setBackground(new Color(0,0,0,0));
		panel.setBounds(0, 0, 686, 66);
		getContentPane().add(panel);
		
		jProgressBar1 = new JProgressBar();
		jProgressBar1.setBounds(10, 330, 666, 25);
		getContentPane().add(jProgressBar1);
		
		jLabel4 = new JLabel("Progress");
		jLabel4.setForeground(new Color(255, 255, 255));
		jLabel4.setBounds(10, 305, 666, 14);
		getContentPane().add(jLabel4);
		
		rdbtnNewRadioButton = new JRadioButton("Legacy Versions");
		rdbtnNewRadioButton.setForeground(new Color(255, 255, 255));
		rdbtnNewRadioButton.setBounds(184, 247, 109, 23);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadLegacyVer();
			}
		});
		getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnUpdatedVersions = new JRadioButton("Updated Versions", true);
		rdbtnUpdatedVersions.setForeground(Color.WHITE);
		rdbtnUpdatedVersions.setBounds(387, 247, 139, 23);
		rdbtnUpdatedVersions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadMixinVer();
			}
		});
		getContentPane().add(rdbtnUpdatedVersions);
		
		ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(rdbtnUpdatedVersions);
        bgroup.add(rdbtnNewRadioButton);
		
		jLabel4.setVisible(false);
		jProgressBar1.setVisible(false);
		
		loadMixinVer();
		
		setVisible(true);
		if(SystemInfo.isWindows) {
			textField.setText(System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming" + File.separator + ".minecraft");
		}else if(SystemInfo.isLinux) {
			textField.setText(System.getProperty("user.home") + File.separator + ".minecraft");
		}else if(SystemInfo.isMacOS) {
			textField.setText(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + "minecraft");
		}
		if(SystemInfo.isWindows_10_orLater && !SystemInfo.isWindows_11_orLater) {
			dwmapi.EnableDarkTitlebar(this);
			dwmapi.blurWin10(this);
			getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.blue));
		}
		if(SystemInfo.isWindows_11_orLater) {
			dwmapi.RoundWindow(this);
			dwmapi.setBorderColor(this);
			dwmapi.EnableDarkTitlebar(this);
			dwmapi.useMica(this);
		}
	}
	private void loadMixinVer() {
        final JSONObject json = this.getVersionList();
        if (json != null) {
            final JSONArray array = json.getJSONArray("clientmixinverlist");
            this.comboBox.removeAllItems();
            for (int i = 0; i < array.length(); ++i) {
                this.comboBox.addItem(array.getJSONObject(i).get("clientver").toString());
            }
        }
    }
    
    private void loadLegacyVer() {
        final JSONObject json = this.getVersionList();
        if (json != null) {
            final JSONArray array = json.getJSONArray("clientverlist");
            this.comboBox.removeAllItems();
            for (int i = 0; i < array.length(); ++i) {
                this.comboBox.addItem(array.getJSONObject(i).get("clientver").toString());
            }
        }
    }
    private JSONObject getVersionList() {
        InputStream is = null;
        try {
            is = new URL("https://raw.githubusercontent.com/and1558/ClientUpdates/main/devverlist.json").openStream();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            final String jsonText = readAll(rd);
            final JSONObject json = new JSONObject(jsonText);
            return json;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot get version list for newer version!", "Cannot download JSON", 0);
            return null;
        }
        finally {
            try {
                is.close();
            }
            catch (IOException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private static String readAll(final Reader rd) throws IOException {
        final StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }
        return sb.toString();
    }
	public void setMinecraftDir() {
		final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(1);
        fileChooser.setCurrentDirectory(new File(textField.getText()));
        final int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            final File mcVer = new File(fileChooser.getSelectedFile().getAbsoluteFile(), "/versions");
            final File mcNeededVer = new File(mcVer.getPath(), "/1.8.9");
            if (mcVer.exists()) {
                if (mcNeededVer.exists()) {
                    this.textField.setText(fileChooser.getSelectedFile().getPath());
                }
                else {
                    //JOptionPane.showMessageDialog(this, "Your selected Minecraft Folder does not have the version 1.8.9 [VANILLA] Installed!", "Version Not Found!", 0);
                    JOptionPane.showMessageDialog(null, "Your selected Minecraft Folder does not have the version 1.8.9 [VANILLA] Installed!", "Version Not Found!", 0);
                	this.setMinecraftDir();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "The selected folder is NOT a valid Minecraft Folder", "Invalid Folder!", 0);
                this.setMinecraftDir();
            }
        }
	}
	public void checkMinecraftDirandInstall(String mcDir) {
            final File mcVer = new File(mcDir, "/versions");
            final File mcNeededVer = new File(mcVer.getPath(), "/1.8.9");
            if (mcVer.exists()) {
                if (mcNeededVer.exists()) {
                	jProgressBar1.setVisible(true);
                	jLabel4.setVisible(true);
                	new Backend(this, comboBox.getSelectedItem().toString(), rdbtnNewRadioButton.isSelected(), textField.getText()).start();
                }
                else {
                    //JOptionPane.showMessageDialog(this, "Your selected Minecraft Folder does not have the version 1.8.9 [VANILLA] Installed!", "Version Not Found!", 0);
                    JOptionPane.showMessageDialog(null, "Your selected Minecraft Folder does not have the version 1.8.9 [VANILLA] Installed!", "Version Not Found!", 0);
                	this.setMinecraftDir();
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "The selected folder is NOT a valid Minecraft Folder", "Invalid Folder!", 0);
                this.setMinecraftDir();
            }
	}
	public JRadioButton rdbtnNewRadioButton;
    public JLabel jLabel4;
	public JProgressBar jProgressBar1;
	public JComboBox comboBox;
}

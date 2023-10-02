package uk.and1558.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.Structure;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HRESULT;
import com.sun.jna.ptr.IntByReference;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class DWMWindow
{
	private JTextField textField;
	/**
	 * @wbp.parser.entryPoint
	 */
	public void showWin() {
    	try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(!System.getProperty("os.version").equals("10.0")) {
			JOptionPane.showMessageDialog(null, "This installer requires Windows NT 10 and above!");
			System.exit(0);
		}
        JFrame f = new JFrame();
        f.setUndecorated(true);
        f.setSize(600, 383);
        f.setBackground(new Color(0,0,0,1));
        f.setTitle("Hello");
        TranslucentPanel panel = new TranslucentPanel();
        f.getContentPane().add(panel);
        panel.setBackground(new Color(0,0,0,0));
        panel.setLayout(null);
        
        
        JButton btnNewButton = new JButton("INSTALL");
        btnNewButton.setBounds(448, 327, 142, 45);
        panel.add(btnNewButton);
        
        textField = new JTextField();
        textField.setBounds(211, 197, 282, 20);
        panel.add(textField);
        textField.setColumns(10);
        
        @SuppressWarnings("rawtypes")
		JComboBox comboBox = new JComboBox();
        comboBox.setBounds(211, 159, 282, 22);
        panel.add(comboBox);
        
        JLabel lblNewLabel_1 = new JLabel("Client Version          : ");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(91, 161, 117, 18);
		panel.add(lblNewLabel_1);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(0, 0, 0, 50));
        panel_1.setBounds(0, 0, 600, 35);
        panel.add(panel_1);
        FrameDragListener fdl = new FrameDragListener(f);
        panel_1.addMouseListener(fdl);
        panel_1.addMouseMotionListener(fdl);
        panel_1.setLayout(null);
        
        JButton closeButton = new JButton("");
        closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        
        
        closeButton.setFont(new Font("Segoe Fluent Icons", Font.PLAIN, 13));
        closeButton.setBounds(555, 0, 45, 34);
        panel_1.add(closeButton);
        
        JLabel lblNewLabel_1_1 = new JLabel("Minecraft Directory :");
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setBounds(91, 200, 117, 18);
        panel.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel = new JLabel("Client Installer v0.4-Universal\r\n");
        lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 19));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(10, 46, 580, 22);
        panel.add(lblNewLabel);
        
        JCheckBox chckbxNewCheckBox = new JCheckBox("Windows 10 Compatibility Mode");
        chckbxNewCheckBox.setBounds(6, 353, 253, 23);
        panel.add(chckbxNewCheckBox);
        
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FlatDarkLaf.setUseNativeWindowDecorations(true);
        f.setVisible(true);
        
        dwmMagic(f, closeButton, panel);
        
        
        
        
	}
	
	private void dwmMagic(JFrame f, JButton closeButton, TranslucentPanel panel) {
		HWND hwnd = new HWND(Native.getWindowPointer(f));
        if(System.getProperty("os.name").equals("Windows 10")) {
        	panel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        	EnableBGBlur(hwnd);
        }
        else if(System.getProperty("os.name").equals("Windows 11")) {
        	LineBorder line = new LineBorder(Color.blue);
            EmptyBorder margin = new EmptyBorder(5, 15, 5, 15);
            CompoundBorder compound = new CompoundBorder(line, margin);
        	closeButton.setBorder(compound);
        	EnableAccent(hwnd);
        	RoundWindowCorners(hwnd);
        }
	}
	
	private void RoundWindowCorners(HWND hwnd) {
		// TODO Auto-generated method stub
		NativeLibrary dwmapi = NativeLibrary.getInstance("dwmapi");
		Function dwmsetwindowattribute = dwmapi.getFunction("DwmSetWindowAttribute");
		dwmsetwindowattribute.invoke(HRESULT.class, new Object[] { hwnd, 33, new IntByReference(2), 4} );
	}

	@SuppressWarnings("serial")
	private static class TranslucentPanel extends JPanel
    {

		@Override
        protected void paintComponent(Graphics g) 
        {
			super.paintComponent(g);
	        if(System.getProperty("os.name").equals("Windows 11")) {
				Dimension arcs = new Dimension(15,15);
		        int width = getWidth();
		        int height = getHeight();
		        Graphics2D graphics = (Graphics2D) g;
		        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		        //Draws the rounded opaque panel with borders.
		        graphics.setColor(Color.blue);
		        graphics.drawRoundRect(1, 1, width-3, height-3, arcs.width, arcs.height);//paint border
	        }
        }
    }
	
	public void EnableAccent(HWND hwnd) {
		RefreshFrame(hwnd);
		RefreshDarkMode(hwnd);
		NativeLibrary dwmapi = NativeLibrary.getInstance("dwmapi");
		Function dwmsetwindowattribute = dwmapi.getFunction("DwmSetWindowAttribute");
		dwmsetwindowattribute.invoke(HRESULT.class, new Object[] { hwnd, 38, new IntByReference(3), 4});
	}
	private void RefreshFrame(HWND hwnd) {	
        MARGINS margins = new MARGINS();
        margins.cxLeftWidth = -1;
        margins.cxRightWidth = -1;
        margins.cyTopHeight = -1;
        margins.cyBottomHeight = -1;
        
        NativeLibrary dwmapi = NativeLibrary.getInstance("dwmapi");
        Function extendFrame = dwmapi.getFunction("DwmExtendFrameIntoClientArea");
        extendFrame.invoke(HRESULT.class, new Object[] { hwnd, margins });
	}
	private void RefreshDarkMode(HWND hwnd) {
		// TODO Auto-generated method stub
		NativeLibrary dwmapi = NativeLibrary.getInstance("dwmapi");
		Function dwmsetwindowattribute = dwmapi.getFunction("DwmSetWindowAttribute");
		dwmsetwindowattribute.invoke(HRESULT.class, new Object[] { hwnd, 20, new IntByReference(1), 4});
	}
	enum DWM_SYSTEMBACKDROP_TYPE{
		DWMSBT_AUTO,
		DWMSBT_NONE,
		DWMSBT_MAINWINDOW,
		DWMSBT_TRANSIENTWINDOW,
		DWMSBT_TABBEDWINDOW
	};
	public class MARGINS extends Structure implements Structure.ByReference {

		public int cxLeftWidth;
		public int cxRightWidth;
		public int cyTopHeight;
		public int cyBottomHeight;

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("cxLeftWidth", "cxRightWidth", "cyTopHeight", "cyBottomHeight");
		}

	}
	public void EnableBGBlur(HWND hwnd) {
    	HWND aeroFrameHWND = hwnd; // Modify pointer to window

		NativeLibrary user32 = NativeLibrary.getInstance("user32");

		AccentPolicy accent = new AccentPolicy();
		accent.AccentState = AccentState.ACCENT_ENABLE_BLURBEHIND;
		int accentStructSize = accent.size();
		accent.write();
		Pointer accentPtr = accent.getPointer();

		WindowCompositionAttributeData data = new WindowCompositionAttributeData();
		data.Attribute = WindowCompositionAttribute.WCA_ACCENT_POLICY;
		data.SizeOfData = accentStructSize;
		data.Data = accentPtr;

		Function setWindowCompositionAttribute = user32.getFunction("SetWindowCompositionAttribute");
		setWindowCompositionAttribute.invoke(HRESULT.class, new Object[] { aeroFrameHWND, data });
    }
    public static interface AccentState {
		public static final int ACCENT_DISABLED = 0;
		public static final int ACCENT_ENABLE_GRADIENT = 1;
		public static final int ACCENT_ENABLE_TRANSPARENTGRADIENT = 2;
		public static final int ACCENT_ENABLE_BLURBEHIND = 3;
		public static final int ACCENT_INVALID_STATE = 4;
	}

	public static interface WindowCompositionAttribute {
		public static final int WCA_ACCENT_POLICY = 19;
	}

	public class AccentPolicy extends Structure implements Structure.ByReference {
		public int AccentState;
		public int AccentFlags;
		public int GradientColor;
		public int AnimationId;

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("AccentState", "AccentFlags", "GradientColor", "AnimationId");
		}
	}

	public class WindowCompositionAttributeData extends Structure implements Structure.ByReference {
		public int Attribute;
		public Pointer Data;
		public int SizeOfData;

		@Override
		protected List<String> getFieldOrder() {
			return Arrays.asList("Attribute", "Data", "SizeOfData");
		}
	}
	public static class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }
}
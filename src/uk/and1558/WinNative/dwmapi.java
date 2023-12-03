package uk.and1558.WinNative;

import java.util.List;

import javax.swing.JFrame;

import com.sun.jna.Function;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.NativeLibrary;
import com.sun.jna.platform.win32.WinNT.HRESULT;

public interface dwmapi extends Library{
	public final static dwmapi INSTANCE = Native.loadLibrary("dwmapi", dwmapi.class);
	
	int DwmSetWindowAttribute(HWND hwnd, int dwAttribute, PointerType pvAttribute, int cbAttribute);
	int DwmExtendFrameIntoClientArea(HWND windowHandle, MARGINS margins);
	
    int DWMWA_USE_IMMERSIVE_DARK_MODE = 20;
    int DWMWA_BORDER_COLOR = 34;
    
    		
    public static void EnableDarkTitlebar(JFrame source)
    {
    	final HWND hwnd = new HWND();
		hwnd.setPointer(Native.getComponentPointer(source));
		
        INSTANCE.DwmSetWindowAttribute(hwnd, DWMWA_USE_IMMERSIVE_DARK_MODE, new IntByReference(1), 4);
    }
    public class MARGINS extends Structure implements Structure.ByReference {
		public static final List<String> FIELDS = createFieldsOrder("cxLeftWidth", "cxRightWidth", "cyTopHeight",
				"cyBottomHeight");

		public int cxLeftWidth;
		public int cxRightWidth;
		public int cyTopHeight;
		public int cyBottomHeight;

		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}

	}
    public static void setBorderColor(JFrame source) {
    	final HWND hwnd = new HWND();
		hwnd.setPointer(Native.getComponentPointer(source));
		INSTANCE.DwmSetWindowAttribute(hwnd, DWMWA_BORDER_COLOR, new IntByReference(0x00FF0000), 4);
    }
    public static void ExtendTitleBarIntoClientArea(JFrame source) {
    	MARGINS margins = new MARGINS();
    	margins.cxLeftWidth = -1;
    	margins.cxRightWidth = -1;
    	margins.cyBottomHeight = -1;
    	margins.cyTopHeight = -1;
    	final HWND hwnd = new HWND();
		hwnd.setPointer(Native.getComponentPointer(source));
    	INSTANCE.DwmExtendFrameIntoClientArea(hwnd, margins);
    }
    public static void RoundWindow(JFrame source) {
    	final HWND hwnd = new HWND();
		hwnd.setPointer(Native.getComponentPointer(source));
		INSTANCE.DwmSetWindowAttribute(hwnd, 33, new IntByReference(2), 4);
    }
    public static void useMica(JFrame source) {
    	final HWND hwnd = new HWND();
		hwnd.setPointer(Native.getComponentPointer(source));
    	INSTANCE.DwmSetWindowAttribute(hwnd, 38, new IntByReference(2), 4);
    }
    //Windows 10
    public class AccentPolicy extends Structure implements Structure.ByReference {
		public static final List<String> FIELDS = createFieldsOrder("AccentState", "AccentFlags", "GradientColor",
				"AnimationId");
		public int AccentState;
		public int AccentFlags;
		public int GradientColor;
		public int AnimationId;

		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}
    public static interface AccentState {
		public static final int ACCENT_DISABLED = 0;
		public static final int ACCENT_ENABLE_GRADIENT = 1;
		public static final int ACCENT_ENABLE_TRANSPARENTGRADIENT = 2;
		public static final int ACCENT_ENABLE_BLURBEHIND = 3;
		public static final int ACCENT_ENABLE_ACRYLICBLURBEHIND  = 4;
		public static final int ACCENT_INVALID_STATE = 5;
	}
    public static interface WindowCompositionAttribute {
		public static final int WCA_ACCENT_POLICY = 19;
	}
    public class WindowCompositionAttributeData extends Structure implements Structure.ByReference {
		public static final List<String> FIELDS = createFieldsOrder("Attribute", "Data", "SizeOfData");
		public int Attribute;
		public Pointer Data;
		public int SizeOfData;

		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}

    
    public static void blurWin10(JFrame source) {
    	final HWND hwnd = new HWND();
		hwnd.setPointer(Native.getComponentPointer(source));
		HWND aeroFrameHWND = hwnd; // Modify pointer to window

		NativeLibrary user32 = NativeLibrary.getInstance("user32");

		AccentPolicy accent = new AccentPolicy();
		accent.AccentState = AccentState.ACCENT_ENABLE_ACRYLICBLURBEHIND;
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
}

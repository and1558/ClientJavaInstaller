package uk.and1558;

import uk.and1558.UI.DWMWindow;
import uk.and1558.UI.MainWindow;

public class Init {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(System.getProperty("os.name").contains("Windows"))
			new DWMWindow().showWin();
		else
			new MainWindow().setVisible(true);
	}

}

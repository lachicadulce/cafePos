package baseSettings;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

public class PosFrame extends JFrame {
	
	public void setDefaultOptions() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(new Point(2020, 100));
		setSize(new Dimension(1200, 900));
		setVisible(true);
	}
}

package disneyShortcuts;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {

	public DrawPanel() {
		this.setPreferredSize(new Dimension(1248, 900));
		//this.setOpaque(false);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(new Line2D.Double(100, 100, 200, 200));
	}
	
}

package bgwumpus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;

public class GUI extends javax.swing.JFrame implements UserInterface {

	/**
	 * TODO work out what this is, eclipse complains with a warning if I do not have it
	 */
	private static final long serialVersionUID = 5111988071128070407L;
	private Image img;

	GUI(){

	UserInputHandler inputHandler = new UserInputHandler(); 
	addKeyListener(inputHandler);

	setSize(640,480);
	setResizable(false);

	}
	
	public void render(){
	setVisible(true);

	}
	
	public void paint(Graphics g){
	
		
		Toolkit t = Toolkit.getDefaultToolkit();
		Image img = t.getImage("dirt.gif");
		
		for(int i=0; i<480; i+=32){
			for(int j=0; j<640; j+=32){
				g.drawImage(img, j, i, 32, 32, null);
			}
		}
		
		render();


	}
	
	
	
}

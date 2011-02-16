package bgwumpus;

import javax.swing.JFrame;

public class GUI extends javax.swing.JFrame implements UserInterface {

	/**
	 * TODO work out what this is, eclipse complains with a warning if I do not have it
	 */
	private static final long serialVersionUID = 5111988071128070407L;

	GUI(){
	setTitle("Hello World");
	setSize(640,480);

	}
	
	public void render(){
	setVisible(true);
	}
	
	
	
}

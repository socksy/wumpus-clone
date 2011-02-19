/**
 * 
 */
package bgwumpus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles user input from the keyboard
 *
 */
public class UserInputHandler implements KeyListener {

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		processKeyPress(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		processKeyRelease(arg0);

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void processKeyPress(KeyEvent e){
		
		int key_code = e.getKeyCode();
		
		switch(key_code){
		
		case KeyEvent.VK_RIGHT: System.out.println("RIGHT"); break;
		case KeyEvent.VK_LEFT: System.out.println("LEFT"); break;
		case KeyEvent.VK_UP: System.out.println("UP"); break;
		case KeyEvent.VK_DOWN: System.out.println("DOWN"); break;
		}
		
	}
	
	public void processKeyRelease(KeyEvent e){
		
		int key_code = e.getKeyCode();
		
		switch(key_code){
		
		case KeyEvent.VK_RIGHT: System.out.println("RIGHT RELEASED"); break;
		case KeyEvent.VK_LEFT: System.out.println("LEFT RELEASED"); break;
		case KeyEvent.VK_UP: System.out.println("UP RELEASED"); break;
		case KeyEvent.VK_DOWN: System.out.println("DOWN RELEASED"); break;
		}
		
		
	}

}

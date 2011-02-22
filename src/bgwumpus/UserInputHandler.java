
package bgwumpus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles user input from the keyboard
 *
 */
public class UserInputHandler implements KeyListener {
	
	Player player;
	boolean shift_down = false;
	
	UserInputHandler(Player player){
		
		this.player = player;
		
	}

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
		
		//Are all these necessary?
			case KeyEvent.VK_RIGHT:  break;
			case KeyEvent.VK_LEFT:  break;
			case KeyEvent.VK_UP:  break;
			case KeyEvent.VK_DOWN:  break;
			case KeyEvent.VK_ESCAPE: System.exit(0); break; //quit upon hitting escape
			case KeyEvent.VK_R: GameLogic.map_revealed = true; break; 
			case KeyEvent.VK_SHIFT: shift_down = true; break;
			

		}
		
	}
	
	public void processKeyRelease(KeyEvent e){
		
		int key_code = e.getKeyCode();
		
		switch(key_code){
		
			//move up,down,left,or right when corresponding arrow key is released
			case KeyEvent.VK_LEFT: 
				if (!shift_down) {
					player.moveLeft(); 
				} else {
					player.shoot(0);
				}
				break;
			case KeyEvent.VK_UP:  
				if (!shift_down) {
					player.moveUp(); 
				} else {
					player.shoot(1);
				}
				break;
			case KeyEvent.VK_RIGHT: 
				if(!shift_down) {
					player.moveRight();
				} else {
					player.shoot(2);
				}
				break;
			case KeyEvent.VK_DOWN:  
				if (!shift_down) {
					player.moveDown();
				} else {
					player.shoot(3);
				}
				break;
			case KeyEvent.VK_R: GameLogic.map_revealed = false; break; 
			case KeyEvent.VK_SHIFT: shift_down = false; break;

		}
		
		
	}

}

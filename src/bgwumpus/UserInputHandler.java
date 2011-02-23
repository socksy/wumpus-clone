
package bgwumpus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles user input from the keyboard
 *
 */
public class UserInputHandler implements KeyListener {
	
	PlayableEntity player;
	boolean shift_down = false;
	
	UserInputHandler(PlayableEntity player){
		
		this.player = player;
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		processKeyPress(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		processKeyRelease(arg0);

	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
	public void processKeyPress(KeyEvent e){
		
		int key_code = e.getKeyCode();
		
		switch(key_code){
		
		//Are all these necessary?
			case KeyEvent.VK_B: GameLogic.setInGame(true); break;
			case KeyEvent.VK_ESCAPE: System.exit(0); break; //quit upon hitting escape
			case KeyEvent.VK_R: GameLogic.map_revealed = true; break; 
			case KeyEvent.VK_SHIFT: shift_down = true; break;
			

		}
		
	}
	
	public void processKeyRelease(KeyEvent e){
		
		int key_code = e.getKeyCode();
		
		switch(key_code){
		
			//move up,down,left,or right when corresponding arrow key is released
			case KeyEvent.VK_A: 
				if (!shift_down) {
					player.moveLeft(); 
				} else {
					player.shoot(0);
				}
				break;
			case KeyEvent.VK_W:  
				if (!shift_down) {
					player.moveUp(); 
				} else {
					player.shoot(1);
				}
				break;
			case KeyEvent.VK_D: 
				if(!shift_down) {
					player.moveRight();
				} else {
					player.shoot(2);
				}
				break;
			case KeyEvent.VK_S:  
				if (!shift_down) {
					player.moveDown();
				} else {
					player.shoot(3);
				}
				break;
			case KeyEvent.VK_R: GameLogic.map_revealed = false; break; 
			case KeyEvent.VK_SHIFT: shift_down = false; break;
			case KeyEvent.VK_M: GameLogic.switchMode(); break;


		}
		
		
	}

}

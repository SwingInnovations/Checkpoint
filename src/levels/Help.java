package levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Help extends BasicGameState{
    
    private Image backGround;
    private Image masterBackBtn;
    private Image activeBackBtn;
    private Image passiveBackBtn;
    
    public Help(int id){
    
    }
    
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException{
        backGround = new Image("./res/img/background.png");
        activeBackBtn = new Image("./res/img/actToMenu.png");
        passiveBackBtn = new Image("./res/img/passToMenu.png");
        masterBackBtn = passiveBackBtn;
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException{
        Point point = new Point(gc.getInput().getMouseX(), gc.getInput().getMouseY());
        Rectangle rect = new Rectangle(128, 512, masterBackBtn.getWidth(), masterBackBtn.getHeight());
        if(rect.contains(point)){
            masterBackBtn = activeBackBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                sbg.enterState(0);
            }
        }else{
            masterBackBtn = passiveBackBtn;
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        g.drawImage(backGround, 0, 0);
        g.drawImage(masterBackBtn, 128, 512);
    }
    
    public int getID(){
        return 2;
    }
}

package levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;

public class MainMenu extends BasicGameState {
    
    private Image backGround;
    private Image masterPlayBtn, masterHelpBtn, masterQuitBtn;
    private Image activePlayBtn, activeHelpBtn, activeQuitBtn;
    private Image passivePlayBtn, passiveHelpBtn, passiveQuitBtn;
    
    public MainMenu(int id){
    
    }
    
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException{
        backGround = new Image("./res/img/background.png");
        activePlayBtn = new Image("./res/img/actPlay.png");
        activeHelpBtn = new Image("./res/img/actHelp.png");
        activeQuitBtn = new Image("./res/img/actQuit.png");
        passivePlayBtn = new Image("./res/img/passPlay.png");
        passiveHelpBtn = new Image("./res/img/passHelp.png");
        passiveQuitBtn = new Image("./res/img/passQuit.png");
        
        masterPlayBtn = passivePlayBtn;
        masterHelpBtn = passiveHelpBtn;
        masterQuitBtn = passiveQuitBtn;
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException{
        Point point = new Point(gc.getInput().getMouseX(), gc.getInput().getMouseY());
        Rectangle playRect = new Rectangle(128, 196, masterPlayBtn.getWidth(), masterPlayBtn.getHeight());
        Rectangle helpRect = new Rectangle(128, 292, masterHelpBtn.getWidth(), masterHelpBtn.getHeight());
        Rectangle quitRect = new Rectangle(128, 388, masterQuitBtn.getWidth(), masterQuitBtn.getHeight());
        if(playRect.contains(point)){
            masterPlayBtn = activePlayBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                sbg.enterState(1);
            }
        }else{
            masterPlayBtn = passivePlayBtn;
        }
        
        if(helpRect.contains(point)){
            masterHelpBtn = activeHelpBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                sbg.enterState(2);
            }
        }else{
            masterHelpBtn = passiveHelpBtn;
        }
        
        if(quitRect.contains(point)){
            masterQuitBtn = activeQuitBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                System.exit(0);
            }
        }else{
            masterQuitBtn = passiveQuitBtn;
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException{
        g.drawImage(backGround, 0, 0);
        g.drawImage(masterPlayBtn, 128, 196);
        g.drawImage(masterHelpBtn, 128, 292);
        g.drawImage(masterQuitBtn, 128, 388);
    }
    
    public int getID(){
        return 0;
    }
    
}

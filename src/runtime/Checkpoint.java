package runtime;

import levels.Help;
import levels.Level;
import levels.MainMenu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Checkpoint extends StateBasedGame{

    private static final String GAME_NAME = "Checkpoint";
    private static final int MAIN_MENU = 0;
    private static final int GAME = 1;
    private static final int HELP = 2;
    public Checkpoint(String name){
        super(name);
        this.addState(new MainMenu(MAIN_MENU));
        this.addState(new Level(GAME));
        this.addState(new Help(HELP));
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(MAIN_MENU).init(gc, this);
        this.getState(GAME).init(gc, this);
        this.getState(HELP).init(gc, this);
        this.enterState(MAIN_MENU);
    }
    
    public static void main(String[] args){
        try{
            AppGameContainer app = new AppGameContainer(new Checkpoint(GAME_NAME), 512, 768, false);
            app.setVSync(true);
            app.setTargetFrameRate(60);
            app.setShowFPS(false);
            app.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
}

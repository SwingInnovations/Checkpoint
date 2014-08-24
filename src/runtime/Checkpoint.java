package runtime;

import levels.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Checkpoint extends StateBasedGame{

    private static final String GAME_NAME = "Checkpoint";
    private static final int GAME = 0;
    public Checkpoint(String name){
        super(name);
        this.addState(new Level(GAME));
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.getState(GAME).init(gc, this);
        this.enterState(GAME);
    }
    
    public static void main(String[] args){
        try{
            AppGameContainer app = new AppGameContainer(new Checkpoint(GAME_NAME), 512, 768, false);
            app.setVSync(true);
            app.setTargetFrameRate(60);
            app.setShowFPS(true);
            app.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
}

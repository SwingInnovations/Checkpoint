package levels;

import engine.Player;
import engine.Enemy;
import engine.NPC;
import engine.Block;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;

public class Level extends BasicGameState {
    private boolean GameOver;
    private boolean Pause;
    private Player p1;
    private Player p2;
    private float SPEED = 0.25f;
    private Image background;
    private Point mousePoint;
    private Image masterResumeBtn, masterBackBtn, masterRetryBtn;
    private Image activeResumeBtn, activeBackBtn, activeRetryBtn;
    private Image passiveResumeBtn, passiveBackBtn, passiveRetryBtn;
    private float ctime;
    private float ctime2;
    private int SCORE;
    private int AMT_MISSED;
    private ArrayList<Enemy> enemy;
    private ArrayList<NPC> npc;
    private ArrayList<Block> bullet;
    
    public Level(int id){
    
    }
    
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException
    {
        GenPlayers();
        bullet = new ArrayList<>();
        enemy = new ArrayList<>();
        npc = new ArrayList<>();
        LoadRes();
        activeResumeBtn = new Image("./res/img/actResume.png");
        activeBackBtn = new Image("./res/img/actToMenu.png");
        activeRetryBtn = new Image("./res/img/actRetry.png");
        
        passiveResumeBtn = new Image("./res/img/passResume.png");
        passiveBackBtn = new Image("./res/img/passToMenu.png");
        passiveRetryBtn = new Image("./res/img/passRetry.png");
        
        masterResumeBtn = passiveResumeBtn;
        masterRetryBtn = passiveRetryBtn;
        masterBackBtn = passiveBackBtn;
    }
    
    private void Reset(){
        if(!bullet.isEmpty()){
            bullet.clear();
        }
        
        if(!enemy.isEmpty()){
            enemy.clear();
        }
        
        if(!npc.isEmpty()){
            npc.clear();
        }
        
        GenPlayers();
        try{
            LoadRes();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException{
        Input in = gc.getInput();
        if(in.isKeyPressed(Input.KEY_ESCAPE)){
            Pause =! Pause;
        }
        if(!GameOver){
            if(!Pause){
                this.ctime += delta/10;
                this.ctime2 += delta/10;
                if(ctime > 25){
                    GenerateNPC();
                    ctime = 0;
                }
                if(ctime2 > 50){
                    GenerateEnemy();
                    ctime2 = 0;
                }
                HandlePlayers(gc, delta);
                HandleAuto(delta);
                
            }else{
                //display pause screen
                HandlePauseScreen(gc, sbg);
            }
        }else{
            //display Game over screen
            HandleGameOverScreen(gc, sbg);
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException{
       if(!GameOver){
            if(!Pause){
                g.drawImage(background, 0, 0);
                g.drawString("Score: " + SCORE, 64, 740);
                p1.Render(g);
                p2.Render(g);
                DrawAuto(g);
            }else{
                //display pause screen
                RenderPauseScreen(g);
            }
        }else{
            //display Game over screen
           RenderGameOverScreen(g);
        }
    }
    
    public int getID(){
        return 1;
    }
    
    private void GenPlayers(){
        p1 = new Player(new Vector2f(128, 704), new Vector2f(32, 32), 0);
        p2 = new Player(new Vector2f(384, 64), new Vector2f(32, 32), 1);
    }
    
    private void HandlePlayers(GameContainer gc, int delta)throws SlickException{
        Input input = gc.getInput();
        
        //player 1 movement
        if(input.isKeyDown(Input.KEY_A)){
            p1.GetPos().x -= SPEED * delta;
            p1.SetX(p1.GetPos().x);
            if(p1.GetPos().x < 0){
                p1.GetPos().x = 0;
                p1.SetX(p1.GetPos().x);
            }
        }
        
        if(input.isKeyDown(Input.KEY_D)){
            p1.GetPos().x += SPEED * delta;
            p1.SetX(p1.GetPos().x);
            if(p1.GetPos().x + p1.GetDim().x > 256){
                p1.GetPos().x = 256 - p1.GetDim().x;
                p1.SetX(p1.GetPos().x);
            }
        }
        
        if(input.isKeyPressed(Input.KEY_W)){
            Fire(p1, 0, delta);
        }
        
        //Player 2 movement
        if(input.isKeyDown(Input.KEY_LEFT)){
            p2.GetPos().x -= SPEED * delta;
            p2.SetX(p2.GetPos().x);
            if(p2.GetPos().x < 256){
                p2.GetPos().x = 256;
                p2.SetX(p2.GetPos().x);
            }
        }
        
        if(input.isKeyDown(Input.KEY_RIGHT)){
            p2.GetPos().x += SPEED * delta;
            p2.SetX(p2.GetPos().x);
            if(p2.GetPos().x + p2.GetDim().x > 512){
                p2.GetPos().x = 512 - p2.GetDim().x;
                p2.SetX(p2.GetPos().x);
            }
        }
        
        if(input.isKeyPressed(Input.KEY_UP)){
            Fire(p2, 1, delta);
        }
    }
    
    private void HandleAuto(int delta){
        for(int i = 0; i < bullet.size(); i++){
            if(bullet.size() > 0){
                if(bullet.get(i).GetDirection() == 0){
                    bullet.get(i).GetPos().y -= (2*SPEED)* delta;
                    bullet.get(i).SetY(bullet.get(i).GetPos().y);
                    if(bullet.get(i).GetPos().y < 0){
                        bullet.remove(i);
                    }
                }else{
                    bullet.get(i).GetPos().y += (2*SPEED)* delta;
                    bullet.get(i).SetY(bullet.get(i).GetPos().y);
                    if(bullet.get(i).GetPos().y > 768){
                        bullet.remove(i);
                    }
                }
            }
        }
        for(int j = 0; j < enemy.size(); j++){
                if(enemy.size() > 0){
                    if(enemy.get(j).GetDirection() == 1){
                        enemy.get(j).GetPos().y += (2*SPEED)*delta;
                        enemy.get(j).SetY(enemy.get(j).GetPos().y);
                        if(enemy.get(j).GetPos().y > 768){
                            AMT_MISSED+=1;
                            enemy.remove(j);
                        }
                    }else{
                        enemy.get(j).GetPos().y -= (2*SPEED)*delta;
                        enemy.get(j).SetY(enemy.get(j).GetPos().y);
                        if(enemy.get(j).GetPos().y < 0){
                            AMT_MISSED+=1;
                            enemy.remove(j);
                        }
                }
                for(int i = 0; i < bullet.size(); i++){
                    if(i < enemy.size()){
                       if(bullet.get(i).HasIntersected(enemy.get(j))){
                            System.out.println("I: " + i);
                            System.out.println("J: " + j);
                            SCORE+=2;
                            enemy.remove(j);
                            bullet.remove(i);
                        }
                    }
                }

            }
            
            for(int k = 0; k < npc.size(); k++){
                if(npc.size() > 0){
                    if(npc.get(k).GetDirection() == 1){
                        npc.get(k).GetPos().y += (2*SPEED)*delta;
                        npc.get(k).SetY(npc.get(k).GetPos().y);
                        if(npc.get(k).GetPos().y > 768){
                            SCORE-=1;
                            npc.remove(k);
                        }
                    }else{
                        npc.get(k).GetPos().y -= (2*SPEED)*delta;
                        npc.get(k).SetY(npc.get(k).GetPos().y);
                        if(npc.get(k).GetPos().y < 0){
                            SCORE-=1;
                            npc.remove(k);
                        }
                    }
                    for(int i = 0; i < bullet.size(); i++){
                        if(i < npc.size()){
                            if(bullet.get(i).HasIntersected(npc.get(k)) && i <= k){
                                System.out.println("I: " + i);
                                System.out.println("K: " + k);
                                SCORE-=1;
                                npc.remove(k);
                                bullet.remove(i);
                            }
                        }
                    }
                 }
               }
            }
        
        if(AMT_MISSED >= 40){
            GameOver = true;
        }
    }
    
    private void DrawAuto(Graphics g){
        for(int i = 0; i < bullet.size(); i++){
            bullet.get(i).Render(g);
        }
        for(int j = 0; j < enemy.size(); j++){
            enemy.get(j).Render(g);
        }
        for(int k = 0; k < npc.size(); k++){
            npc.get(k).Render(g);
        }
    }
    
    private void Fire(Player p, int dir, int delta){
        if(dir == 0){
            //Shooting upward
            bullet.add(new Block(new Vector2f(p.GetPos().x + (p.GetDim().x/4), p.GetPos().y), new Vector2f(16, 16), 0));
        }else{
            bullet.add(new Block(new Vector2f(p.GetPos().x + (p.GetDim().x/4), p.GetPos().y), new Vector2f(16, 16), 1));
        }
        for(int i = 0; i < bullet.size(); i++){
            try{
                bullet.get(i).SetImage(new Image("./res/img/bullet.png"));
            }catch(SlickException e){
                e.printStackTrace();
            }
        }
    }
    
    private void LoadRes()throws SlickException{
        background = new Image("./res/img/background.png");
        p1.SetImage(new Image("./res/img/ship_player_U.png"));
        p2.SetImage(new Image("./res/img/ship_player_D.png"));
    }
    
    private void GenerateEnemy(){
        Random rand1 = new Random();
        int rDir = rand1.nextInt(2);
        Random rand2 = new Random();
        if(rDir == 1){
            int pX = rand2.nextInt(256);
            enemy.add(new Enemy(new Vector2f(pX, -32), new Vector2f(32, 32), 1));
            for(int i = 0; i < enemy.size(); i++){
                try{
                    enemy.get(i).SetImage(new Image("./res/img/ship_enemy_D.png"));
                }catch(SlickException e){
                    e.printStackTrace();}
            }
        }else{
            int pX = rand2.nextInt(512);
            if(pX > 256){
                enemy.add(new Enemy(new Vector2f(pX, 768), new Vector2f(32, 32), 2));
            }
            for(int i = 0; i < enemy.size(); i++){
                try{
                    enemy.get(i).SetImage(new Image("./res/img/ship_enemy_U.png"));
                }catch(SlickException e){
                    e.printStackTrace();}
            }
        }
    }
    
    private void HandlePauseScreen(GameContainer gc, StateBasedGame sbg){
        mousePoint = new Point(gc.getInput().getMouseX(), gc.getInput().getMouseY());
        Rectangle resumeRect = new Rectangle(128, 196, masterResumeBtn.getWidth(), masterResumeBtn.getHeight());
        Rectangle retryRect = new Rectangle(128, 292, masterRetryBtn.getWidth(), masterRetryBtn.getHeight());
        Rectangle backRect = new Rectangle(128, 388, masterBackBtn.getWidth(), masterBackBtn.getHeight());
        if(resumeRect.contains(mousePoint)){
            masterResumeBtn = activeResumeBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                Pause =! Pause;
            }
        }else{
            masterResumeBtn = passiveResumeBtn;
        }
        if(retryRect.contains(mousePoint)){
            masterRetryBtn = activeRetryBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                //Reset 
                Reset();
                Pause = false;
            }
        }else{
            masterRetryBtn = passiveRetryBtn;
        }
        if(backRect.contains(mousePoint)){
            masterBackBtn = activeBackBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                sbg.enterState(0);
            }
        }else{
            masterBackBtn = passiveBackBtn;
        }
        
    }
    
    private void RenderPauseScreen(Graphics g){
        g.drawImage(background, 0, 0);
        g.drawImage(masterResumeBtn, 128, 196);
        g.drawImage(masterRetryBtn, 128, 292);
        g.drawImage(masterBackBtn, 128, 388);
    }
    
    private void HandleGameOverScreen(GameContainer gc, StateBasedGame sbg){
        mousePoint = new Point(gc.getInput().getMouseX(), gc.getInput().getMouseY());
        Rectangle retryRect = new Rectangle(128, 292, masterRetryBtn.getWidth(), masterRetryBtn.getHeight());
        Rectangle backRect = new Rectangle(128, 388, masterBackBtn.getWidth(), masterBackBtn.getHeight());
        
        if(retryRect.contains(mousePoint)){
            masterRetryBtn = activeRetryBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                //Reset 
                Reset();
                GameOver = false;
            }
        }else{
            masterRetryBtn = passiveRetryBtn;
        }
        if(backRect.contains(mousePoint)){
            masterBackBtn = activeBackBtn;
            if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                sbg.enterState(0);
            }
        }else{
            masterBackBtn = passiveBackBtn;
        }
    }
    
    private void RenderGameOverScreen(Graphics g){
        g.drawImage(background, 0, 0);
        g.drawImage(masterRetryBtn, 128, 292);
        g.drawImage(masterBackBtn, 128, 388);
    }
    
    private void GenerateNPC(){
        Random rand1 = new Random();
        int rDir = rand1.nextInt(2);
        Random rand2 = new Random();
        if(rDir == 1){
            int pX = rand2.nextInt(256);
            npc.add(new NPC(new Vector2f(pX, -32), new Vector2f(32, 32), 1));
            for(int i = 0; i < npc.size(); i++){
                try{
                    npc.get(i).SetImage(new Image("./res/img/ship_npc_D.png"));
                }catch(SlickException e){
                    e.printStackTrace();
                }
            }
        }else{
            int pX = rand2.nextInt(512);
            if(pX > 256){
                npc.add(new NPC(new Vector2f(pX, 256), new Vector2f(32, 32), 2));
            }
            for(int i = 0; i < npc.size(); i++){
                try{
                    npc.get(i).SetImage(new Image("./res/img/ship_npc_U.png"));
                }catch(SlickException e){
                    e.printStackTrace();
                }
            }
        }
    }
}


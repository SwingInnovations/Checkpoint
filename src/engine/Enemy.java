package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Enemy implements Entity {

    private Vector2f position;
    private Vector2f dimension;
    private Polygon poly;
    private int direction;
    private Image image;
    private boolean DebugRender;
    
    public Enemy(Vector2f pos, Vector2f dim, int d){
        SetPos(pos);
        SetDim(dim);
        InitPoly();
        SetDirection(d);
    }
    
    @Override
    public void SetPos(Vector2f pos) {
        this.position = pos;
    }

    @Override
    public void SetDim(Vector2f dim) {
        this.dimension = dim;
    }

    @Override
    public void InitPoly() {
        poly = new Polygon(new float[]{
            position.x, position.y,
            position.x, position.y + dimension.y,
            position.x + dimension.x, position.y + dimension.y,
            position.x + dimension.x, position.y
        });
    }

    @Override
    public void SetDirection(int d) {
        this.direction = d;
    }
    
    public void SetX(float _x){ position.x = _x; poly.setX(_x); }
    public void SetY(float _y){ position.y = _y; poly.setY(_y); }
    
    public void SetDebugRender(boolean val){
        DebugRender = val;
    }
    
    public void Render(Graphics g){
        if(DebugRender){
            g.setColor(Color.red);
            g.fill(poly);
            g.draw(poly);
        }
        //draw image
    }
    
    public void SetImage(Image img){
        this.image = img;
    }

    @Override
    public Vector2f GetPos() {
        return position;
    }

    @Override
    public Vector2f GetDim() {
        return dimension;
    }

    @Override
    public Polygon GetPoly() {
        return poly;
    }

    @Override
    public int GetDirection() {
        return direction;
    }

    @Override
    public boolean HasIntersected(Entity other) {
        return poly.intersects(other.GetPoly());
    }
}
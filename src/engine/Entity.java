package engine;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Polygon;

public interface Entity {
    public void SetPos(Vector2f pos);
    public void SetDim(Vector2f dim);
    public void InitPoly();
    public void SetDirection(int d);
    public Vector2f GetPos();
    public Vector2f GetDim();
    public Polygon GetPoly();
    public int GetDirection();
    public boolean HasIntersected(Entity other);
}

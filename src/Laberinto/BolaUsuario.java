
package Laberinto;

import java.awt.Color;

public class BolaUsuario extends Bolas
{
    protected int vel, punto=0;
    protected boolean arriba, abajo, izquierda, derecha;
    protected Color F;

    @Override
    public Color Farbe()
    {
        return F;
    }
    public int puntos(){return punto;}
    
    public int puntos(int w)
    {        
        punto +=w;
        return 1;
    }
    
    public BolaUsuario(Color F, int vel, float x, float y, int diametro)
    {
        this.F=F; this.vel=vel; this.x=x; this.y=y; this.diameter=diametro;
    }
}

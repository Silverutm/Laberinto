/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Laberinto;

import java.awt.Color;

/**
 *
 * @author Moro
 */
public class BolaBonus extends Bolas
{
    protected Color F;
    protected int punto;
    @Override
    public int puntos(){return punto;}
    public Color Farbe()
    {
        return F;
    }
    public BolaBonus(float x, float y, int diametro, Color F, int punto)
    {
        this.x=x; this.y=y; this.diameter=diametro; this.F=F; this.punto=punto;
    }
}

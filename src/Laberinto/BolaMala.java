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
public class BolaMala extends Bolas
{
    @Override
    public int puntos(){return -1;}
    public Color Farbe()
    {
        return Color.BLUE;
    }
    
    public BolaMala(float x, float y,  int diametro)
    {
        this.x=x; this.y=y; this.diameter=diametro;
    }
}

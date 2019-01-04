
package Laberinto;

import java.awt.Color;

public abstract class Bolas 
{
    public float x, y ;    
    public int diameter;
    public boolean activo;
    
    
    public abstract int puntos();
    
    public abstract Color Farbe();
    
}

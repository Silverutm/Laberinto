package Laberinto;

import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class LabyrinthderZeit extends JComponent
{
    private static final long serialVersionUID = 1L;                            //id que se agrego pero no se para que sirva
    private final static int ancho = 320;                                       //ancho pantalla
    private final static int altura = 320;                                      //alto pantalla 
    private static int tiempo=0, diametro;   
    private  int  ttiempo=1, seg=0,vel=10, x[]={3,183,3,103,283,243,23,183,123,63,283,3,83,223,163,283,43,143,203,43,123, 3, 223, 303,283, 303, 263, 203, 203, 23},
            y[]={3,3,23,23,23,63,83,83,103,123,143,163,183,183,203,223,243,243,243,283,283, 303, 303, 303, 283, 203, 103, 100, 63, 263}, cc=0, numeroactivos=5, intervalo=200;
    int i, j, bolbon=0; 
    private boolean ghj=true;
    private static int [][]  matriz = new int[350][350];
    private ArrayList<BolaUsuario> user = new ArrayList <>();
    private ArrayList<Bolas> Bolasaccion = new ArrayList <>();
    
    
    
    private void hacermatriz(int xx, int yy, int xx2, int yy2)
    {
       int i, j;
       for (i=yy; i<=yy2; ++i)
           for (j=xx; j<=xx2; ++j)
               matriz[j][i]=1;
    }
    
    private boolean verarriba(int xx, int yy, int dm)
    {
        int i;
        for (i=0; i<=dm; ++i)
            if (matriz[xx+i][yy]==1 ) return true;
        return false;
    }
    
    private boolean verabajo(int xx, int yy, int dm)
    {
        int i;
        for (i=0; i<=dm; ++i)
            if (matriz[xx+i][yy+dm]==1  ) return true;
        return false;
    }
    
    private boolean verizquierda(int xx, int yy, int dm)
    {
        int i;
        for (i=0; i<=dm; ++i)
            if (matriz[xx][yy+i]==1  ) return true;
        return false;
    }
    
    private boolean verderecha(int xx, int yy, int dm)
    {
        int i;
        for (i=0; i<=dm; ++i)
            if (matriz[xx+dm][yy+i]==1  ) return true;
        return false;
    }
    
    public LabyrinthderZeit() 
    {
        setPreferredSize(new Dimension(ancho, altura));                         //ventana
        
        BolaUsuario bu = new BolaUsuario(Color.BLACK, vel, 3, 123, diametro);
        user.add(bu);
        bu = new BolaUsuario(Color.GREEN, vel , 203, 123, diametro);
        user.add(bu);
        
        BolaBuena bb;
        BolaMala bm;
        for (i=-1; ++i<5;)
        {
            bb=new BolaBuena(x[++cc], y[cc], diametro);
            Bolasaccion.add(bb);
        }
        for (i=-1; ++i<4;)
        {
            bm=new BolaMala(x[++cc], y[cc], diametro);
            Bolasaccion.add(bm);
        }
        
        		
        addKeyListener(new KeyAdapter() 
        {                                                                       //funcion para los eventos de teclado
                public void keyPressed(KeyEvent e) 
                {                                                               //función que manipula las teclas
                    actualiza(e.getKeyCode(), true);
                }
                public void keyReleased(KeyEvent e) 
                {
                    actualiza(e.getKeyCode(), false);
                }
                
                private void actualiza(int keyCode, boolean pressed)            //Sirve para saber que tecla presionaron
                {
                    switch (keyCode) 
                    {
                        case KeyEvent.VK_UP:                                    //tecla arriva
                            user.get(0).arriba=pressed ;
                            break;
                        case KeyEvent.VK_DOWN:                                  //tecla abajo
                            user.get(0).abajo = pressed;
                            break;
                        case KeyEvent.VK_LEFT:
                            user.get(0).izquierda = pressed;                                //agrega evento tecla izq
                            break;
                        case KeyEvent.VK_RIGHT:
                            user.get(0).derecha = pressed;                                  //agrega evento tecla der
                            break;
                        case KeyEvent.VK_W:
                            user.get(01).arriba=pressed ;
                            break;
                        case KeyEvent.VK_S:
                            user.get(1).abajo = pressed;
                            break;
                        case KeyEvent.VK_D:
                            user.get(1).derecha = pressed;
                            break;
                        case KeyEvent.VK_A:
                            user.get(1).izquierda = pressed;
                            break;
                    }
                }
            });
        setFocusable(true);                                           //función que no permite cambios por parte del usuario
    }
    private float limites(float valor, float max)                    //funcion que delimita las orillas
    {                                                                           //Para no salirse de la pantalla
        if (valor > max)
            return max;
        if (valor < 0)                                                        //En que caso de salirse se regresa a la orilla
            return 0;
        return valor;
    }
    private void movimientos()
    {
    	
        for (BolaUsuario bu: user)
        {
            float bas;
            if (bu.arriba) 
            {
                bas=limites(bu.y+ - bu.vel*0.02f , altura - bu.diameter);
                if(verarriba(  Math.round(bu.x), Math.round(bas), bu.diameter)==false) bu.y = bas;                  
            }
            else if (bu.abajo)
            {
                bas=limites(bu.y + bu.vel*0.02f , altura - bu.diameter);
                if(verabajo(  Math.round(bu.x), Math.round(bas), bu.diameter)==false) bu.y = bas;
            }


             if (bu.izquierda)
            {
                bas=limites(bu.x - bu.vel*0.02f ,  ancho - bu.diameter);
                if(verizquierda(  Math.round(bas), Math.round(bu.y), bu.diameter)==false) bu.x = bas;
            }
            else if (bu.derecha)
            {
                 bas=limites(bu.x + bu.vel*0.02f , ancho - bu.diameter);
                 if(verderecha(  Math.round(bas), Math.round(bu.y), bu.diameter)==false) bu.x = bas;
            }
        }
        
        
           
    }
    public void paint(Graphics g)
    {    	
        /*paredes, el numero no implica orden en las delimitaciones
        solo esta enumerado para identficarlas, pero si en los estan semi enlazadas*/
        g.setColor(Color.white);                                                //en blanco
        g.fillRect(0, 0, ancho, altura);                                        //del tamaño de la ventana
        
        
        for (BolaUsuario bu: user)
        {
            g.setColor(bu.Farbe());                                                //color de mi objeto
            g.fillOval(Math.round(bu.x), Math.round(bu.y), bu.diameter, bu.diameter);             
            if (numeroactivos==0)
                if( bu.x<=5 &&  bu.y<=5) 
                {
                    if (numeroactivos==0) JOptionPane.showMessageDialog(this, "Termino el juego\nPuntos verde" + user.get(1).puntos() + 
                            "\nPuntos Negro" + user.get(0).puntos() );
                        numeroactivos=-1; 
                    System.exit(0);
                }
        }
        j=Bolasaccion.size();
        for (i=-1; ++i<j;)
        {            
            Bolas bo=Bolasaccion.get(i);
            g.setColor(bo.Farbe());
            g.fillOval(Math.round(bo.x), Math.round(bo.y), bo.diameter, bo.diameter);
            BolaUsuario bu;
            int k, l;
            for (k=-1; ++k<2; )
            {            
                bu=user.get(k);
                if(Math.abs(bu.x-bo.x)<=bo.diameter && Math.abs(bu.y-bo.y)<=bo.diameter)
                {                    
                    l = k==1 ? 0:1;
                    switch (bo.puntos())
                    {
                        case 1: case 2:
                            bu.puntos(bo.puntos());
                            user.get(k).vel=user.get(l).vel=vel;                             
                           break;
                        case 3:
                            user.get(l).vel=0;
                            break;
                        case 4:
                            user.get(l).vel=vel/2;
                            break;
                        case 5:
                            user.get(k).vel=vel*2;
                            break;
                    }                                        
                    Bolasaccion.remove(i);
                    --i; --j;
                    break;
                }                            
           }        
        }
        
        g.drawString(user.get(0).puntos() + " Puntos negro", 220, 100);
        g.drawString(user.get(1).puntos() + " Puntos verde", 50, 100);
                    	
        ++seg;
        ttiempo +=seg/intervalo;
        tiempo -=seg/intervalo;
        seg %=intervalo;        
        g.drawString(tiempo +"tiempo", 260, 150);
        if( ttiempo%40==0)      //Cambiarlas de lugar
        {
            ++ttiempo;
            for (Bolas bo : Bolasaccion)                                            
                if (++cc<30)
                {
                    bo.x=x[cc]; bo.y =y[cc];
                }                       
        }   
        else if (ttiempo%60==0)    //Hacer las malas buenas y las buenas malas
        {
            ++ttiempo;
            i = Bolasaccion.size();
            for (j=-1; ++j<i;)                
            {
                float  xr=Bolasaccion.get(j).x, yr=Bolasaccion.get(j).y;                    
                if (Bolasaccion.get(j) instanceof BolaBuena)                                            
                {
                    Bolasaccion.add(new BolaMala(xr, yr, diametro));
                    --numeroactivos;
                }                                            
                else if (Bolasaccion.get(j) instanceof BolaMala)
                {
                    Bolasaccion.add(new BolaBuena(xr, yr, diametro));
                    ++numeroactivos;
                }
                Bolasaccion.remove(j);
                --i; --j;
            }            
        }
        else if (ttiempo%15==0)
        {            
            ++ttiempo;
            if (++cc<30)
                Bolasaccion.add(new BolaBuena(x[cc], y[cc], diametro));
        }
        else if (ttiempo%14==0)
        {
            ++ttiempo;
            ++bolbon;
            bolbon %=4;
            if (++cc<30)
            {
                Color f; int p;
                if (bolbon==0) f=Color.YELLOW;                
                else if (bolbon==1) f=Color.DARK_GRAY;
                else if (bolbon==2) f=Color.CYAN;
                else f=Color.MAGENTA;                    
                Bolasaccion.add(new BolaBonus(x[cc], y[cc], diametro, f, bolbon+2)); 
            }                
        }
        
        if (tiempo==0) {user.get(0).y=3; user.get(0).x=3; numeroactivos=0;}
        
        //comienzan las paredes
        g.setColor(Color.BLACK);
    	g.drawLine(0,20,20,20);
        g.drawLine(60,20,80,20);
        g.drawLine(100,20,120,20);
        g.drawLine(140, 20, 160, 20);
        g.drawLine(240, 20, 260, 20);// ////////////
        g.drawLine(40, 40, 100, 40);
        g.drawLine(120, 40, 140, 40);
        g.drawLine(160, 40, 180, 40);
        g.drawLine(200, 40,220, 40);
        g.drawLine(260, 40, 300, 40);/////////////////
        g.drawLine(20, 60, 40, 60);
        g.drawLine(60, 60, 100, 60);
        g.drawLine(120, 60, 160, 60);
        g.drawLine(240, 60, 300, 60);////////////////
        g.drawLine(20, 80, 100, 80);
        g.drawLine(120, 80, 140, 80);
        g.drawLine(160, 80, 180, 80);
        g.drawLine(200, 80, 220, 80);
        g.drawLine(280, 80, 320, 80);////////////
        g.drawLine(40, 100, 100, 100);
        g.drawLine(200, 100, 220, 100);
        g.drawLine(260, 100, 300, 100);/////////////
        g.drawLine(60, 120, 80, 120);
        g.drawLine(160, 120, 180, 120);
        g.drawLine(200, 120, 220, 120);/////////
        g.drawLine(120, 140, 140, 140);
        g.drawLine(160, 140, 220, 140);
        g.drawLine(260, 140, 330, 140);//////////////
        g.drawLine(40, 160, 80, 160);
        g.drawLine(140, 160, 160, 160);////////
        g.drawLine(20, 180, 100, 180);
        g.drawLine(240, 180, 280, 180);/////////////
        g.drawLine(0, 200, 20, 200);
        g.drawLine(80, 200, 160, 200);
        g.drawLine(240, 200, 320, 200);/////////////
        g.drawLine(20, 220, 120, 220);
        g.drawLine(160, 220, 200, 220);
        g.drawLine(220, 220, 240, 220);
        g.drawLine(260, 220, 300, 220);//////////////
        g.drawLine(0, 240, 20, 240);
        g.drawLine(40, 240, 60, 240);
        g.drawLine(80, 240, 100, 240);
        g.drawLine(120, 240, 160, 240);
        g.drawLine(200, 240, 220, 240);
        g.drawLine(240, 240, 260, 240);
        g.drawLine(300, 240, 320, 240);/////////////////////
        g.drawLine(20, 260, 40, 260);
        g.drawLine(60, 260, 80, 260);
        g.drawLine(100, 260, 120, 260);
        g.drawLine(140, 260, 160, 260);
        g.drawLine(220, 260, 240, 260);//////////////////
        g.drawLine(40, 280, 80, 280);
        g.drawLine(140, 280, 160, 280);
        g.drawLine(180, 280, 200, 280);
        g.drawLine(220, 280, 240, 280);////////////////
        g.drawLine(80, 300, 160, 300);
        g.drawLine(180, 300, 220, 300);
        g.drawLine(240, 300, 260, 300);
        g.drawLine(280, 300, 300, 300);//////////////
        
        
        g.setColor(Color.BLUE);
    	g.drawLine(20,40,20,60);
        g.drawLine(20,80,20,160);
        g.drawLine(20,260,20,300);////////////
        g.drawLine(40,0,40,20);
        g.drawLine(40,40,40,60);
        g.drawLine(40,100,40,160);
        g.drawLine(40,200,40,240);
        g.drawLine(40,260,40,300);///////////////
        g.drawLine(60,120,60,140);
        g.drawLine(60,240,60,260);
        g.drawLine(60,280,60,300);//////
        g.drawLine(80,0,80,20);
        g.drawLine(80,120,80,160);
        g.drawLine(80,220,80,240);///////////
        g.drawLine(100,20,100,40);
        g.drawLine(100,100,100,180);
        g.drawLine(100,240,100,300);////////
        g.drawLine(120,20,120,40);
        g.drawLine(120,80,120,120);
        g.drawLine(120,160,120,200);
        g.drawLine(120,300,120,320);////////
        g.drawLine(140,20,140,40);
        g.drawLine(140,120,140,140);
        g.drawLine(140,180,140,220);/////////
        g.drawLine(160,40,160,60);
        g.drawLine(160,80,160,120);
        g.drawLine(160,140,160,160);
        g.drawLine(160,180,160,200);
        g.drawLine(160,220,160,280);///////////
        g.drawLine(180,20,180,40);
        g.drawLine(180,80,180,120);
        g.drawLine(180,160,180,200);
        g.drawLine(180,240,180,260);////////////
        g.drawLine(200,40,200,80);
        g.drawLine(200,100,200,120);
        g.drawLine(200,160,200,220);
        g.drawLine(200,240,200,260);
        g.drawLine(200,280,200,300);/////////////
        g.drawLine(220,0,220,20);
        g.drawLine(220,40,220,80);
        g.drawLine(220,100,220,120);
        g.drawLine(220,160,220,200);
        g.drawLine(220,260,220,280);/////////
        g.drawLine(240,20,240,60);
        g.drawLine(240,100,240,180);
        g.drawLine(240,260,240,280);//////
        g.drawLine(260,60,260,100);
        g.drawLine(260,240,260,300);/////
        g.drawLine(280,160,280,180);
        g.drawLine(280,220,280,240);
        g.drawLine(280,260,280,300);//////
        g.drawLine(300,20,300,40);
        g.drawLine(300,100,300,120);
        g.drawLine(300,160,300,200);
        g.drawLine(300,240,300,300);////
        
        if (ghj==true)
        {
            hacermatriz(0,20,20,20);
            hacermatriz(60,20,80,20);
            hacermatriz(100,20,120,20);
            hacermatriz(140, 20, 160, 20);
            hacermatriz(240, 20, 260, 20);// ////////////
            hacermatriz(40, 40, 100, 40);
            hacermatriz(120, 40, 140, 40);
            hacermatriz(160, 40, 180, 40);
            hacermatriz(200, 40,220, 40);
            hacermatriz(260, 40, 300, 40);/////////////////
            hacermatriz(20, 60, 40, 60);
            hacermatriz(60, 60, 100, 60);
            hacermatriz(120, 60, 160, 60);
            hacermatriz(240, 60, 300, 60);////////////////
            hacermatriz(20, 80, 100, 80);
            hacermatriz(120, 80, 140, 80);
            hacermatriz(160, 80, 180, 80);
            hacermatriz(200, 80, 220, 80);
            hacermatriz(280, 80, 320, 80);////////////
            hacermatriz(40, 100, 100, 100);
            hacermatriz(200, 100, 220, 100);
            hacermatriz(260, 100, 300, 100);/////////////
            hacermatriz(60, 120, 80, 120);
            hacermatriz(160, 120, 180, 120);
            hacermatriz(200, 120, 220, 120);/////////
            hacermatriz(120, 140, 140, 140);
            hacermatriz(160, 140, 220, 140);
            hacermatriz(260, 140, 330, 140);//////////////
            hacermatriz(40, 160, 80, 160);
            hacermatriz(140, 160, 160, 160);////////
            hacermatriz(20, 180, 100, 180);
            hacermatriz(240, 180, 280, 180);/////////////
            hacermatriz(0, 200, 20, 200);
            hacermatriz(80, 200, 160, 200);
            hacermatriz(240, 200, 320, 200);/////////////
            hacermatriz(20, 220, 120, 220);
            hacermatriz(160, 220, 200, 220);
            hacermatriz(220, 220, 240, 220);
            hacermatriz(260, 220, 300, 220);//////////////
            hacermatriz(0, 240, 20, 240);
            hacermatriz(40, 240, 60, 240);
            hacermatriz(80, 240, 100, 240);
            hacermatriz(120, 240, 160, 240);
            hacermatriz(200, 240, 220, 240);
            hacermatriz(240, 240, 260, 240);
            hacermatriz(300, 240, 320, 240);/////////////////////
            hacermatriz(20, 260, 40, 260);
            hacermatriz(60, 260, 80, 260);
            hacermatriz(100, 260, 120, 260);
            hacermatriz(140, 260, 160, 260);
            hacermatriz(220, 260, 240, 260);//////////////////
            hacermatriz(40, 280, 80, 280);
            hacermatriz(140, 280, 160, 280);
            hacermatriz(180, 280, 200, 280);
            hacermatriz(220, 280, 240, 280);////////////////
            hacermatriz(80, 300, 160, 300);
            hacermatriz(180, 300, 220, 300);
            hacermatriz(240, 300, 260, 300);
            hacermatriz(280, 300, 300, 300);//////////////

            hacermatriz(20,40,20,60);
            hacermatriz(20,80,20,160);
            hacermatriz(20,260,20,300);////////////
            hacermatriz(40,0,40,20);
            hacermatriz(40,40,40,60);
            hacermatriz(40,100,40,160);
            hacermatriz(40,200,40,240);
            hacermatriz(40,260,40,300);///////////////
            hacermatriz(60,120,60,140);
            hacermatriz(60,240,60,260);
            hacermatriz(60,280,60,300);//////
            hacermatriz(80,0,80,20);
            hacermatriz(80,120,80,160);
            hacermatriz(80,220,80,240);///////////
            hacermatriz(100,20,100,40);
            hacermatriz(100,100,100,180);
            hacermatriz(100,240,100,300);////////
            hacermatriz(120,20,120,40);
            hacermatriz(120,80,120,120);
            hacermatriz(120,160,120,200);
            hacermatriz(120,300,120,320);////////
            hacermatriz(140,20,140,40);
            hacermatriz(140,120,140,140);
            hacermatriz(140,180,140,220);/////////
            hacermatriz(160,40,160,60);
            hacermatriz(160,80,160,120);
            hacermatriz(160,140,160,160);
            hacermatriz(160,180,160,200);
            hacermatriz(160,220,160,280);///////////
            hacermatriz(180,20,180,40);
            hacermatriz(180,80,180,120);
            hacermatriz(180,160,180,200);
            hacermatriz(180,240,180,260);////////////
            hacermatriz(200,40,200,80);
            hacermatriz(200,100,200,120);
            hacermatriz(200,160,200,220);
            hacermatriz(200,240,200,260);
            hacermatriz(200,280,200,300);/////////////
            hacermatriz(220,0,220,20);
            hacermatriz(220,40,220,80);
            hacermatriz(220,100,220,120);
            hacermatriz(220,160,220,200);
            hacermatriz(220,260,220,280);/////////
            hacermatriz(240,20,240,60);
            hacermatriz(240,100,240,180);
            hacermatriz(240,260,240,280);//////
            hacermatriz(260,60,260,100);
            hacermatriz(260,240,260,300);/////
            hacermatriz(280,160,280,180);
            hacermatriz(280,220,280,240);
            hacermatriz(280,260,280,300);//////
            hacermatriz(300,20,300,40);
            hacermatriz(300,100,300,120);
            hacermatriz(300,160,300,200);
            hacermatriz(300,240,300,300);////
            //busqueda();
            ghj=false;
        }
        
        
    }
    private void dibuja() throws Exception 
    {   	//sutitulle repaint
        SwingUtilities.invokeAndWait(new Runnable() 
        {/*para ejecutar el código de creación de GUI 
        en el thread de despacho de eventos*/
            public void run() 
            {
                paintImmediately(0, 0, ancho, altura);                          //Refresca la pantalla en cada iteración
            }                                                                    //como estamos usando hilos no podemos usar
                                                                                //repaint
         });
    }
   public void retorno() throws Exception
   {
       //long ta = System.nanoTime();
       /*Devuelve el valor actual del contador de tiempo más precisa disponible en el sistema, en nanosegundos.
Este método sólo se puede utilizar para medir el tiempo transcurrido y no está relacionada con ninguna otra noción de sistema o el tiempo del reloj de pared. 
El valor devuelto representa nanosegundos desde un tiempo fijo, pero arbitraria(los valores pueden ser negativos).
Este método proporciona una precisión de nanosegundos, pero no necesariamente una exactitud de nanosegundos. No se garantiza sobre cómo los 
valores cambian con frecuencia.*/
        while (true) 
        {
           // long tn = System.nanoTime();
            //float tempo = (tn - ta) / 1000000000f;
            //ta = tn;
            movimientos();
            dibuja();
        }
    }
   //constructor------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception 
    {                                                                                                                                                      
        JOptionPane.showMessageDialog(null, "Intrucciones:\nHay 2 jugadores (bola verde y bola negra) "
                + "que se mueven con asdw y las flechas respectivamente.\nLas bolas rojas te dan un "
                + "punto\nLas azules te quitan un punto\n"
                + "Las amarillas dan 2 puntos\nLas grises detienen al otro jugador hasta que hagas un punto"
                + "\nLas Cafe alentan al otro jugador hasta que hagas un punto"
                + "\nLas moradas dan mas velocidad"
                + "\nHay tiempo limite");
        tiempo=Integer.parseInt(JOptionPane.showInputDialog("Cuanto tiempo deseas¿"));
        diametro=Integer.parseInt(JOptionPane.showInputDialog("Tamaño de bolas\nRecomendado 10,  max 15"));
        int i, j;
        for (i=-1; ++i<=ancho;)
            for (j=-1; ++j<=altura;)
                matriz[i][j]=0;
               LabyrinthderZeit reproduce = new LabyrinthderZeit();
        reproduce.setVisible(true);
        //-------------------------------jframe--------------------------//
        JFrame jf = new JFrame("Laberinto");
        jf.addWindowListener(new WindowAdapter() 
        {/*permite ajustarnos a los eventos que 
        se produzcan en la ventana. 
        Así, en el constructor de nuestro programa utilizaremos dicho método*/
            public void windowClosing(WindowEvent e)
            {                                                                   /*Aquí realizamos un exit del sistema*/
                JOptionPane.showMessageDialog(null, "Termino el juego");
                System.exit(0);
            }
         });
        jf.setResizable(false);                                             //para que el usuario no pueda modificar la pantalla
        LabyrinthderZeit lab = new LabyrinthderZeit();                                    //estas cosas son triviales
        jf.getContentPane().add(lab);//----
        jf.pack();     //----
        jf.setVisible(true);///---
        lab.retorno();//----- 
    }
}
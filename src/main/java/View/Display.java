package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import model.Vibora;

@SuppressWarnings("serial")
public class Display extends JFrame implements Observer {
  private final int WIDTH = 500;
  private final int HEIGHT = 500;
  private final Dibujo arena;
  private final Controler controler;
  private final Vibora vibora;
  private int posicion_x;
  private int posicion_y;
  public Display() {
    setTitle("Viborita!");
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    vibora = new Vibora();
    controler = new Controler(vibora);
    vibora.attachObserver(this);
    arena = new Dibujo();
    add(arena);
    setVisible(true);
  }
  public void update() {
    posicion_x = vibora.getPosicionX();
    posicion_y = vibora.getPosicionY();
    arena.repaint();
  }
  

  private class Dibujo extends Canvas implements KeyListener {
    public Dibujo() {
      addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
      g.setColor(new Color(255, 0, 0));
      g.fillRect(posicion_x, posicion_y, 10, 10);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    controler.notifyEvent(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
  }

}

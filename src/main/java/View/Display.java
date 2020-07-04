package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import model.Vibora;
/**
 * Crea una ventana, y la pasa un Canvas que tambien es un KeyListener.
 * @author Sebastian
 *
 */
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
    Thread t1 = new Thread(vibora, "Viborita");
    arena = new Dibujo();
    add(arena);
    setVisible(true);
    t1.run();
  }
  /**
   * Updatea el observer, es llamado por los subjects cada vez que hay nueva informacion relevante para este observer.
   */
  public void update() {
    posicion_x = vibora.getPosicionX();
    posicion_y = vibora.getPosicionY();
    arena.repaint();
  }
  /**
   * esta clase se la crea para pasarsela al JFrame, la cual implementa KeyListener para poder escuchar las entradas
   * del teclado del usuario
   * @author Sebastian
   *
   */

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

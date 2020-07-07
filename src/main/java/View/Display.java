package View;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Vibora;
/**
 * Crea una ventana, y la pasa un Canvas que tambien es un KeyListener.
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class Display extends JFrame implements Observer {
  private final int WIDTH;
  private final int HEIGHT;
  private final Dibujo arena;
  private final Controler controler;
  private final Vibora vibora;
  private int vibora_posicion_x;
  private int vibora_posicion_y;
  private int comida_posicion_x;
  private int comida_posicion_y;

  public Display() {
    vibora = new Vibora();
    vibora.attachObserver(this);
    WIDTH = vibora.getWidth();
    HEIGHT= vibora.getHeight();
    setTitle("Viborita!");
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setLocationRelativeTo(null);
    controler = new Controler(vibora);
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
    vibora_posicion_x = vibora.getPosicionX();
    vibora_posicion_y = vibora.getPosicionY();
    comida_posicion_x = vibora.getPosicionComidaX();
    comida_posicion_y = vibora.getPosicionComidaY();
    arena.repaint();
  }
  /**
   * Esta clase se la crea para pasarsela al JFrame, la cual implementa KeyListener para poder escuchar las entradas
   * del teclado del usuario.
   * @author Sebastian
   *
   */

  private class Dibujo extends JPanel implements KeyListener {
    public Dibujo() {
      addKeyListener(this);
      setFocusable(true); //para el JPanel, necesitas esta linea para que funcione el KeyListener
    }

    @Override
    public void paint(Graphics g) {
      super.paintComponent(g);
      g.setColor(new Color(255, 0, 0));
      g.fillRect(vibora_posicion_x, vibora_posicion_y, 10, 10);
      g.fillRect(comida_posicion_x, comida_posicion_y, 10, 10);
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

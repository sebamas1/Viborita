package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Vibora;

/**
 * Crea una ventana, y la pasa un Canvas que tambien es un KeyListener.
 * 
 * @author Sebastian
 *
 */
@SuppressWarnings("serial")
public class Display extends JFrame implements Observer {
  private final int WIDTH;
  private final int HEIGHT;
  private final int DIMENSION;
  private final int MARGEN_WIDTH = 30;
  private final int MARGEN_HEIGHT = 50;
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
    controler = new Controler(vibora);
    WIDTH = vibora.getWidth();
    HEIGHT = vibora.getHeight();
    DIMENSION = vibora.getDesplazamiento();
    setTitle("Viborita!");
    setSize(WIDTH + MARGEN_WIDTH, HEIGHT + MARGEN_HEIGHT); //estaria bueno poder borrar estos margenes, pero eso es un problema para el futuro
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setLocationRelativeTo(null);
    arena = new Dibujo();
    add(arena);
    Thread t1 = new Thread(vibora, "Viborita");
    setVisible(true);
    t1.run();
  }

  /**
   * Updatea el observer, es llamado por los subjects cada vez que hay nueva
   * informacion relevante para este observer.
   */
  public void update() {
    vibora_posicion_x = vibora.getPosicionX();
    vibora_posicion_y = vibora.getPosicionY();
    comida_posicion_x = vibora.getPosicionComidaX();
    comida_posicion_y = vibora.getPosicionComidaY();
    arena.repaint();
  }

  /**
   * Esta clase se la crea para pasarsela al JFrame, la cual implementa
   * KeyListener para poder escuchar las entradas del teclado del usuario.
   * 
   * @author Sebastian
   *
   */

  private class Dibujo extends JPanel implements KeyListener {
    public Dibujo() {
      setBorder(BorderFactory.createLineBorder(Color.black));
      addKeyListener(this);
      setFocusable(true); // para el JPanel, necesitas esta linea para que funcione el KeyListener
    }

    @Override
    public void paint(Graphics g) {
      super.paintComponent(g);
      g.setColor(new Color(255, 0, 0));
      g.fillRect(vibora_posicion_x, vibora_posicion_y, DIMENSION, DIMENSION);
      g.fillRect(comida_posicion_x, comida_posicion_y, DIMENSION, DIMENSION);
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

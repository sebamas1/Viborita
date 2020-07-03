package View;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {
  private final int WIDTH = 500;
  private final int HEIGHT = 500;
  private final int FLECHA_ARRIBA = KeyEvent.VK_UP;
  private final int FLECHA_IZQUIERDA = KeyEvent.VK_LEFT;
  private final int FLECHA_DERECHA = KeyEvent.VK_RIGHT;
  private final int FLECHA_ABAJO = KeyEvent.VK_DOWN;

  public Display() {
    setTitle("Viborita!");
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    add(new Dibujo());
    setVisible(true);
  }

  private class Dibujo extends Canvas implements KeyListener {
    public Dibujo() {
      addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
      g.setColor(new Color(255, 0, 0));
      g.fillRect(100, 100, 10, 10);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
      case FLECHA_ARRIBA:
        System.out.println("ARRIBA");
        break;
      case FLECHA_ABAJO:
        System.out.println("ABAJO");
        break;
      case FLECHA_IZQUIERDA:
        System.out.println("IZQUIERDA");
        break;
      case FLECHA_DERECHA:
        System.out.println("DERECHA");
        break;
      default:
      }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
  }

}

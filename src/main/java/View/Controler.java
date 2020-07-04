package view;

import java.awt.event.KeyEvent;

import model.Vibora;
/**
 * Parte del MVC implementado, recibe los KeyEvent del display, y sabe que significan para el model.
 * @author Sebastian
 *
 */
public class Controler {
  private final int FLECHA_ARRIBA = KeyEvent.VK_UP;
  private final int FLECHA_IZQUIERDA = KeyEvent.VK_LEFT;
  private final int FLECHA_DERECHA = KeyEvent.VK_RIGHT;
  private final int FLECHA_ABAJO = KeyEvent.VK_DOWN;
  private final Vibora vibora;

  public Controler(Vibora vibora) {
    this.vibora = vibora;
  }
  /**
   * 
   * @param e evento del teclado.
   */
  public void notifyEvent(KeyEvent e) {
    switch (e.getKeyCode()) {
    case FLECHA_ARRIBA:
      vibora.setMovimiento(0);
      break;
    case FLECHA_ABAJO:
      vibora.setMovimiento(1);
      break;
    case FLECHA_IZQUIERDA:
      vibora.setMovimiento(2);
      break;
    case FLECHA_DERECHA:
      vibora.setMovimiento(3);
      break;
    default:
    }
  }
}

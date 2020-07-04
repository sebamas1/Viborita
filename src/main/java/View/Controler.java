package view;

import java.awt.event.KeyEvent;

import model.Vibora;

public class Controler {
  private final int FLECHA_ARRIBA = KeyEvent.VK_UP;
  private final int FLECHA_IZQUIERDA = KeyEvent.VK_LEFT;
  private final int FLECHA_DERECHA = KeyEvent.VK_RIGHT;
  private final int FLECHA_ABAJO = KeyEvent.VK_DOWN;
  private final Vibora vibora;

  public Controler(Vibora vibora) {
    this.vibora = vibora;
  }
  public void notifyEvent(KeyEvent e) {
    switch (e.getKeyCode()) {
    case FLECHA_ARRIBA:
      vibora.posicion_y--;
      vibora.notifyObservers();
      break;
    case FLECHA_ABAJO:
      vibora.posicion_y++;
      vibora.notifyObservers();
      break;
    case FLECHA_IZQUIERDA:
      vibora.posicion_x--;
      vibora.notifyObservers();
      break;
    case FLECHA_DERECHA:
      vibora.posicion_x++;
      vibora.notifyObservers();
      break;
    default:
    }
  }
}

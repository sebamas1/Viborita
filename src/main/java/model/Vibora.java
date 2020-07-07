package model;

import java.util.HashSet;
import View.Observer;

public class Vibora implements Subject, Runnable {
  private int vibora_posicion_x = 0;
  private int vibora_posicion_y = 0;
  private int comida_posicion_x;
  private int comida_posicion_y;
  private int movimiento = 3;
  private boolean cambioMovimiento = false;
  private final HashSet<Observer> observers;
  private final int VELOCIDAD = 10;
  private final int WIDTH = 500;
  private final int HEIGHT = 500;
  private final int SLEEP = 100;
  /*
   * por el momento, el tema de pasar la viborita por la comida queda bien, porque
   * las dimensiones del cuadrado de la viborita coincide con la velocidad de desplazamiento
   */
  
  
  public Vibora() {
    observers = new HashSet<Observer>();
  }

  public int getPosicionX() {
    return vibora_posicion_x;
  }

  public int getPosicionY() {
    return vibora_posicion_y;
  }

  @Override
  public void attachObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void detachObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }

  }
/**
 * Este metodo se corre como un thread separado: provoca que la viborita esta en constante movimiento hasta que un thread
 * cambia el movimiento de la viborita. En ese momento se actualiza el movimiento de la misma.
 */
  public void run() {
   generarComida();
    while(true) {
     setCambioFalse();
     switch(movimiento) {
     case 0:
       while(!getCambio()) {
        vibora_posicion_y = (vibora_posicion_y - VELOCIDAD) < 0 ?  HEIGHT : vibora_posicion_y - VELOCIDAD;
        notifyObservers();
         try {
           Thread.sleep(SLEEP);
         } catch(InterruptedException e) {}
       }
       break;
     case 1:
       while(!getCambio()) {
         vibora_posicion_y = (vibora_posicion_y + VELOCIDAD) > HEIGHT ?  0 : vibora_posicion_y + VELOCIDAD;
         notifyObservers();
         try {
           Thread.sleep(SLEEP);
         } catch(InterruptedException e) {}
       }
       break;
     case 2:
       while(!getCambio()) {
         vibora_posicion_x = (vibora_posicion_x - VELOCIDAD) < 0 ? WIDTH : vibora_posicion_x - VELOCIDAD;
         notifyObservers();
         try {
           Thread.sleep(SLEEP);
         } catch(InterruptedException e) {}
       }
       break;
     case 3:
       while(!getCambio()) {
         vibora_posicion_x = (vibora_posicion_x + VELOCIDAD) > WIDTH ?  0 : vibora_posicion_x + VELOCIDAD;
         notifyObservers();
         try {
           Thread.sleep(SLEEP);
         } catch(InterruptedException e) {}
       }
       break;
       default:
     }
   }
  }
  
  private synchronized boolean getCambio() {
    return cambioMovimiento;
  }
  private synchronized void setCambioFalse() {
    cambioMovimiento = false;
  }
  /**
   * Setea el movimiento siempre y cuando el movimiento sea valido para el modelo.
   * @param movimiento
   */
  public synchronized void setMovimiento(int movimiento) {
    if(!(this.movimiento == movimiento)) {
      if(!(this.movimiento == 0 && movimiento == 1) && !(this.movimiento == 1 && movimiento == 0)) {
      if (!(this.movimiento == 2 && movimiento == 3) && !(this.movimiento == 3 && movimiento == 2)) {
        this.movimiento = movimiento;
        cambioMovimiento = true;
      } else return;
      } else return;
    } else return;
  }
  public int getWidth() {
    return WIDTH;
  }
  public int getHeight() {
    return HEIGHT;
  }
  public int getPosicionComidaX() {
    return comida_posicion_x;
  }
  public int getPosicionComidaY() {
    return comida_posicion_y;
  }
  //para que quede mas limpio, se van anecesitar posiciones multiplos de la velocidad
  private void generarComida() {
   do {
     comida_posicion_x = (int) (Math.random() * (WIDTH-50)); //estos valores es para que no se salgan del margen
   } while (!(comida_posicion_x % VELOCIDAD == 0));
   do {
     comida_posicion_y = (int) (Math.random() * (HEIGHT-50));//hardodeadisimo, busca una solucion
   } while(!(comida_posicion_y % VELOCIDAD == 0));
  }
}

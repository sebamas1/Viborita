package model;

import java.util.HashSet;
import View.Observer;

public class Vibora implements Subject, Runnable {
  public int posicion_x = 10;
  public int posicion_y = 50;
  private int velocidad_x = 1;
  private int velocidad_y = 1;
  private HashSet<Observer> observers;
  private int movimiento = 0;
  private boolean cambioMovimiento = false;

  public Vibora() {
    observers = new HashSet<Observer>();
  }

  public int getPosicionX() {
    return posicion_x;
  }

  public int getPosicionY() {
    return posicion_y;
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
   while(true) {
     setCambioFalse();
     switch(movimiento) {
     case 0:
       while(!getCambio()) {
         posicion_y -= velocidad_y;
         notifyObservers();
         try {
           Thread.sleep(10);
         } catch(InterruptedException e) {}
       }
       break;
     case 1:
       while(!getCambio()) {
         posicion_y += velocidad_y;
         notifyObservers();
         try {
           Thread.sleep(10);
         } catch(InterruptedException e) {}
       }
       break;
     case 2:
       while(!getCambio()) {
         posicion_x -= velocidad_x;
         notifyObservers();
         try {
           Thread.sleep(10);
         } catch(InterruptedException e) {}
       }
       break;
     case 3:
       while(!getCambio()) {
         posicion_x += velocidad_x;
         notifyObservers();
         try {
           Thread.sleep(10);
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
}

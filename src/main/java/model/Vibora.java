package model;

import java.util.HashSet;
import View.Observer;

public class Vibora implements Subject, Runnable {
  private int vibora_posicion_x = 0;
  private int vibora_posicion_y = 0;
  private int comida_posicion_x;
  private int comida_posicion_y;
  private int comida_posicion_anterior_x;
  private int comida_posicion_anterior_y;
  private int movimiento = 3;
  private boolean cambioMovimiento = false;
  private final HashSet<Observer> observers;
  private final int DESPLAZAMIENTO = 10;
  private final int WIDTH = 500;
  private final int HEIGHT = 500;
  private final int SLEEP = 100;
  
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
        vibora_posicion_y = (vibora_posicion_y - DESPLAZAMIENTO) < 0 ?  HEIGHT : vibora_posicion_y - DESPLAZAMIENTO;
        comer();
        notifyObservers();
         try {
           Thread.sleep(SLEEP);
         } catch(InterruptedException e) {}
       }
       break;
     case 1:
       while(!getCambio()) {
         vibora_posicion_y = (vibora_posicion_y + DESPLAZAMIENTO) > HEIGHT ?  0 : vibora_posicion_y + DESPLAZAMIENTO;
         comer();
         notifyObservers();
         try {
           Thread.sleep(SLEEP);
         } catch(InterruptedException e) {}
       }
       break;
     case 2:
       while(!getCambio()) {
         vibora_posicion_x = (vibora_posicion_x - DESPLAZAMIENTO) < 0 ? WIDTH : vibora_posicion_x - DESPLAZAMIENTO;
         comer();
         notifyObservers();
         try {
           Thread.sleep(SLEEP);
         } catch(InterruptedException e) {}
       }
       break;
     case 3:
       while(!getCambio()) {
         vibora_posicion_x = (vibora_posicion_x + DESPLAZAMIENTO) > WIDTH ?  0 : vibora_posicion_x + DESPLAZAMIENTO;
         comer();
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
  public int getDesplazamiento() {
    return DESPLAZAMIENTO;
  }
  private void generarComida() {
   do {
     comida_posicion_x = (int) (Math.random() * (WIDTH));
   } while (!(comida_posicion_x % DESPLAZAMIENTO == 0));
   do {
     comida_posicion_y = (int) (Math.random() * (HEIGHT));
   } while(!(comida_posicion_y % DESPLAZAMIENTO == 0));
  }
  private void comer() {
    if((vibora_posicion_x == comida_posicion_x) && (vibora_posicion_y == comida_posicion_y)) {
      comida_posicion_anterior_x = comida_posicion_x;
      comida_posicion_anterior_y = comida_posicion_y;
      do {
        generarComida();
      }while((comida_posicion_x == comida_posicion_anterior_x) || (comida_posicion_y == comida_posicion_anterior_y));
    }
  }
  private boolean posicionComidaValida() {
    return false;
  }
}

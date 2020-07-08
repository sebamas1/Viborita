package model;

import java.util.ArrayList;
import java.util.HashSet;
import View.Observer;

public class Vibora implements Subject, Runnable {
  private int viboraPosicionX;
  private int viboraPosicionY;
  private int comidaPosicionX;
  private int comidaPosicionY;
  private int comidaPosicionAnteriorX;
  private int comidaPosicionAnteriorY;
  private int movimiento = 3;
  private boolean cambioMovimiento = false;
  private final int VIBORA_POSICION_INICIAL_X = 0;
  private final int VIBORA_POSICION_INICIAL_Y = 0;
  private final HashSet<Observer> observers;
  private final int DESPLAZAMIENTO = 10;
  private final int WIDTH = 500;
  private final int HEIGHT = 500;
  private final int SLEEP = 100;
  private final ArrayList<Posicion> cuerpoVibora;

  public Vibora() {
    viboraPosicionX = VIBORA_POSICION_INICIAL_X;
    viboraPosicionY = VIBORA_POSICION_INICIAL_Y;
    observers = new HashSet<Observer>();
    cuerpoVibora = new ArrayList<Posicion>();
  }

  public int getPosicionX() {
    return viboraPosicionX;
  }

  public int getPosicionY() {
    return viboraPosicionY;
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
   * Este metodo se corre como un thread separado: provoca que la viborita esta en
   * constante movimiento hasta que un thread cambia el movimiento de la viborita.
   * En ese momento se actualiza el movimiento de la misma.
   */
  public void run() {
    generarComida();
    while (true) {
      setCambioFalse();
      switch (movimiento) {
      case 0:
        while (!getCambio()) {
          int y = viboraPosicionY;
          viboraPosicionY = (viboraPosicionY - DESPLAZAMIENTO) < 0 ? HEIGHT : viboraPosicionY - DESPLAZAMIENTO;
          comer(viboraPosicionX, y);
          desplazarCuerpo(viboraPosicionX, y);
          notifyObservers();
          try {
            Thread.sleep(SLEEP);
          } catch (InterruptedException e) {
          }
        }
        break;
      case 1:
        while (!getCambio()) {
          int y = viboraPosicionY;
          viboraPosicionY = (viboraPosicionY + DESPLAZAMIENTO) > HEIGHT ? 0 : viboraPosicionY + DESPLAZAMIENTO;
          comer(viboraPosicionX, y);
          desplazarCuerpo(viboraPosicionX, y);
          notifyObservers();
          try {
            Thread.sleep(SLEEP);
          } catch (InterruptedException e) {
          }
        }
        break;
      case 2:
        while (!getCambio()) {
          int x = viboraPosicionX;
          viboraPosicionX = (viboraPosicionX - DESPLAZAMIENTO) < 0 ? WIDTH : viboraPosicionX - DESPLAZAMIENTO;
          comer(x, viboraPosicionY);
          desplazarCuerpo(x, viboraPosicionY);
          notifyObservers();
          try {
            Thread.sleep(SLEEP);
          } catch (InterruptedException e) {
          }
        }
        break;
      case 3:
        while (!getCambio()) {
          int x = viboraPosicionX;
          viboraPosicionX = (viboraPosicionX + DESPLAZAMIENTO) > WIDTH ? 0 : viboraPosicionX + DESPLAZAMIENTO;
          comer(x, viboraPosicionY);
          desplazarCuerpo(x, viboraPosicionY);
          notifyObservers();
          try {
            Thread.sleep(SLEEP);
          } catch (InterruptedException e) {
          }
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
   * 
   * @param movimiento
   */
  public synchronized void setMovimiento(int movimiento) {
    if (!(this.movimiento == movimiento)) {
      if (!(this.movimiento == 0 && movimiento == 1) && !(this.movimiento == 1 && movimiento == 0)) {
        if (!(this.movimiento == 2 && movimiento == 3) && !(this.movimiento == 3 && movimiento == 2)) {
          this.movimiento = movimiento;
          cambioMovimiento = true;
        } else
          return;
      } else
        return;
    } else
      return;
  }

  public int getWidth() {
    return WIDTH;
  }

  public int getHeight() {
    return HEIGHT;
  }

  public int getPosicionComidaX() {
    return comidaPosicionX;
  }

  public int getPosicionComidaY() {
    return comidaPosicionY;
  }

  public int getDesplazamiento() {
    return DESPLAZAMIENTO;
  }
  public ArrayList<Posicion> getCuerpoVibora() {
    return cuerpoVibora;
  }

  private void generarComida() {
    do {
      comidaPosicionX = (int) (Math.random() * (WIDTH));
    } while (!((comidaPosicionX - VIBORA_POSICION_INICIAL_X) % DESPLAZAMIENTO == 0));
    do {
      comidaPosicionY = (int) (Math.random() * (HEIGHT));
    } while (!((comidaPosicionY - VIBORA_POSICION_INICIAL_Y) % DESPLAZAMIENTO == 0));
  }

  private void comer(int x, int y) {
    if ((viboraPosicionX == comidaPosicionX) && (viboraPosicionY == comidaPosicionY)) {
      comidaPosicionAnteriorX = comidaPosicionX;
      comidaPosicionAnteriorY = comidaPosicionY;
      crecer(x, y);
      do {
        generarComida();
      } while (posicionComidaValida()); //hay que completar la funcion de validez
    }
  }

  private boolean posicionComidaValida() {
    if ((comidaPosicionX == comidaPosicionAnteriorX) || (comidaPosicionY == comidaPosicionAnteriorY))
      return false;
    return true;
  }
  private void crecer(int x, int y) {
    if (cuerpoVibora.size() == 0) {
      cuerpoVibora.add(new Posicion(x, y));
    } else {
      cuerpoVibora.add(cuerpoVibora.get(cuerpoVibora.size() - 1).setSiguienteNodo());
    }
  }
  private void desplazarCuerpo(int nuevaX, int nuevaY) {
    if (cuerpoVibora.size() == 0) {
      return;
    } else {
      cuerpoVibora.get(0).actualizarPosicion(nuevaX, nuevaY);
    }
  }
}

package model;

import java.util.HashSet;
import view.Observer;

public class Vibora implements Subject {
  public int posicion_x = 10;
  public int posicion_y = 50;
  private int velocidad_x = 50;
  private int velocidad_y = 0;
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
 //fijate si haces algo con este metodo para hacerlo o no privado
  @Override
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }

  }


  
}

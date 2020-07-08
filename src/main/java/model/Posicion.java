package model;

public class Posicion {
  private int x;
  private int y;
  private int viejaX;
  private int viejaY;
  private Posicion nodo;
  protected Posicion(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
  protected void setX(int x) {
    this.x = x;
  }
  protected void setY(int y) {
    this.y = y;
  }
  protected Posicion setSiguienteNodo() {
    Posicion aux = new Posicion(viejaX, viejaY);
    this.nodo = aux;
    return aux;
  }
  protected void cortar() {
    nodo = null;
  }
  protected void actualizarPosicion(int x, int y) {
    if(nodo == null) {
      viejaX = this.x;
      viejaY = this.y;
      this.x = x;
      this.y = y;
    } else {
      viejaX = this.x;
      viejaY = this.y;
      this.x = x;
      this.y = y;
      nodo.actualizarPosicion(viejaX, viejaY);
    }
  }
}

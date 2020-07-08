import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Vibora;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViboraTest {

  /**
   * unitest para probar que el seteo de movimiento trabaje en logica como debe
   * ser cambiando el campo de movimiento como debe ser y cuando corresponde
   */
  @Test
  void test() {
    setMovimientoTest();
    generarComidaTest();
  }

  /**
   * Se fija que el movimiento que le metes al metodo, sea el que finalmente tiene
   * la viborita.
   */
  private void setMovimientoTest() {
    Vibora viborita = new Vibora();
    Class<?> refleccion = viborita.getClass();
    Class[] parameterTypes = new Class[] { int.class }; // esta linea es super interesante, creas un array con los tipos
                                                        // de param a pasrle al getMethod

    try {
      Method metodo = refleccion.getMethod("setMovimiento", parameterTypes);
      metodo.setAccessible(true);
      Field campo = refleccion.getDeclaredField("movimiento");
      campo.setAccessible(true);
      metodo.invoke(viborita, 0);
      assertEquals(campo.get(viborita), Integer.valueOf(0));
      metodo.invoke(viborita, 2);
      assertEquals(campo.get(viborita), Integer.valueOf(2));
      metodo.invoke(viborita, 1);
      assertEquals(campo.get(viborita), Integer.valueOf(1));
      metodo.invoke(viborita, 3);
      assertEquals(campo.get(viborita), Integer.valueOf(3));

      // esto siguiente es porque si estoy en 0,(arriba) no puedo ir hacia 1(abajo)
      metodo.invoke(viborita, 0);
      metodo.invoke(viborita, 1);
      assertFalse(campo.get(viborita).equals(Integer.valueOf(1)));
      assertEquals(campo.get(viborita), Integer.valueOf(0));

      metodo.invoke(viborita, 3);
      metodo.invoke(viborita, 1);
      metodo.invoke(viborita, 0);
      assertFalse(campo.get(viborita).equals(Integer.valueOf(0)));
      assertEquals(campo.get(viborita), Integer.valueOf(1));

      metodo.invoke(viborita, 2);
      metodo.invoke(viborita, 3);
      assertFalse(campo.get(viborita).equals(Integer.valueOf(3)));
      assertEquals(campo.get(viborita), Integer.valueOf(2));

      metodo.invoke(viborita, 1);
      metodo.invoke(viborita, 3);
      metodo.invoke(viborita, 2);
      assertFalse(campo.get(viborita).equals(Integer.valueOf(2)));
      assertEquals(campo.get(viborita), Integer.valueOf(3));

    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException | SecurityException e) {
      e.printStackTrace();
    }
  }

  /**
   * Se fija que la comida que se genera, este siempre en una posicion tal que la
   * viborita se pueda encasillar en esa posicion.
   */
  private void generarComidaTest() {
    Vibora viborita = new Vibora();
    Class<?> refleccion = viborita.getClass();
    try {
      for (int i = 0; i < 10000; i++) {
        Method generarComida = refleccion.getDeclaredMethod("generarComida");
        generarComida.setAccessible(true);
        generarComida.invoke(viborita);
        Field comida_posicion_x = refleccion.getDeclaredField("comidaPosicionX");
        Field comida_posicion_y = refleccion.getDeclaredField("comidaPosicionY");
        Field DESPLAZAMIENTO = refleccion.getDeclaredField("DESPLAZAMIENTO");
        Field inicialX = refleccion.getDeclaredField("VIBORA_POSICION_INICIAL_X");
        Field inicialY = refleccion.getDeclaredField("VIBORA_POSICION_INICIAL_Y");
        inicialX.setAccessible(true);
        inicialY.setAccessible(true);
        comida_posicion_x.setAccessible(true);
        comida_posicion_y.setAccessible(true);
        DESPLAZAMIENTO.setAccessible(true);
        assertTrue(
            ((comida_posicion_x.getInt(viborita) - inicialX.getInt(viborita)) % DESPLAZAMIENTO.getInt(viborita)) == 0);
        assertTrue(
            ((comida_posicion_y.getInt(viborita) - inicialY.getInt(viborita)) % DESPLAZAMIENTO.getInt(viborita)) == 0);
      }
    } catch (NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

}

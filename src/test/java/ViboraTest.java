import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Vibora;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
class ViboraTest {

  /**
   * unitest para probar que el seteo de movimiento trabaje en logica como debe ser
   * cambiando el campo de movimiento como debe ser y cuando corresponde
   */
  @Test
  void test() {
    reflect();
  }
  
  private void reflect() {
    Vibora viborita = new Vibora();
    Class<?> refleccion = viborita.getClass();
    Class[] parameterTypes = new Class[] {int.class}; //esta linea es super interesante, creas un array con los tipos de param a pasrle al getMethod
    
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
      
      //esto siguiente es porque si estoy en 0,(arriba) no puedo ir hacia 1(abajo)
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
    } catch(NoSuchMethodException e) {
      e.printStackTrace();
    } catch(NoSuchFieldException | SecurityException e) {
      e.printStackTrace();
    }
  }

}

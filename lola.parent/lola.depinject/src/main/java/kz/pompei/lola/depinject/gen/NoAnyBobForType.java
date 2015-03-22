package kz.pompei.lola.depinject.gen;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;

public class NoAnyBobForType extends RuntimeException {
  
  public final Type type;
  public final AccessibleObject accessibleObject;
  
  public NoAnyBobForType(Type type, AccessibleObject accessibleObject) {
    this.type = type;
    this.accessibleObject = accessibleObject;
    
  }
  
}

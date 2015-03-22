package kz.pompei.lola.depinject.gen;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;
import java.util.List;

public class TooManyBobsForType extends RuntimeException {
  
  public final Type type;
  public final List<String> foundBobs;
  public final AccessibleObject accessibleObject;
  
  public TooManyBobsForType(Type type, List<String> foundIds, AccessibleObject accessibleObject) {
    this.type = type;
    this.foundBobs = foundIds;
    this.accessibleObject = accessibleObject;
  }
  
}

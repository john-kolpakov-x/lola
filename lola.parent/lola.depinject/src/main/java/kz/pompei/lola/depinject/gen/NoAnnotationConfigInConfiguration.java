package kz.pompei.lola.depinject.gen;

public class NoAnnotationConfigInConfiguration extends RuntimeException {
  
  public final Class<?> configClass;
  
  public NoAnnotationConfigInConfiguration(Class<?> configClass) {
    this.configClass = configClass;
  }
  
}

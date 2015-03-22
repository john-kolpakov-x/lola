package kz.pompei.lola.depinject.gen;

public class BobIdAlreadyExists extends RuntimeException {
  
  public final String id;
  public final Class<?> cl;
  
  public BobIdAlreadyExists(String id, Class<?> cl) {
    this.id = id;
    this.cl = cl;
  }
  
}

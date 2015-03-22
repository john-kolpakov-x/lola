package kz.pompei.lola.depinject.gen;

import kz.pompei.lola.depinject.src.DepinjectContext;

public class ContextMustBeInterface extends RuntimeException {
  
  public final Class<? extends DepinjectContext> iface;
  
  public ContextMustBeInterface(Class<? extends DepinjectContext> iface) {
    this.iface = iface;
  }
  
}

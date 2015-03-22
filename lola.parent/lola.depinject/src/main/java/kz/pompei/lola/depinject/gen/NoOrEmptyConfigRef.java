package kz.pompei.lola.depinject.gen;

import kz.pompei.lola.depinject.src.DepinjectContext;

public class NoOrEmptyConfigRef extends RuntimeException {
  public final Class<? extends DepinjectContext> errorClass;
  
  public NoOrEmptyConfigRef(Class<? extends DepinjectContext> errorClass) {
    this.errorClass = errorClass;
  }
}

package kz.pompei.lola.depinject.gen.example;

import kz.pompei.lola.depinject.gen.example.beans_01.Config01;
import kz.pompei.lola.depinject.gen.example.beans_02.Config02;
import kz.pompei.lola.depinject.src.Autoget;
import kz.pompei.lola.depinject.src.ConfigRef;
import kz.pompei.lola.depinject.src.DepinjectContext;

@ConfigRef({ Config01.class, Config02.class })
public interface AsdTestContext extends DepinjectContext {
  
  ServiceThree serviceThree();
  
  Autoget<ServiceThree> serviceThree1();
  
}

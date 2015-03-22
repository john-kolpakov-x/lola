package kz.pompei.lola.depinject.gen.example.beans_02.bobs.here;

import kz.pompei.lola.depinject.gen.example.ServiceThree;
import kz.pompei.lola.depinject.gen.example.beans_01.IfaceOne;
import kz.pompei.lola.depinject.src.Autoget;
import kz.pompei.lola.depinject.src.Bob;

@Bob
public class Bob02_THREE implements ServiceThree {
  
  public Autoget<IfaceOne> one1111;
  
  @Override
  public String helloFromIfaceOne() {
    return one1111.get().hello();
  }
  
}

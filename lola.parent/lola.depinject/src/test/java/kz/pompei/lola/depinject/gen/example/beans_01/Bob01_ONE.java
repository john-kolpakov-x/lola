package kz.pompei.lola.depinject.gen.example.beans_01;

import kz.pompei.lola.depinject.src.Bob;

@Bob("one")
public class Bob01_ONE implements IfaceOne {
  @Override
  public String hello() {
    return "Hello from Bob01 ONE";
  }
}

package kz.pompei.lola.stand.src;

import kz.pompei.lola.client.src.ShowHelloWorld;
import kz.pompei.lola.share.src.Hello;

import com.google.gwt.core.client.EntryPoint;

public class MainStandEP implements EntryPoint {
  
  @Override
  public void onModuleLoad() {
    ShowHelloWorld.showHelloWorld(Hello.MESSAGE);
  }
}

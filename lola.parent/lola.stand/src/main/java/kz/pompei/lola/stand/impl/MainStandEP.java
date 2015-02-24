package kz.pompei.lola.stand.impl;

import kz.pompei.lola.client.impl.ShowHelloWorld;

import com.google.gwt.core.client.EntryPoint;

public class MainStandEP implements EntryPoint {
  
  @Override
  public void onModuleLoad() {
    ShowHelloWorld.showHelloWorld();
  }
}

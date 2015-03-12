package kz.pompei.lola.stand.src;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class MainStandEP implements EntryPoint {
  
  @Override
  public void onModuleLoad() {
    AsdIface o = GWT.create(AsdIface.class);
    o.asd();
    
    Window.alert("o = " + o);
  }
}

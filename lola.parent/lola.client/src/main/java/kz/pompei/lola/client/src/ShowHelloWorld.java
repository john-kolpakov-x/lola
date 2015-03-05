package kz.pompei.lola.client.src;

import com.google.gwt.user.client.Window;

public class ShowHelloWorld {
  public static void showHelloWorld(String message) {
    Window.alert("Hello world from CLIENT: " + message);
  }
}

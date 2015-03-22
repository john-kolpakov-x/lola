package kz.pompei.lola.depinject.gen;

import java.io.PrintStream;

public class StrPrintStream implements StrPrint {
  private PrintStream target;
  
  public StrPrintStream(PrintStream target) {
    this.target = target;
  }
  
  @Override
  public void print(String s) {
    target.print(s);
  }
  
  @Override
  public void println(String s) {
    target.println(s);
  }
  
  @Override
  public void println() {
    target.println();
  }
  
}

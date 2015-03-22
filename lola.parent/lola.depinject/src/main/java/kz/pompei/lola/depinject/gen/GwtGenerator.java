package kz.pompei.lola.depinject.gen;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class GwtGenerator extends Generator {
  
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName)
      throws UnableToCompleteException {
    try {
      return generateInner(logger, context, typeName);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private String generateInner(TreeLogger logger, GeneratorContext context, String typeName)
      throws Exception {
    
    JClassType returnType = context.getTypeOracle().getType(typeName);
    System.out.println("logger = " + logger);
    System.out.println("context = " + context);
    System.out.println("typeName = " + typeName);
    System.out.println("returnType = " + returnType);
    
    String packageName = returnType.getPackage().getName();
    String simpleName = returnType.getSimpleSourceName() + "Generated";
    
    ClassSourceFileComposerFactory comp = new ClassSourceFileComposerFactory(packageName,
        simpleName);
    
    comp.addImplementedInterface("kz.pompei.lola.stand.src.AsdIface");
    comp.addImport(com.google.gwt.user.client.Window.class.getName());
    
    PrintWriter pr = context.tryCreate(logger, packageName, simpleName);
    if (pr == null) throw new NullPointerException();
    
    SourceWriter src = comp.createSourceWriter(context, pr);
    
    src.println("@Override public void asd() {");
    //com.google.gwt.user.client.Window
    src.println("Window.alert(\"Hello from generated class\");");
    src.println("}");
    
    src.commit(logger);
    
    return packageName + '.' + simpleName;
  }
  
}

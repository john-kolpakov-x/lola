package kz.pompei.lola.depinject.gen;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map.Entry;

import kz.pompei.lola.depinject.gen.example.AsdTestContext;
import kz.pompei.lola.depinject.gen.example.beans_01.Bob01_ONE;
import kz.pompei.lola.depinject.gen.example.beans_01.Bob01_TWO;
import kz.pompei.lola.depinject.gen.example.beans_02.bobs.here.Bob02_THREE;
import kz.pompei.lola.depinject.gen.example.beans_02.bobs.here.sub.Bob02_FOUR;
import kz.pompei.lola.depinject.src.ConfigRef;
import kz.pompei.lola.depinject.src.DepinjectContext;

import org.fest.assertions.MapAssert;
import org.testng.annotations.Test;

public class ModelLoaderTest {
  
  private static class NotInterface implements DepinjectContext {}
  
  private static interface NoConfigRef extends DepinjectContext {}
  
  @ConfigRef({})
  private static interface EmptyConfigRef extends DepinjectContext {}
  
  @Test(expectedExceptions = ContextMustBeInterface.class)
  public void contextIsNotInterface() throws Exception {
    new ModelLoader().setContextIface(NotInterface.class);
  }
  
  @Test(expectedExceptions = NoOrEmptyConfigRef.class)
  public void noConfigRef() throws Exception {
    new ModelLoader().setContextIface(NoConfigRef.class);
  }
  
  @Test(expectedExceptions = NoOrEmptyConfigRef.class)
  public void emptyConfigRef() throws Exception {
    new ModelLoader().setContextIface(EmptyConfigRef.class);
  }
  
  @Test
  public void testBobClasses() throws Exception {
    ModelLoader loader = new ModelLoader();
    loader.setContextIface(AsdTestContext.class);
    
    assertThat(loader.bobClasses).contains(Bob01_ONE.class);
    assertThat(loader.bobClasses).contains(Bob01_TWO.class);
    assertThat(loader.bobClasses).contains(Bob02_THREE.class);
    assertThat(loader.bobClasses).contains(Bob02_FOUR.class);
  }
  
  @Test
  public void testInitIdClassMap() throws Exception {
    ModelLoader loader = new ModelLoader();
    loader.setContextIface(AsdTestContext.class);
    
    loader.initIdClassMap();
    
    for (Entry<String, Class<?>> e : loader.idClassMap.entrySet()) {
      System.out.println(e.getKey() + " --> " + e.getValue().getName());
    }
    
    assertThat(loader.idClassMap).includes(MapAssert.entry("Bob01_TWO", Bob01_TWO.class));
    assertThat(loader.idClassMap).includes(MapAssert.entry("Bob02_THREE", Bob02_THREE.class));
    assertThat(loader.idClassMap).includes(MapAssert.entry("one", Bob01_ONE.class));
    assertThat(loader.idClassMap).includes(MapAssert.entry("Bob02_FOUR", Bob02_FOUR.class));
    
    assertThat(loader.idClassMap.keySet()).contains("AsdBob", "AsdBob_1");
  }
  
  @Test
  public void testInitGetterIdMap() throws Exception {
    
    ModelLoader loader = new ModelLoader();
    loader.setContextIface(AsdTestContext.class);
    
    loader.initIdClassMap();
    loader.initGetterIdMap();
    
    for (Entry<String, String> e : loader.idGetterMap.entrySet()) {
      System.out.println(e.getKey() + " -> " + e.getValue());
    }
    assertThat(1);
  }
  
  @Test
  public void printClassContent() throws Exception {
    ModelLoader loader = new ModelLoader();
    loader.setContextIface(AsdTestContext.class);
    loader.initIdClassMap();
    loader.initGetterIdMap();
    
    System.out.println("-----------------------------------------------");
    loader.printClassContent(new StrPrintStream(System.out));
    System.out.println("-----------------------------------------------");
  }
}

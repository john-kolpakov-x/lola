package kz.pompei.lola.depinject.gen;

import java.io.File;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kz.pompei.lola.depinject.src.Autoget;
import kz.pompei.lola.depinject.src.Bob;
import kz.pompei.lola.depinject.src.Config;
import kz.pompei.lola.depinject.src.ConfigRef;
import kz.pompei.lola.depinject.src.DepinjectContext;

public class ModelLoader {
  
  final Set<Class<?>> bobClasses = new HashSet<>();
  
  final Map<String, Class<?>> idClassMap = new HashMap<>();
  
  final Map<String, String> getterIdMap = new HashMap<>();
  final Map<String, String> idGetterMap = new HashMap<>();
  
  Class<? extends DepinjectContext> contextIface;
  
  public void setContextIface(Class<? extends DepinjectContext> iface) throws Exception {
    contextIface = iface;
    
    if (!iface.isInterface()) throw new ContextMustBeInterface(iface);
    ConfigRef configRef = iface.getAnnotation(ConfigRef.class);
    if (configRef == null) throw new NoOrEmptyConfigRef(iface);
    if (configRef.value().length == 0) throw new NoOrEmptyConfigRef(iface);
    
    for (Class<?> configClass : configRef.value()) {
      appendConfig(configClass);
    }
    
  }
  
  private void appendConfig(Class<?> configClass) throws Exception {
    Config configAnn = configClass.getAnnotation(Config.class);
    if (configAnn == null) throw new NoAnnotationConfigInConfiguration(configClass);
    String[] scan = configAnn.scan();
    if (scan.length == 0) {
      scanForBobs(configClass, null);
    } else for (String subpackage : scan) {
      scanForBobs(configClass, subpackage);
    }
  }
  
  private void scanForBobs(Class<?> configClass, String subpackage) throws Exception {
    String subdir = "";
    if (subpackage != null) {
      subdir = "/" + subpackage.replace('.', '/');
    }
    
    String scanPath = configClass.getPackage().getName().replace('.', '/') + subdir;
    
    String packageName = configClass.getPackage().getName();
    if (subpackage != null && subpackage.length() > 0) {
      if (subpackage.startsWith(".")) {
        packageName = packageName + subpackage;
      } else {
        packageName = packageName + '.' + subpackage;
      }
    }
    
    ClassLoader clo = configClass.getClassLoader();
    URL resource = clo.getResource(scanPath);
    scanFolder(new File(resource.getFile()), packageName, clo);
    
  }
  
  private static final String CL_EXT = ".class";
  
  private void scanFolder(File folder, String packageName, ClassLoader clo) throws Exception {
    for (String name : folder.list()) {
      if (name.startsWith(".")) continue;
      File file = new File(folder.getAbsolutePath() + "/" + name);
      if (file.isDirectory()) {
        scanFolder(file, packageName + '.' + name, clo);
        continue;
      }
      if (file.isFile() && name.endsWith(CL_EXT)) {
        Class<?> cl = clo.loadClass(packageName + '.'
            + name.substring(0, name.length() - CL_EXT.length()));
        if (cl.getAnnotation(Bob.class) != null) {
          bobClasses.add(cl);
        }
      }
    }
  }
  
  public void initIdClassMap() {
    FOR: for (Class<?> cl : bobClasses) {
      {
        String id = cl.getAnnotation(Bob.class).value();
        if (id.length() > 0) {
          if (idClassMap.containsKey(id)) throw new BobIdAlreadyExists(id, cl);
          idClassMap.put(id, cl);
          continue FOR;
        }
      }
      
      {
        String id = cl.getSimpleName();
        if (!idClassMap.containsKey(id)) {
          idClassMap.put(id, cl);
          continue FOR;
        }
        int idx = 1;
        while (true) {
          String addId = id + '_' + idx;
          if (!idClassMap.containsKey(addId)) {
            idClassMap.put(addId, cl);
            continue FOR;
          }
          idx++;
        }
      }
    }//end FOR
  }
  
  public static final String toFirstUpper(String s) {
    if (s == null) return null;
    if (s.length() < 0) return s;
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }
  
  public void initGetterIdMap() {
    final Set<String> methodSet = new HashSet<>();
    for (Method m : contextIface.getMethods()) {
      methodSet.add(m.getName());
    }
    
    FOR: for (String id : idClassMap.keySet()) {
      String base = "getter_" + id;
      String name = base;
      int idx = 1;
      while (true) {
        if (!methodSet.contains(name)) {
          methodSet.add(name);
          getterIdMap.put(name, id);
          continue FOR;
        }
        
        name = base + '_' + idx++;
      }
    }//FOR
    
    for (Entry<String, String> e : getterIdMap.entrySet()) {
      idGetterMap.put(e.getValue(), e.getKey());
    }
  }
  
  private String findOneBobIdOrFail(Type type, AccessibleObject accessibleObject) {
    
    final Class<?> targetClass;
    
    if (type instanceof Class) {
      targetClass = (Class<?>)type;
    } else {
      ParameterizedType pt = (ParameterizedType)type;
      Class<?> rawType = (Class<?>)pt.getRawType();
      if (rawType.isAssignableFrom(Autoget.class)) {
        targetClass = (Class<?>)pt.getActualTypeArguments()[0];
      } else {
        targetClass = rawType;
      }
    }
    
    final List<String> foundIds = new LinkedList<>();
    for (Entry<String, Class<?>> e : idClassMap.entrySet()) {
      if (targetClass.isAssignableFrom(e.getValue())) {
        foundIds.add(e.getKey());
      }
    }
    
    if (foundIds.isEmpty()) throw new NoAnyBobForType(type, accessibleObject);
    if (foundIds.size() > 1) throw new TooManyBobsForType(type, foundIds, accessibleObject);
    
    return foundIds.get(0);
  }
  
  private void printContextMethod(Method m, StrPrintStream out, Set<String> needIds) {
    Type retType = m.getGenericReturnType();
    String bobId = findOneBobIdOrFail(retType, m);
    needIds.add(bobId);
    
    String get = "";
    
    String retTypeStr = retType.toString();
    if (retType instanceof Class) {
      retTypeStr = ((Class<?>)retType).getName();
      get = ".get()";
    }
    
    out.println("@Override " + retTypeStr + " " + m.getName() + "(){");
    out.println("  return " + idGetterMap.get(bobId) + get + ";");
    out.println("}");
    out.println();
    
  }
  
  public void printClassContent(StrPrintStream out) {
    Set<String> needIds = new HashSet<>();
    for (Method m : contextIface.getMethods()) {
      printContextMethod(m, out, needIds);
    }
    
    Set<String> addedIds = new HashSet<>();
    
    while (true) {
      String bobId = getOneNotAdded(needIds, addedIds);
      if (bobId == null) return;
      printGetter(out, bobId, needIds);
      addedIds.add(bobId);
    }
    
  }
  
  private String getOneNotAdded(Set<String> needIds, Set<String> addedIds) {
    for (String id : needIds) {
      if (!addedIds.contains(id)) return id;
    }
    return null;
  }
  
  private void printGetter(StrPrintStream out, String bobId, Set<String> needIds) {
    String cachedValue = "cachedValue";
    Class<?> cl = idClassMap.get(bobId);
    String getterName = idGetterMap.get(bobId);
    out.println("private " + Autoget.class.getName() + "<" + cl.getName() + "> " + getterName);
    out.println("  = new " + Autoget.class.getName() + "<" + cl.getName() + ">(){");
    out.println("    " + cl.getName() + " " + cachedValue + " = null;");
    out.println("    @Override " + cl.getName() + " get() {");
    out.println("      if (" + cachedValue + " != null) return " + cachedValue + ";");
    out.println("      " + cl.getName() + " ret = new " + cl.getName() + "();");
    out.println();
    
    for (Field field : cl.getFields()) {
      Type type = field.getGenericType();
      if (type instanceof ParameterizedType) {
        ParameterizedType pt = (ParameterizedType)type;
        Class<?> fcl = (Class<?>)pt.getRawType();
        if (Autoget.class.isAssignableFrom(fcl)) {
          String fieldBobId = findOneBobIdOrFail(type, field);
          needIds.add(fieldBobId);
          String fieldGetter = idGetterMap.get(fieldBobId);
          out.println("      ret." + field.getName() + " = " + fieldGetter + ";");
          out.println();
        }
      }
    }
    
    out.println("      return " + cachedValue + " = ret;");
    out.println("    }");
    out.println("  };");
    out.println();
  }
}

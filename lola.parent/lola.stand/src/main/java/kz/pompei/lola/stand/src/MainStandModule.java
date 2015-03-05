package kz.pompei.lola.stand.src;

import kz.pompei.lola.client.src.MainModule;
import kz.pompei.lola.share.src.remote.account.AuthServiceAsync;
import kz.pompei.lola.stand.src.remote.account.AuthServiceFakeAsync;

public class MainStandModule extends MainModule {
  @Override
  protected void configure() {
    super.configure();
    
    bind(AuthServiceAsync.class).to(AuthServiceFakeAsync.class);
  }
}

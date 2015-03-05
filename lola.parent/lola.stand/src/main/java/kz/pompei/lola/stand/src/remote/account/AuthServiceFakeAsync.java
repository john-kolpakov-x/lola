package kz.pompei.lola.stand.src.remote.account;

import kz.pompei.lola.share.src.model.AuthDetails;
import kz.pompei.lola.share.src.remote.account.AuthService;
import kz.pompei.lola.share.src.remote.account.AuthServiceAsync;

import com.google.inject.Inject;

public class AuthServiceFakeAsync extends FakeSync<Void, AuthDetails> implements AuthServiceAsync {
  @Inject
  public AuthServiceFakeAsync(AuthService sync) {
    super(sync);
  }
}

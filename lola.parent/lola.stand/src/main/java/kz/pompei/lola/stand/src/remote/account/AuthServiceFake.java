package kz.pompei.lola.stand.src.remote.account;

import kz.pompei.lola.share.src.model.AuthDetails;
import kz.pompei.lola.share.src.remote.account.AuthService;

public class AuthServiceFake implements AuthService {
  @Override
  public AuthDetails invoke(Void in) {
    AuthDetails ret = new AuthDetails();
    ret.accountInfo = "Это фэйковый пользователь";
    return ret;
  }
}

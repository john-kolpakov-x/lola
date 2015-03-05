package kz.pompei.lola.share.src.util;

import com.google.gwt.user.client.rpc.RemoteService;

public interface Service<In, Out> extends RemoteService {
  Out invoke(In in);
}

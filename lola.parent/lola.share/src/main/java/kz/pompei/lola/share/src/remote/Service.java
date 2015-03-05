package kz.pompei.lola.share.src.remote;

import com.google.gwt.user.client.rpc.RemoteService;

public interface Service<In, Out> extends RemoteService {
  Out invoke(In in);
}

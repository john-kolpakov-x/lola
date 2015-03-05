package kz.pompei.lola.share.src.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync<In, Out> {
  void invoke(In in, AsyncCallback<Out> callback);
}

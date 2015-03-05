package kz.pompei.lola.stand.src.remote.account;

import kz.pompei.lola.share.src.util.Service;
import kz.pompei.lola.share.src.util.ServiceAsync;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FakeSync<In, Out> implements ServiceAsync<In, Out> {
  
  private final Service<In, Out> sync;
  
  public FakeSync(Service<In, Out> sync) {
    this.sync = sync;
  }
  
  @Override
  public void invoke(final In in, final AsyncCallback<Out> callback) {
    new Timer() {
      @Override
      public void run() {
        try {
          Out out = sync.invoke(in);
          callback.onSuccess(out);
        } catch (Throwable t) {
          callback.onFailure(t);
        }
      }
    }.schedule(1000);
  }
  
}

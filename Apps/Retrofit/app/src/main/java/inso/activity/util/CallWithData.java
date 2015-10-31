package inso.activity.util;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public abstract class CallWithData<T> implements Callback<T> {

    private Object data;

   public CallWithData(Object data) {
       this.data = data;
   }

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {

    }

    @Override
    public void onFailure(Throwable t) {

    }

    public Object getData() {
        return data;
    }
}

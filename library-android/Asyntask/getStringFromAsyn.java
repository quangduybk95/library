package material.quangduy.com.material.until;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

/**
 * Created by Quang Duy on 14-Nov-15.
 */
public class getStringFromAsyn {
    public String getString(AsyncTask asyncTask) {
        String result = "";
        try {
            result = asyncTask.get().toString();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}

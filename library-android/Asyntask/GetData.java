package material.quangduy.com.material.until;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by Quang Duy on 28-Nov-15.
 */
public class GetData  extends
        AsyncTask<String, Void, String> {
    JSONParser parser = new JSONParser();
    private ProgressDialog pd;
    private boolean finished = false;
    List<NameValuePair> param;
    String url="";
    Context context;
    public GetData(Context context,String url,List<NameValuePair> param)
    {
        this.context = context;
        this.url = url;
        this.param = param;
    }
    public void attach() {
        if (this.finished == false) {
            pd = ProgressDialog.show(context, "Please wait...",
                    "Loading data...", true, false);
        }
    }

    public void detach() {
        pd.dismiss();
    }

    @Override
    protected void onPreExecute() {
//            if (!parser.checkInternetConnect(getActivity())) {
//                Toast.makeText(getActivity(), "You are offline",
//                        Toast.LENGTH_LONG).show();
//                cancel(true);
//            }
        pd = ProgressDialog.show(context, "Please wait...",
                "Loading data...", true, false);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        if (null != result) {
            try {
                if ((this.pd != null) && this.pd.isShowing()) {
                    this.pd.dismiss();
                }
            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                this.pd = null;
            }
        } else
            Toast.makeText(context, "ok", Toast.LENGTH_LONG).show();
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {
        return parser.makeHttpRequest(url, "POST",
                param);
    }
}
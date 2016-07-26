package material.quangduy.com.material.fragment;

/**
 * Created by Quang Duy on 17-Sep-15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import material.quangduy.com.material.R;

/**
 * Created by Quang Duy on 16-Sep-15.
 */
public class secondPageFragment extends Fragment{

    public static secondPageFragment newInstance()
    {
        secondPageFragment main = new secondPageFragment();
        return main;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.secondpage_layout, container, false);
        Toast.makeText(getActivity(), "Hello World", Toast.LENGTH_LONG).show();

        return layout;
    }
}

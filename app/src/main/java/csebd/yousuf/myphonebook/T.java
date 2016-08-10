package csebd.yousuf.myphonebook;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Yousuf on 3/9/2016.
 */
public class T {

    public static void t(Context c, String m) {
        Toast.makeText(c, m, Toast.LENGTH_SHORT).show();
    }

    public static void d(String m) {
        Log.d("www.d.com", m);
    }
}

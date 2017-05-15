package web.afor.innovation.quizzhub.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import web.afor.innovation.quizzhub.R;


public class Utils {
    public static void launchActivity(Activity activity, Class<?> cls) {

        Intent intent = new Intent(activity, cls);
       /* if (bundle != null)
            intent.putExtras(bundle);*/
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_close_translate, R.anim.activity_open_scale);

    }
}


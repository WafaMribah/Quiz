package web.afor.innovation.quizzhub.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.R;

/**
 * Created by wafa on 7/29/16.
 */
public class preferences {
    public static void saveString( String value,String key, Context context){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String loadString(Context context, String key, String defaulValue){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defaulValue);
    }



    public static void savePreferencesBoolean(Context context, String key, Boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean readPreferencesBoolean(Context context, String key, Boolean defaultValue){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, defaultValue);
    }
    public static void saveInt( String key,int value, Context context){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public static int loadInt(String key,Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key,0);
    }




    //il te manque les saves et loads les booleens tout comme les strings pour le score un class staticpur la first time

}

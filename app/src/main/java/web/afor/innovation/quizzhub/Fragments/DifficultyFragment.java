package web.afor.innovation.quizzhub.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import web.afor.innovation.quizzhub.Activities.MainActivity;
import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.R;


public class DifficultyFragment extends Fragment {
    Button bt_easy;
    Button bt_medium;
    Button bt_hard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

                View view =inflater.inflate(R.layout.fragment_difficulty, container, false);
               Button bt_easy = (Button) view.findViewById(R.id.bt_easy);
               Button bt_medium = (Button) view.findViewById(R.id.bt_medium);
               Button bt_hard = (Button) view.findViewById(R.id.bt_hard);
        bt_easy.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                       ((MainActivity)getActivity()).replaceFragmentWithBundle("mode","easy");



                    }
                });
        bt_medium.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragmentWithBundleandTimer("mode","easy",30,1);



            }
        });
        bt_hard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragmentWithBundleandTimer("mode","easy",50,1);



            }
        });


        return view ;
    }





    public void replaceFragmentWithBundle (String key,String value,int position){

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment fragment =Fragment.instantiate(getActivity(),Constant.ArrayFragment[position]);


        Bundle bundle=new Bundle();
        bundle.putString(key,value);

        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_layout,fragment);
        fragmentTransaction.commit();

    }






}

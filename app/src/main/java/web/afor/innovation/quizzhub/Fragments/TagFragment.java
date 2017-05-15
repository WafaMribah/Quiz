package web.afor.innovation.quizzhub.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import web.afor.innovation.quizzhub.Activities.MainActivity;
import web.afor.innovation.quizzhub.Activities.TagActivity;
import web.afor.innovation.quizzhub.Adaptors.QuizAdaptor;
import web.afor.innovation.quizzhub.Config.Constant;
import web.afor.innovation.quizzhub.Models.ApiInterface;
import web.afor.innovation.quizzhub.Models.JSONResponse;
import web.afor.innovation.quizzhub.Models.Tags;
import web.afor.innovation.quizzhub.R;
import web.afor.innovation.quizzhub.Utils.Utils;
import web.afor.innovation.quizzhub.WebService.DeviderItemDecoration;


public class TagFragment extends Fragment {
    private List<Tags> results = new ArrayList<>();
    public RecyclerView recyclerView;
    private QuizAdaptor adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        initViews(view);
        loadJSON();
        return view;
    }

    public void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DeviderItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new MainActivity.ClickListener() {

            public void onClick(View view, int position) {
                Log.d("String", "maktouba " + results.get(position).getName_Tag());

             /*   ((MainActivity) getActivity()).replaceFragmentWithBundle("fragment",
                        results.get(position).getName_Tag().toString());
                Bundle b = new Bundle();
                b.putString("tag",results.get(position).getName_Tag());
                Utils.launchActivity(getActivity(),b,TagActivity.class);*/
                ((MainActivity)getActivity()).replaceFragmentWithBundle("fragment", results.get(position).getName_Tag().toString());

                // Movie movie = movieList.get(position);
                // Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            public void onLongClick(View view, int pos) {


            }
        }));
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface request = retrofit.create(ApiInterface.class);
        Call<JSONResponse> call = request.getResults();
        call.enqueue(new Callback<JSONResponse>() {


            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();

                results = jsonResponse.getResults();
                Log.d("String", "" + results.get(0).getName_Tag());
                Log.d("TAG", "session" + results.size());
                adapter = new QuizAdaptor(results);

                recyclerView.setAdapter(adapter);
            }

            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }

        });
    }


    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }
}
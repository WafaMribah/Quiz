package web.afor.innovation.quizzhub.Adaptors;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import web.afor.innovation.quizzhub.Models.Tags;
import web.afor.innovation.quizzhub.R;

public class QuizAdaptor extends RecyclerView.Adapter<QuizAdaptor.MyViewHolder> {
    public ImageView Image_tag;
    private List<Tags> TagsList;
    public QuizAdaptor(List<Tags> ListeTags) {
        this.TagsList = ListeTags;
    }
    public QuizAdaptor.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tags, viewGroup, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        protected TextView text_tag;
        protected ImageView Image_tag;

        public MyViewHolder(View view) {
            super(view);
             text_tag = (TextView) view.findViewById(R.id.text_tag);
             Image_tag = (ImageView) view.findViewById(R.id.image_tag);
        }

    }


    public void onBindViewHolder(QuizAdaptor.MyViewHolder viewHolder, int position) {
        viewHolder.text_tag.setText(TagsList.get(position).getName_Tag());
        //Picasso.with(activity.getApplicationContext()).load("http://simpleicon.com/wp-content/uploads/flag.png").resize(32, 32).into(viewHolder.Image_tag);


       // viewHolder.Image_tag.setImage(TagsList.get(position).getVer());


    }



    public int getItemCount() {
        return TagsList.size();
    }

}

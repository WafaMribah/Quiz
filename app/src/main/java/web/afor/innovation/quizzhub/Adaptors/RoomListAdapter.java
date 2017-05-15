package web.afor.innovation.quizzhub.Adaptors;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

import java.util.ArrayList;
import java.util.StringTokenizer;

import web.afor.innovation.quizzhub.Fragments.ListRoomFragment;
import web.afor.innovation.quizzhub.R;

/**
 * Created by Administrateur on 03/08/2016.
 */
public class RoomListAdapter extends BaseAdapter {

    private ArrayList<String> roomIdList=new ArrayList<String>();

    private ListRoomFragment roomListFragment;

    private Context context;

    public RoomListAdapter(Context c){
        this.context=c;
        roomListFragment=(ListRoomFragment) c;
    }
    @Override
    public int getCount() {
        return roomIdList.size();
    }


    public void setData(RoomData[] roomData){
        roomIdList.clear();
        for(int i=0;i<roomData.length;i++){
            roomIdList.add(roomData[i].getId());
        }
        notifyDataSetChanged();
    }

    public void clear(){
        roomIdList.clear();
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return roomIdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_room, null);
        }
        if (roomIdList != null) {
            Log.d("roomIdList", "not null:"+roomIdList.size());
            TextView roomId = (TextView) convertView.findViewById(R.id.item_roomId);
            Button joinButton = (Button) convertView.findViewById(R.id.item_joinButton);
            roomId.setText(roomIdList.get(position));
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("positionnn","gg"+position);
                    roomListFragment.joinRoom(roomIdList.get(position));
                }
            });
        }
        return convertView;
    }
}

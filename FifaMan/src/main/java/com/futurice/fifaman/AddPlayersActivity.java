package com.futurice.fifaman;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.futurice.fifaman.models.Player;

import java.util.ArrayList;
import java.util.List;

public class AddPlayersActivity extends ListActivity {

    private PlayerListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new PlayerListAdapter();

        View footer = getLayoutInflater().inflate(R.layout.add_player_list_footer, null);
        ImageButton addPlayerButton = (ImageButton)footer.findViewById(android.R.id.button1);
        addPlayerButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addPlayer();
            }
        });

        ListView list = getListView();
        list.addFooterView(footer);

        setListAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class PlayerListAdapter extends BaseAdapter {

        private List<Player> mPlayers;

        public PlayerListAdapter() {
            mPlayers = new ArrayList<Player>();
        }

        public void addPlayer() {
            Player player = new Player();
            mPlayers.add(player);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mPlayers.size();
        }

        @Override
        public Object getItem(int position) {
            return mPlayers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mPlayers.get(position).getName().hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.add_player_list_item, parent, false);
            }

            Player player = mPlayers.get(position);

            TextView nameField = (TextView)convertView.findViewById(android.R.id.text1);
            nameField.setText(player.getName());
            return convertView;
        }
    }
}

package com.futurice.fifaman;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.futurice.fifaman.models.Player;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.concurrency.AndroidSchedulers;
import rx.concurrency.Schedulers;

public class RecentPlayersActivity extends ListActivity implements Observer<Player> {

    private final List<Player> mPlayers = new ArrayList<Player>();
    private final RecentPlayersListAdapter mAdapter = new RecentPlayersListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_players_activity);
        setListAdapter(mAdapter);

        Repository repo = new Repository();
        repo.getPlayers().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recent_players, menu);
        return true;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onNext(Player player) {
        mPlayers.add(player);
        mAdapter.notifyDataSetChanged();
    }

    private class ListItemViewHolder {
        public CheckBox checkBox;
    }

    private class RecentPlayersListAdapter extends BaseAdapter {

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
            ListItemViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.recent_player_list_item, parent, false);
                holder = new ListItemViewHolder();
                holder.checkBox = (CheckBox) convertView.findViewById(android.R.id.checkbox);
                convertView.setTag(holder);
            }
            else {
                holder = (ListItemViewHolder) convertView.getTag();
            }

            holder.checkBox.setText(mPlayers.get(position).getName());

            return convertView;
        }
    }
}

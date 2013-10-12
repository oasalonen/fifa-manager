package com.futurice.fifaman;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.futurice.fifaman.models.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.concurrency.AndroidSchedulers;
import rx.concurrency.Schedulers;

public class RecentPlayersActivity extends ListActivity implements Observer<Player> {

    private final List<PlayerSelectionItem> mPlayers = new ArrayList<PlayerSelectionItem>();
    private final RecentPlayersListAdapter mAdapter = new RecentPlayersListAdapter();
    private View mFooter;
    private CheckBox mHeaderCheckbox;
    private boolean mIgnoreSelectAllCheckedEvent;
    private Subscription mGetPlayersSubscription;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_players_activity);
        createFooter();
        createHeader();
        setListAdapter(mAdapter);

        Repository repo = new Repository();
        mGetPlayersSubscription = repo.getPlayers().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this);
    }

    @Override
    protected void onDestroy() {
        mGetPlayersSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recent_players, menu);
        return true;
    }

    private void createFooter() {
        mFooter = getLayoutInflater().inflate(R.layout.list_loading_footer, null);
        getListView().addFooterView(mFooter);
    }

    private void createHeader() {
        View header = getLayoutInflater().inflate(R.layout.recent_player_list_item, null);
        mHeaderCheckbox = (CheckBox) header.findViewById(android.R.id.checkbox);
        mHeaderCheckbox.setTypeface(Typeface.DEFAULT_BOLD);
        mHeaderCheckbox.setText(R.string.recent_players_select_all);
        mHeaderCheckbox.setEnabled(mPlayers.size() > 0);
        mHeaderCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mIgnoreSelectAllCheckedEvent) {
                    setSelectionStateOfAllPlayers(isChecked);
                }
            }
        });

        getListView().addHeaderView(header);
    }

    private void setSelectAllIsCheckedProgrammatically(boolean isChecked) {
        mIgnoreSelectAllCheckedEvent = true;
        mHeaderCheckbox.setChecked(isChecked);
        mIgnoreSelectAllCheckedEvent = false;
    }

    private void setSelectionStateOfAllPlayers(boolean isSelected) {
        for (Iterator<PlayerSelectionItem> it = mPlayers.iterator(); it.hasNext();) {
            it.next().setIsSelected(isSelected);
        }
    }

    private void handlePlayerSelectionChange() {
        for (Iterator<PlayerSelectionItem> it = mPlayers.iterator(); it.hasNext();) {
            if (!it.next().getIsSelected()) {
                setSelectAllIsCheckedProgrammatically(false);
                return;
            }
        }
        setSelectAllIsCheckedProgrammatically(true);
    }

    @Override
    public void onCompleted() {
        getListView().removeFooterView(mFooter);
    }

    @Override
    public void onError(Throwable throwable) {
        getListView().removeFooterView(mFooter);
    }

    @Override
    public void onNext(final Player player) {
        mPlayers.add(new PlayerSelectionItem(player, false));
        mAdapter.notifyDataSetChanged();
        mHeaderCheckbox.setEnabled(mPlayers.size() > 0);
        handlePlayerSelectionChange();
    }

    private static class ListItemViewHolder {
        public CheckBox checkBox;
    }

    private static class PlayerSelectionItem {
        public final Player player;
        private boolean mIsSelected;
        private OnIsSelectedChangedListener mIsSelectedListener;

        public interface OnIsSelectedChangedListener {
            public void isSelectedChanged(boolean isSelected);
        }

        public PlayerSelectionItem(final Player player, final boolean isSelected) {
            this.player = player;
            mIsSelected = isSelected;
        }

        public void setIsSelected(final boolean isSelected) {
            mIsSelected = isSelected;
            if (mIsSelectedListener != null) {
                mIsSelectedListener.isSelectedChanged(mIsSelected);
            }
        }

        public boolean getIsSelected() {
            return mIsSelected;
        }

        public void setIsSelectedListener(final OnIsSelectedChangedListener listener) {
            mIsSelectedListener = listener;
        }
    }

    private class RecentPlayersListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mPlayers.size();
        }

        @Override
        public Object getItem(final int position) {
            return mPlayers.get(position);
        }

        @Override
        public long getItemId(final int position) {
            return mPlayers.get(position).player.getName().hashCode();
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
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

            // Listen to selection state changes and update UI accordingly
            final CheckBox checkBox = holder.checkBox;
            final PlayerSelectionItem item = mPlayers.get(position);
            item.setIsSelectedListener(new PlayerSelectionItem.OnIsSelectedChangedListener() {
                @Override
                public void isSelectedChanged(boolean isSelected) {
                    checkBox.setChecked(isSelected);
                }
            });

            // Listen to the UI checkbox state changes and update the model accordingly
            holder.checkBox.setText(item.player.getName());
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setIsSelected(isChecked);
                    handlePlayerSelectionChange();
                }
            });

            return convertView;
        }
    }
}

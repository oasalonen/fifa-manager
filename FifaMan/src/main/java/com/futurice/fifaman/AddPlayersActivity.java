package com.futurice.fifaman;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.futurice.fifaman.models.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddPlayersActivity extends ListActivity {

    private static final String PLAYERS_DATA_KEY = "players";

    private final ArrayList<Player> mPlayers = new ArrayList<Player>();
    private final PlayerListAdapter mAdapter = new PlayerListAdapter();
    private EditText mPlayerNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createActionBar();
        setContentView(R.layout.activity_main);
        createFooter();
        setListAdapter(mAdapter);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle data) {
        super.onRestoreInstanceState(data);
        List<Player> players = data.getParcelableArrayList(PLAYERS_DATA_KEY);
        mPlayers.addAll(players);
    }

    @Override
    protected void onSaveInstanceState(final Bundle data) {
        data.putParcelableArrayList(PLAYERS_DATA_KEY, mPlayers);
        super.onSaveInstanceState(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.recent_cancel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recent:
                Intent intent = new Intent(this, RecentPlayersActivity.class);
                startActivity(intent);
                return true;
            case R.id.cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createActionBar() {
        final LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View doneActionBarView = inflater.inflate(R.layout.actionbar_view_done, null);
        final View doneAction = doneActionBarView.findViewById(R.id.actionbar_done);
        doneAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM |
                ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(doneActionBarView);
    }

    private void createFooter() {
        final View footer = getLayoutInflater().inflate(R.layout.add_player_list_footer, null);

        final ImageButton addPlayerButton = (ImageButton)footer.findViewById(android.R.id.button1);
        addPlayerButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
            }
        });
        addPlayerButton.setEnabled(false);

        mPlayerNameEdit = (EditText)footer.findViewById(android.R.id.text1);
        mPlayerNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addPlayer();
                    return true;
                }
                else {
                    return false;
                }
            }
        });
        mPlayerNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addPlayerButton.setEnabled(canAddPlayer(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ListView list = getListView();
        list.addFooterView(footer);
    }

    private void addPlayer() {
        String trimmedName = mPlayerNameEdit.getText().toString().trim();
        if (canAddPlayer(trimmedName)) {
            mPlayerNameEdit.setText("");
            mPlayers.add(new Player(trimmedName));
            mAdapter.notifyDataSetChanged();
        }
    }

    private boolean canAddPlayer(final String name) {
        String trimmedName = mPlayerNameEdit.getText().toString().trim();
        return trimmedName.length() > 0 && !isPlayerAdded(trimmedName);
    }

    private boolean isPlayerAdded(final String name) {
        for (Iterator<Player> i = mPlayers.iterator(); i.hasNext();) {
            if (i.next().getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static class ListItemViewHolder {
        public TextView nameField;
        public ImageButton removeButton;
    }

    private class PlayerListAdapter extends BaseAdapter {

        public PlayerListAdapter() {
        }

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
            return mPlayers.get(position).getName().hashCode();
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ListItemViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.add_player_list_item, parent, false);
                holder = new ListItemViewHolder();
                holder.nameField = (TextView)convertView.findViewById(android.R.id.text1);
                holder.removeButton = (ImageButton)convertView.findViewById(android.R.id.icon1);
                convertView.setTag(holder);
            }
            else {
                holder = (ListItemViewHolder) convertView.getTag();
            }

            if (position % 2 == 1) {
                convertView.setBackgroundColor(getResources().getColor(R.color.add_player_list_item_background_highlight));
            }
            else {
                convertView.setBackgroundColor(0x00000000);
            }

            Player player = mPlayers.get(position);
            holder.nameField.setText(player.getName());
            holder.removeButton.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayers.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}

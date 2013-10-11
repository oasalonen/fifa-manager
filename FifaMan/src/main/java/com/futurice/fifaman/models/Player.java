package com.futurice.fifaman.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Olli on 26/09/13.
 */
public class Player implements Parcelable {

    private String mName;

    public Player() {
        mName = "";
    }

    public Player(String name) {
        assert name != null;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        assert name != null;
        mName = name;
    }

    // START Parcelable
    private static int PARCELABLE_VERSION = 1;

    private Player(Parcel data) {
        int version = data.readInt();
        if (version <= PARCELABLE_VERSION) {
            mName = data.readString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel data, int flags) {
        data.writeInt(PARCELABLE_VERSION);
        data.writeString(mName);
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Player createFromParcel(Parcel data) {
            return new Player(data);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    // END Parcelable
}

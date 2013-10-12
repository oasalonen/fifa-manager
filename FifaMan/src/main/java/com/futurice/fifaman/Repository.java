package com.futurice.fifaman;

import android.os.SystemClock;

import com.futurice.fifaman.models.Player;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Olli on 12/10/13.
 */
public class Repository {

    public Observable<Player> getPlayers() {
        return Observable.create(new Observable.OnSubscribeFunc<Player>() {
            @Override
            public Subscription onSubscribe(Observer<? super Player> observer) {
                SystemClock.sleep(300);
                observer.onNext(new Player("Joe"));
                SystemClock.sleep(300);
                observer.onNext(new Player("Jimmy"));
                SystemClock.sleep(300);
                observer.onNext(new Player("Jane"));
                SystemClock.sleep(300);
                observer.onNext(new Player("John"));
                return Subscriptions.empty();
            }
        });
    }

}

package com.futurice.fifaman;

import android.os.SystemClock;

import com.futurice.fifaman.models.Player;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Olli on 12/10/13.
 */
public class Repository {

    public Observable<Player> getPlayers() {
        return Observable.create(new Observable.OnSubscribeFunc<Player>() {
            @Override
            public Subscription onSubscribe(Observer<? super Player> observer) {
                BooleanSubscription subscription = new BooleanSubscription();

                try {
                    SystemClock.sleep(1500);
                    String[] players = new String[] { "Joe", "Jimmy", "Jane", "John"};
                    for (int i = 0; i < players.length; i++) {
                        SystemClock.sleep(300);
                        observer.onNext(new Player(players[i]));
                    }
                    observer.onCompleted();
                }
                catch (Throwable ex) {
                    observer.onError(ex);
                }
                return subscription;
            }
        });
    }

}

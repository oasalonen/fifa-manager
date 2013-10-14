package com.futurice.fifaman;

import com.futurice.fifaman.models.Player;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.BooleanSubscription;

/**
 * Created by Olli on 12/10/13.
 */
public class Repository {

    public Observable<Player> getPlayers() {
        return Observable.create(new Observable.OnSubscribeFunc<Player>() {

            @Override
            public Subscription onSubscribe(final Observer<? super Player> observer) {
                final BooleanSubscription subscription = new BooleanSubscription();

                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String[] players = new String[] { "Joe", "Jimmy", "Jane", "John"};
                            Thread.sleep(1000);
                            for (int i = 0; i < players.length; i++) {
                                if (subscription.isUnsubscribed()) {
                                    System.out.println("############### UNSUB");
                                    return;
                                }
                                Thread.sleep(300);
                                observer.onNext(new Player(players[i]));
                            }
                            observer.onCompleted();
                        }
                        catch (Throwable ex) {
                            observer.onError(ex);
                        }
                    }
                });
                thread.start();

                return subscription;
            }
        });
    }

}

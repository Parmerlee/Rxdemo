package com.bonc.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        });
//
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String integer) throws Exception {
//                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };
//
////        observable.subscribe(consumer);
//
        observable
                .subscribeOn(Schedulers.newThread()) //Schedulers.newThread() Rx内置的子线程，subscribeOn指定subscribe运行的线程
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {

                        return "this is a :" + integer;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //()Android主线程，observeOn同上。
                .subscribe(consumer);
//
//
//        observable.flatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                final List<String> list = new ArrayList<>();
//                for (int i = 0; i < 3; i++) {
//                    list.add("I am value " + integer);
//                }
//                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
//            }
//        }).subscribe(consumer);
//
//        observable.concatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                final List<String> list = new ArrayList<>();
//                for (int i = 0; i < 3; i++) {
//                    list.add("I am value " + integer);
//                }
//                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
//            }
//        }).subscribe(consumer);


        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {

                Log.d(TAG, "subscribe: 1");
                e.onNext(1);
                Log.d(TAG, "subscribe: 2");
                e.onNext(2);
                Log.d(TAG, "subscribe: 3");
                e.onNext(3);
                Log.d(TAG, "subscribe: 4");
                e.onNext(4);
                Log.d(TAG, "subscribe: complete1");
                e.onComplete();
            }
        });
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {

                Log.d(TAG, "subscribe: A");
                e.onNext("A");
                Log.d(TAG, "subscribe: B");
                e.onNext("B");
                Log.d(TAG, "subscribe: C");
                e.onNext("C");
                Log.d(TAG, "subscribe: complete2");
                e.onComplete();
            }
        });
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                return integer + s;
            }
        }).subscribe(consumer);
    }


}

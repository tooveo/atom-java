package com.ironsrc.test;

import com.google.gson.Gson;
import com.ironsrc.atom.IronSourceAtom;
import com.ironsrc.atom.IronSourceAtomTracker;
import com.ironsrc.atom.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.StreamHandler;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class MultiThreadTest {
    private static String stream = "sdkdev_sdkdev.public.g8y3etest";
    private static String authKey = "I40iwPPOsG3dfWX30labriCg9HqMfL";

    static IronSourceAtom atom_;
    static IronSourceAtomTracker atomTracker_ = new IronSourceAtomTracker();

    static {
        atomTracker_.enableDebug(true);
        atomTracker_.setAuth(authKey);
        atomTracker_.setBulkBytesSize(2048);
        atomTracker_.setBulkSize(2);
        atomTracker_.setFlushInterval(2000);
        atomTracker_.setEndpoint("http://track.atom-data.io/");
    }

    public static void runTest() {
        List<Thread> threads = new ArrayList<Thread>();
        IntStream.range(0, 10).forEach(index -> {
            Thread threadObj = new Thread(() -> {
                int randTimer = (int)(2000 * Math.random());
                try {
                    Thread.sleep(randTimer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("From thread id: " + index);

                for (int reqIndex = 0; reqIndex < 6; reqIndex++) {
                    HashMap<String, String> dataMap = new HashMap<String, String>();

                    dataMap.put("strings", "data " + reqIndex);
                    dataMap.put("id", "" + index);

                    System.out.println("Request: " + new Gson().toJson(dataMap));
                    atomTracker_.track(stream, new Gson().toJson(dataMap));
                }
            });
            threadObj.run();

            threads.add(threadObj);
        });

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threads.stream().forEach(thread -> {
            /*try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        });

        atomTracker_.flush();
    }
}

public class ExampleStaticThreads {
    public static void main(String ... argc) {
        MultiThreadTest.runTest();
    }
}
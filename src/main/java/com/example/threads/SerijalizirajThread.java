package com.example.threads;

import com.example.utils.Promjena;

public class SerijalizirajThread extends ThreadResourceCommunication implements Runnable {
    private Promjena promjena;

    public SerijalizirajThread(Promjena promjena) {
        this.promjena = promjena;
    }

    @Override
    public void run() {
        super.serijaliziraj(promjena);
    }
}

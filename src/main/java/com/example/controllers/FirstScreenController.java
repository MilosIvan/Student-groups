package com.example.controllers;

import com.example.threads.RefreshDataThread;

public class FirstScreenController {

    public void initialize() {
        RefreshDataThread thread = new RefreshDataThread();
        Thread threadStarter = new Thread(thread);
        threadStarter.setDaemon(true);
        threadStarter.start();
    }

}

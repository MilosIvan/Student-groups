package com.example.utils;

import com.example.ApplicationProject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serijalizacija {
    public static void serijaliziraj(Promjena promjena) {
        List<Object> objectList;
        objectList = deserijaliziraj();
        objectList.add(promjena);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dat//serijalizacija.txt"))) {

            oos.writeObject(objectList);

        } catch (IOException e) {
            ApplicationProject.logger.error("Greška prilikom serijalizacije");
            System.out.println(e.getMessage());
        }
    }

    public static List<Object> deserijaliziraj() {
        List<Object> objectList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dat//serijalizacija.txt"))) {

            Object object = ois.readObject();

            if(object instanceof List<?>) {
                objectList = (List<Object>) object;
            }

        } catch (IOException e) {
            ApplicationProject.logger.error("Greška prilikom deserijalizacije");
        } catch (ClassNotFoundException e) {
            ApplicationProject.logger.error("ClassNotFoundException prilikom deserijalizacije");
            System.out.println(e.getMessage());
        }

        return objectList;
    }

}

package com.example.catapp;


import java.util.ArrayList;
import java.util.HashMap;

public class FakeDatabase {

    public static HashMap<String, CatModel> cats = new HashMap<>();
    public static HashMap<String, ImageModel> images = new HashMap<>();

    //Fake database as per Tutorial Examples

    public static void saveCatsToFakeDatabase(ArrayList<CatModel> catsToSave) {
        for(int i = 0; i < catsToSave.size(); i++) {
            CatModel cat = catsToSave.get(i);
            cats.put(cat.getId(),cat);
        }
    }

    public static void saveImagesToFakeDatabase(ArrayList<ImageModel> imagesToSave) {
        for(int i = 0; i < imagesToSave.size(); i++) {
            ImageModel image = imagesToSave.get(i);
            images.put(image.getId(),image);
        }
    }

    public static CatModel getCatById(String id) {return cats.get(id);}

    public static ImageModel getImageById(String id) {return images.get(id);}
}

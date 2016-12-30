package com.github.thushear.audio;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;

/**
 * Created by kongming on 2016/12/30.
 */
public class MP3Utils {

    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Mp3File mp3File = new Mp3File("D:\\05b87b41-4e60-4414-ba06-b7f8cfec7012.mp3");
            System.out.println(mp3File.getLengthInSeconds());
            String time ="0" + mp3File.getLengthInSeconds() / 60 + ":" + mp3File.getLengthInSeconds() % 60;
        }
        long end = System.currentTimeMillis();

        System.out.println("time = " + (end - start));

    }
}

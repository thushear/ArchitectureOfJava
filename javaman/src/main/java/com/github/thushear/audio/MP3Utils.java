package com.github.thushear.audio;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

/**
 * Created by kongming on 2016/12/30.
 */
public class MP3Utils {

    public static void main(String[] args) throws InvalidDataException, IOException, UnsupportedTagException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        long start = System.currentTimeMillis();
        File file = new File("D:\\mp3\\58c82162-b06a-4532-87dc-5cdd6f7e1f50.mp3");
        for (int i = 0; i < 1; i++) {
            Mp3File mp3File = new Mp3File("D:\\mp3\\58c82162-b06a-4532-87dc-5cdd6f7e1f50.mp3");
            System.out.println(mp3File.getBitrate());
            System.out.println(mp3File.getLength());
            System.out.println(mp3File.getLengthInSeconds());
            System.out.println(mp3File.getLengthInMilliseconds());
            System.out.println(mp3File.getBitrates());
            System.out.println(mp3File.getSampleRate());
            String time ="0" + mp3File.getLengthInSeconds() / 60 + ":" + ( mp3File.getLengthInSeconds() % 60 >= 10 ? mp3File.getLengthInSeconds() % 60 : "0" + mp3File.getLengthInSeconds() % 60);
            System.out.println(time);

            Mp3File mp3File1 = new Mp3File("D:\\mp3\\58c82162-b06a-4532-87dc-5cdd6f7e1f50.mp3");
            System.out.println(mp3File1.getBitrate());
            System.out.println(mp3File1.getLength());
            System.out.println(mp3File1.getLengthInSeconds());
            System.out.println(mp3File1.getLengthInMilliseconds());
            System.out.println(mp3File1.getBitrates());
            System.out.println(mp3File1.getSampleRate());
            String time2 ="0" + mp3File1.getLengthInSeconds() / 60 + ":" + ( mp3File1.getLengthInSeconds() % 60 >= 10 ? mp3File1.getLengthInSeconds() % 60 : "0" + mp3File1.getLengthInSeconds() % 60);
            System.out.println(time2);


        }

        MP3File f = (MP3File) AudioFileIO.read(file);
        MP3AudioHeader mp3AudioHeader = (MP3AudioHeader) f.getAudioHeader();
        System.out.println(mp3AudioHeader.getTrackLength());
        System.out.println(mp3AudioHeader.getTrackLengthAsString());
        System.out.println(mp3AudioHeader.getSampleRateAsNumber());
        System.out.println(mp3AudioHeader.getBitRate());





    }
}

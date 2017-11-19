package com.example.karthi.gtsword;

/**
 * Created by Karthi on 11-Sep-17.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Chunkizer {
    public List<Chunk> Chunkize(Chunk c,int chunkSize){
        List<Chunk> chunks = new ArrayList<Chunk>();
        int min = c.getStartVerseNum();
        int max = c.getEndVerseNum();
        int seq = 1;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar ca = Calendar.getInstance();
        String currDate = df.format(ca.getTime());
        while(max-min>=chunkSize) {
            Chunk subChunk = new Chunk();
            subChunk.setBookName(c.getBookName());
            subChunk.setChapNum(c.getChapNum());
            subChunk.setSeq(seq);
            subChunk.setSpace(1);
            subChunk.setStartVerseNum(min);
            subChunk.setEndVerseNum(min+chunkSize-1);
            subChunk.setSecId(c.getSecId());
            if(seq==1){
                subChunk.setNextDateOfReview(currDate);
            }
            else{
                subChunk.setNextDateOfReview("NA");
            }
            subChunk.setMastered(false);
            chunks.add(subChunk);
            seq++;
            min = min+chunkSize;
        }
        if(max-min>=0){
            Chunk subChunk = new Chunk();
            subChunk.setBookName(c.getBookName());
            subChunk.setChapNum(c.getChapNum());
            subChunk.setSeq(seq);
            subChunk.setSpace(1);
            subChunk.setStartVerseNum(min);
            subChunk.setEndVerseNum(max);
            subChunk.setSecId(c.getSecId());
            if(seq==1){
                subChunk.setNextDateOfReview(currDate);
            }
            else{
                subChunk.setNextDateOfReview("NA");
            }
            subChunk.setMastered(false);
            chunks.add(subChunk);
            seq++;
            min = min+chunkSize;
        }
        return chunks;
    }


}
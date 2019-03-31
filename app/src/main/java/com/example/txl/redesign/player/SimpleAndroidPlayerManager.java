package com.example.txl.redesign.player;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/8/10
 * description：
 */
public class SimpleAndroidPlayerManager {
    private static SimpleAndroidPlayer currentPlayer;

    public static SimpleAndroidPlayer newInstance(){
        currentPlayer = new SimpleAndroidPlayer( true,true );
        return currentPlayer;
    }

    public static SimpleAndroidPlayer getCurrentSimpleAndroidPlayer(){
        if(currentPlayer == null){
            throw new NullPointerException( "you have not create a SimpleAndroidPlayer" );
        }
        return currentPlayer;
    }
}

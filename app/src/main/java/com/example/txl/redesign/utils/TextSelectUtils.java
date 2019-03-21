package com.example.txl.redesign.utils;


import android.content.Context;
import android.graphics.Color;
import android.util.Pair;

/**
 * @author TXL
 * description :用于全局处理文字的选中色和未选中的颜色
 */
public class TextSelectUtils {
    /**
     * 未选中黑色
     * */
    private static final int normalColor = Color.BLACK;
    /**
     * 选中白色
     * */
    private static final int selectColor = Color.WHITE;
    private static Pair<Integer, Integer> colorPair ;

    private TextSelectUtils(){}

    public static Pair<Integer, Integer> getColorPair(Context context){
        if(colorPair == null){
            colorPair = Pair.create(normalColor,selectColor);
        }
        return colorPair;
    }
}

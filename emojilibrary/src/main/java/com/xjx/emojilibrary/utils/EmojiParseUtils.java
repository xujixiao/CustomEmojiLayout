package com.xjx.emojilibrary.utils;

import android.content.Context;
import android.content.pm.ActivityInfo;

import com.dd.plist.NSArray;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by 11073 on 2018/1/24.
 * 解析emoji的表情的工具
 */

public class EmojiParseUtils {
    public static List<String> parseEmoji(Context context) {
        ArrayList<String> emojiList = new ArrayList<>();
        try {
            NSArray nsArray = (NSArray) PropertyListParser.parse(context.getAssets().open("Emoji.plist"));
            int count = nsArray.count();
            for (int i = 0; i < count; i++) {
                emojiList.add(nsArray.objectAtIndex(i).toJavaObject().toString());
            }
            return emojiList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return emojiList;
    }
}

package com.shirwa.simplistic_rss;

/*
 * Copyright (C) 2014 Shirwa Mohamed <shirwa99@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssItem implements Comparable<RssItem> {
    // Format in RSS : Sat, 18 Apr 2015 05:58:00 +0200
    public static String XML_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
    String title;
    String description;
    String link;
    String imageUrl;
    String pubDate;
    String mp3;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    @Override
    public int compareTo(RssItem item) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(XML_DATE_FORMAT, Locale.ENGLISH);
        try {
            Date dateToCompare = dateFormat.parse(item.getPubDate());
            Date date = dateFormat.parse(this.getPubDate());
            return Long.signum(dateToCompare.getTime() - date.getTime());

        } catch (ParseException e) {
            Log.d("RSS", "date1 "+getPubDate());
            Log.d("RSS", "date2 "+item.getPubDate());
            //e.printStackTrace();
        }

        return 0;
    }
}

package com.shirwa.simplistic_rss;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

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


public class RssHandler extends DefaultHandler {
    private RssReader rssReader;
    private List<RssItem> rssItemList;
    private RssItem currentItem;
    private boolean parsingTitle;
    private boolean parsingPubDate;
    private boolean parsingLink;
    private boolean parsingDescription;
    private boolean parsingImage, parsingImageUrl;

    public RssHandler() {
        //Initializes a new ArrayList that will hold all the generated RSS items.
        rssItemList = new ArrayList<RssItem>();
    }

    public List<RssItem> getRssItemList() {
        return rssItemList;
    }

    public RssReader getRssReader() {
        return rssReader;
    }

    /**
     * Called when an opening tag is reached, such as <item> or <title>
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("image")) {
            rssReader = new RssReader();
            parsingImage = true;
        }
        else if (qName.equals("url")&&parsingImage) {
            parsingImageUrl = true;
        }
        else if (qName.equals("item"))
            currentItem = new RssItem();
        else if (qName.equals("title"))
            parsingTitle = true;
        else if (qName.equals("link"))
            parsingLink = true;
        else if (qName.equals("description"))
            parsingDescription = true;
        else if (qName.equals("pubDate")){
            parsingPubDate = true;
        }
        else if (qName.equals("media:thumbnail") ||
                qName.equals("media:content") ||
                qName.equals("image") ||
                (qName.equals("enclosure")&&attributes.getValue("type").contains("image"))
                ) {
            if (attributes.getValue("url") != null)
                currentItem.setImageUrl(attributes.getValue("url"));
        } else if (qName.equals("enclosure")&&attributes.getValue("type").contains("audio")){
            currentItem.setMp3(attributes.getValue("url"));
        }
    }

    /**
     * Called when a closing tag is reached, such as </item> or </title>
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("image")&&parsingImage){
            parsingImage = false;
        }
        if (qName.equals("url")&&parsingImageUrl){
            parsingImageUrl = false;
        }
        if (qName.equals("item")) {
            //End of an item so add the currentItem to the list of items.
            rssItemList.add(currentItem);
            currentItem = null;
        } else if (qName.equals("title"))
            parsingTitle = false;
        else if (qName.equals("link"))
            parsingLink = false;
        else if (qName.equals("description"))
            parsingDescription = false;
        else if (qName.equals("pubDate")){
            parsingPubDate = false;
        }
    }

    /**
     * Goes through character by character when parsing whats inside of a tag.
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentItem != null) {
            //If parsingTitle is true, then that means we are inside a <title> tag so the text is the title of an item.
            if (parsingTitle) {
                currentItem.setTitle(fillField(currentItem.getTitle(), new String(ch, start, length)));
            }
            //If parsingLink is true, then that means we are inside a <link> tag so the text is the link of an item.
            else if (parsingLink){
                currentItem.setLink(fillField(currentItem.getLink(), new String(ch, start, length)));
            }
            //If parsingDescription is true, then that means we are inside a <description> tag so the text is the description of an item.
            else if (parsingDescription) {
                currentItem.setDescription(fillField(currentItem.getDescription(), new String(ch, start, length)));
            }
            else if (parsingPubDate) {
                currentItem.setPubDate(fillField(currentItem.getPubDate(), new String(ch, start, length)));
            }
        }

        if (rssReader != null && parsingImageUrl){
            rssReader.setRssImg(fillField(rssReader.getRssImg(), new String(ch, start, length)));
        }
    }

    /**
     * Avoid the "split" string effect from SAX
     *
     * @param currentText
     * @param newText
     * @return the complete string
     */
    private String fillField(String currentText, String newText){
        if (currentText != null){
            return currentText + newText;
        } else {
            return newText;
        }
    }
}



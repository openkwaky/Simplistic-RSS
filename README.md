Simplistic-RSS
==============

This is a fork of a very simple RSS library for Android. Supports extracting the url of images if they are present. 
It's still very early, but it will continue to get better with updates.

[Sample App](https://github.com/ShirwaM/Simplistic-RSS-Demo)
![Screenshot](https://raw.githubusercontent.com/ShirwaM/Simplistic-RSS/master/Screenshot_2014-08-07-03-47-10_framed.png)

Fork
==============

What's updated from original version ?

* Add build.gradle file for easy gradle integration
* Add RssHandler.appendField() method, to fix the split values bug
* Add pubDate and mp3 fields in RssItem. mp3 field is useful for podcasts
* Add a sortList() method that sorts items by pubDate (more recent first)
* Add rssImg field in RssReader class to get Rss image url
* Move parsing job in constructor


Usage
==============
Create an RssReader by passing in the url of the RSS feed, the parsing is automatically executed
```Java
RssReader rssReader = new RssReader(url);
```


Get a list of all the items that have been extracted from the Rss feed
```Java
ArrayList<RssItem> RssItems = rssReader.getItems();
```

Get the RSS picture url
```Java
String imgUrl = rssReader.getRssImg();
```

Customization
==============
*If you want to add a new attribute to the Rss item, edit the RssItem class to contain getters/setters for the specific attribue. 

*Then follow basic outline in the RssHandler class. 

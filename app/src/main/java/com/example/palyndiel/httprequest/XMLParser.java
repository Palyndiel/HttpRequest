package com.example.palyndiel.httprequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by maxym on 31/01/2017.
 */
public class XMLParser {

    protected ArrayList<Video> Parser (String inputXML) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        xmlFactoryObject.setNamespaceAware(true);
        XmlPullParser myParser = xmlFactoryObject.newPullParser();
        ArrayList<Video> out=new ArrayList();
        Video vid;
        myParser.setInput(new StringReader(inputXML));


        int event = myParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT)  {
            String name=myParser.getName();
            switch (event){
                case XmlPullParser.START_TAG:
                    break;

                case XmlPullParser.END_TAG:
                    if(name.equals("video")){
                        vid = new Video();
                        vid.setID(Integer.parseInt(myParser.getAttributeValue(null,"id")));
                        vid.setTitle(myParser.getAttributeValue(null,"title"));

                        out.add(vid);

                    }
                    break;
            }
            event = myParser.next();
        }
        return out;
    }
}

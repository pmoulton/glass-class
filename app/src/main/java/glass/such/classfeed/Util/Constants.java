package glass.such.classfeed.Util;

/**
 * Created by vincente on 6/20/14.
 */
public class Constants {
    public class JSON{
        public static final String TYPE = "type";
        public static final String DATA = "data";
    }

    public class Debug{
        public static final boolean ENABLED = true;
        public static final String WSURI    = "ws://192.168.43.129:8080/";
    }

    public class Test{
        public static final String NOTEPAYLOAD = "{\"type\": \"Notes\",\"data\": {\"text\": \"Neutra ennui tattooed PBR Vice. Kitsch\",\"urls\": [\"http://en.wikipedia.org/wiki/differential_equations\",\"http://en.wikipedia.org/wiki/Hello_world\"],\"images\":[{\"title\":\"windodge\",\"url\":\"http://ilikewalls.com/wallpaper/13/11/windoge-wallpaper-7.png\"},{\"title\": \"Wall Street Dodge\",\"url\": \"http://i.imgur.com/O930izT.jpg\"}]}}";
        public static final String QUIZPAYLOAD = "{\"type\":\"quiz\",\"data\":{\"uuid\":\"SomeUUIDHere\",\"text\":\"Quiz Title goes here\",\"questions\":[{\"question\":\"Why is the sky blue?\",\"possible\":[{\"text\":\"Dodge of Course!\"},{\"text\":\"Because of the Ocean\"},{\"text\":\"Floor the glory of Satan of course!\"}],\"answer\": 1}]}}";
    }
}

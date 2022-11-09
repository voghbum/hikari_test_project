package tr.gov.bilgem.tubitak.arch.properties;

import java.io.IOException;
import java.util.Properties;

public class Dbcp2Prop extends CommonDbProp {
    private final Properties dbcp2Prop;

    private void parse() {

    }

    public Dbcp2Prop(Properties commonProp, Properties dbcp2Prop) {
        super(commonProp);
        this.dbcp2Prop = dbcp2Prop;
    }

    public Dbcp2Prop() {
        super();
        dbcp2Prop = new Properties();

        try {
            dbcp2Prop.load(ClassLoader.getSystemResourceAsStream("dbcp2prop.properties"));
        } catch (IOException e) {
            throw new RuntimeException("hikaridbprop.properties cannot found or load", e);
        }
    }
}

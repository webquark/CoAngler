package kr.co.cyberdesic.coangler.model;

import java.util.ArrayList;

public class Facility extends ModelBase {

    public String no;
    public String name;
    public String alias;
    public String fac_type;
    public String fac_code;
    public String weight;
    public String fishing;
    public String is_hot;
    public String region_no;
    public String addr;
    public String latitude;
    public String longitude;
    public String area;
    public ArrayList<WaterLevel> level;
    public String last_level;
    public String last_rate;
    public String last_date;

    public Facility() {

    }

    public class WaterLevel {
        public String date;
        public String level;
        public String rate;
    }
}

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
    public String sdate;
    public String edate;
    public ArrayList<WaterLevel> level;
    public String last_date;
    public String last_level;
    public String last_rate;

    public class WaterLevel {
        public String date;
        public String level;
        public String rate;
    }

    public Facility() {

    }

    public String getDate(int index) {
        if (this.level == null) return "";

        if (this.level.get(index) != null) {
            return this.level.get(index).date;
        } else {
            return "";
        }
    }

    public String getLevel(int index) {
        if (this.level == null) return "";

        if (this.level.get(index) != null) {
            return this.level.get(index).level;
        } else {
            return "";
        }
    }

    public String getRate(int index) {
        if (this.level == null) return "";

        if (this.level.get(index) != null) {
            return this.level.get(index).rate;
        } else {
            return "";
        }
    }
}

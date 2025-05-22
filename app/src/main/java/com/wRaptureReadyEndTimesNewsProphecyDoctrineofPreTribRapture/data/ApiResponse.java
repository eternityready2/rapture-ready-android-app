package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data;

import java.util.List;

public class ApiResponse {
    public boolean status;
    public String message;
    public Data data;

    public static class Data {
        public List<Section> sections;
        public List<ButtonItem> bottomNav;
        public List<ButtonItem> more;
    }

    public static class Section {
        public String id;
        public String title;
        public List<ButtonItem> buttons;
    }
}

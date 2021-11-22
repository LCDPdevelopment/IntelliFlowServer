package org.acme.model;

import java.util.ArrayList;

public class InputFileData {
    private String xml;
    private String fileName;
        private String appName;
    public String getAppName() {
            return appName;
        }
        public void setAppName(String appName) {
            this.appName = appName;
        }
        public ArrayList<String> getForElements() {
            return forElements;
        }
        public void setForElements(ArrayList<String> forElements) {
            this.forElements = forElements;
        }
    private ArrayList<String> forElements;
    public String getXml() {
        return xml;
    }
    public void setXml(String xml) {
        this.xml = xml;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}

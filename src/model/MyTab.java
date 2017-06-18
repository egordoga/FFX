package model;

import javafx.scene.control.Tab;

import java.io.File;

/**
 * Created by Master on 18.06.2017.
 */
public class MyTab {
    private Tab tab;
    private File dir;

    public MyTab() {
    }

    public MyTab(Tab tab, File dir) {
        this.tab = tab;
        this.dir = dir;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }
}

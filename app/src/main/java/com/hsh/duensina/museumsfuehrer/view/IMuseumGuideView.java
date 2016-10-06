package com.hsh.duensina.museumsfuehrer.view;

/**
 * Created by alex on 02.08.16.
 */
public interface IMuseumGuideView {
    void closeView();
    void saveStatus();
    void restoreStatus();
    boolean hasStarted();
}

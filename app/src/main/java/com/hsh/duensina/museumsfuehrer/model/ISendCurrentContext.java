package com.hsh.duensina.museumsfuehrer.model;

import org.altbeacon.beacon.Beacon;

/**
 * Created by alex on 11.07.16.
 */
public interface ISendCurrentContext {

    void sendCurrentContext();

    String getCurConAsJSON(Beacon b);
}

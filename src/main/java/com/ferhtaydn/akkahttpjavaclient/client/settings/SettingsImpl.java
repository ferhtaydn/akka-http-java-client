package com.ferhtaydn.akkahttpjavaclient.client.settings;

import akka.actor.Extension;
import com.typesafe.config.Config;

public class SettingsImpl implements Extension {

    public final String URI;
    public final String HOST;
    public final int PORT;

    public SettingsImpl(Config config) {
        URI = config.getString("http.uri");
        HOST = config.getString("http.host");
        PORT = config.getInt("http.port");
    }

}

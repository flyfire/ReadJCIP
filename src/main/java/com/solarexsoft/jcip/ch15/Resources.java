package com.solarexsoft.jcip.ch15;

/**
 * Created by houruhou on 2019/8/11.
 * Desc:
 */
public class Resources {
    private Resources(){}

    private static class ResourceHolder {
        public static Resources instance = new Resources();
    }

    public static Resources getResources() {
        return ResourceHolder.instance;
    }
}

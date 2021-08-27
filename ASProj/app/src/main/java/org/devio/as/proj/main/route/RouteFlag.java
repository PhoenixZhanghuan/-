package org.devio.as.proj.main.route;

public interface RouteFlag {
    int FlAG_LOGIN = 0x01;
    int FLAG_AUTHENTICATION = FlAG_LOGIN << 1;
    int FLAG_VIP = FLAG_AUTHENTICATION << 1;
}

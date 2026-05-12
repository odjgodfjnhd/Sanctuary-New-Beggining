package com.sanctuary.world;

public interface MapProvider {

    WorldMap loadMap(String mapId);

    void loadIntoWorld(WorldMap worldMap);
}
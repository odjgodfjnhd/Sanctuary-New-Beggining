package com.sanctuary.world;

import com.almasb.fxgl.dsl.FXGL;
import com.sanctuary.config.AssetPaths;
import com.sanctuary.config.GameConfig;
import com.sanctuary.config.GameConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.logging.Logger;

public class TiledMapProvider implements MapProvider {

    private static final Logger LOGGER = Logger.getLogger(TiledMapProvider.class.getName());

    @Override
    public WorldMap loadMap(String mapId) {
        String mapFileName = mapId + AssetPaths.TMX_EXTENSION;
        String resourcePath = AssetPaths.LEVELS_RESOURCE_DIR + mapFileName;

        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("TMX resource not found: " + resourcePath);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setNamespaceAware(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            document.getDocumentElement().normalize();

            Element mapElement = document.getDocumentElement();

            int widthInTiles = parseIntOrDefault(
                    mapElement.getAttribute("width"),
                    0
            );
            int heightInTiles = parseIntOrDefault(
                    mapElement.getAttribute("height"),
                    0
            );
            int tileWidth = parseIntOrDefault(
                    mapElement.getAttribute("tilewidth"),
                    GameConstants.TILE_SIZE
            );
            int tileHeight = parseIntOrDefault(
                    mapElement.getAttribute("tileheight"),
                    GameConstants.TILE_SIZE
            );

            WorldMap worldMap = new WorldMap(
                    mapId,
                    mapFileName,
                    widthInTiles,
                    heightInTiles,
                    tileWidth,
                    tileHeight
            );

            loadSpawnPointsFromTiled(worldMap, mapElement);
            ensureDefaultSpawnExists(worldMap);

            return worldMap;

        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse TMX map: " + mapId, e);
        }
    }

    @Override
    public void loadIntoWorld(WorldMap worldMap) {
        FXGL.setLevelFromMap(worldMap.getMapFileName());
    }

    private void loadSpawnPointsFromTiled(WorldMap worldMap, Element mapElement) {
        for (Node layerNode = mapElement.getFirstChild();
             layerNode != null;
             layerNode = layerNode.getNextSibling()) {

            if (!(layerNode instanceof Element layerElement)) {
                continue;
            }

            if (!"objectgroup".equals(layerElement.getTagName())) {
                continue;
            }

            parseObjectGroup(worldMap, layerElement);
        }
    }

    private void parseObjectGroup(WorldMap worldMap, Element objectGroupElement) {
        for (Node objectNode = objectGroupElement.getFirstChild();
             objectNode != null;
             objectNode = objectNode.getNextSibling()) {

            if (!(objectNode instanceof Element objectElement)) {
                continue;
            }

            if (!"object".equals(objectElement.getTagName())) {
                continue;
            }

            parseObject(worldMap, objectElement);
        }
    }

    private void parseObject(WorldMap worldMap, Element objectElement) {
        String typeValue = firstNonBlank(
                objectElement.getAttribute("type"),
                objectElement.getAttribute("class")
        );

        TileObjectType objectType = TileObjectType.fromString(typeValue);

        if (objectType != TileObjectType.PLAYER_SPAWN) {
            return;
        }

        String spawnId = firstNonBlank(
                objectElement.getAttribute("name"),
                GameConfig.DEFAULT_SPAWN_ID
        );

        double x = parseDoubleOrDefault(
                objectElement.getAttribute("x"),
                GameConstants.SPAWN_FALLBACK_X
        );

        double y = parseDoubleOrDefault(
                objectElement.getAttribute("y"),
                GameConstants.SPAWN_FALLBACK_Y
        );

        worldMap.addSpawnPoint(new SpawnPoint(spawnId, x, y));

        LOGGER.info(() ->
                "Loaded spawn point from Tiled: id="
                        + spawnId
                        + ", x="
                        + x
                        + ", y="
                        + y
        );
    }

    private void ensureDefaultSpawnExists(WorldMap worldMap) {
        if (worldMap.getSpawnPoint(GameConfig.DEFAULT_SPAWN_ID) == null) {
            worldMap.addSpawnPoint(new SpawnPoint(
                    GameConfig.DEFAULT_SPAWN_ID,
                    GameConstants.SPAWN_FALLBACK_X,
                    GameConstants.SPAWN_FALLBACK_Y
            ));

            LOGGER.warning(() ->
                    "Default spawn was not found in Tiled. Using fallback: "
                            + GameConstants.SPAWN_FALLBACK_X
                            + ", "
                            + GameConstants.SPAWN_FALLBACK_Y
            );
        }
    }

    private String firstNonBlank(String first, String second) {
        if (first != null && !first.isBlank()) {
            return first;
        }

        return second;
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
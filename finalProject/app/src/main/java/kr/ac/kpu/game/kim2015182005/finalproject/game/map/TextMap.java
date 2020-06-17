package kr.ac.kpu.game.kim2015182005.finalproject.game.map;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameWorld;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Boss;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.CandyItem;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Enemy;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Platform;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

public class TextMap {
    private final int blockSize;
    private final GameWorld gameWorld;
    private final int createAtX;
    private int currentX;
    private int mapIndex;
    private int columns, rows;
    ArrayList<String> lines;
    private double timeElapsed;

    public TextMap(String assetFilename, GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        AssetManager assets = UiBridge.getActivity().getAssets();
        try {
            InputStream is = assets.open(assetFilename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String header = reader.readLine();
            String[] comps = header.split(" ");
            columns = Integer.parseInt(comps[0]);
            rows = Integer.parseInt(comps[1]);
            lines = new ArrayList<>();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        blockSize = UiBridge.metrics.size.y / rows;
        createAtX = UiBridge.metrics.size.x + 2 * blockSize;

        reset();
    }

    private void createColumn() {
        float y = blockSize / 2;
        for (int row = 0; row < rows; row++) {
            char ch = getAt(mapIndex, row);
            createObject(ch, currentX, y);
            y += blockSize;
        }
        currentX += blockSize;
        mapIndex++;
    }

    private void createObject(char ch, float x, float y) {
        MainScene.Layer layer = MainScene.Layer.item;
        GameObject obj = null;
        switch (ch) {
            case '1': case '2': case '3': case '4':
                layer = MainScene.Layer.item;
                obj = CandyItem.get(x, y, blockSize, blockSize, ch - '1');
                break;
            case 'O': case 'P': case 'Q':
                layer = MainScene.Layer.platform;
                obj = Platform.get(x, y, blockSize, ch - 'O');
                break;
            case 'x':
                layer = MainScene.Layer.obstacle;
                obj = new AnimObject(x + 3 * blockSize / 2, y + 3 * blockSize / 2, 3 * blockSize, 3 * blockSize, R.mipmap.fireball_128_24f, 2, 0);
                break;
            case '5':
                layer = MainScene.Layer.enemy;
                obj = Enemy.get(x, y-blockSize, blockSize*2, blockSize*3);
                break;
            case '6':
                layer = MainScene.Layer.enemy;
                obj = Boss.get(x, y-blockSize, blockSize*2, blockSize*3);
                break;
        }
        if (obj != null) {
            gameWorld.add(layer.ordinal(), obj);
        }
    }

    private char getAt(int index, int row) {
        try {
            int line = index / columns * rows + row;
            return lines.get(line).charAt(index % columns);
        } catch (Exception e) {
            return 0;
        }
    }

    public void update(float dx) {
        timeElapsed += GameTimer.getTimeDiffSeconds();
        currentX += dx;
        if (currentX < createAtX) {
            createColumn();
        }
    }
    public void reset() {
        mapIndex = 0;

        currentX = 0;
        while (currentX <= createAtX) {
            createColumn();
        }
    }
}

package inso.activity.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elisabeth on 31.10.2015.
 */
public class PositionStore {
    private static PositionStore positionStore;

    private volatile Map<Integer, Integer> positionMapping = null;

    private PositionStore() {
        positionMapping = new HashMap<Integer, Integer>();
    }

    public static synchronized PositionStore getInstance() {
        if (positionStore == null) {
            positionStore = new PositionStore();
        }
        return positionStore;
    }

    public synchronized void addProductionLine(int index) {
        positionMapping.put(index, 0);
    }

    public synchronized void addProductionLine(int index, int count) {
        positionMapping.put(index, count);
    }

    public synchronized void addCountOnIndex(int index) {
        Integer count = positionMapping.get(index);
        positionMapping.put(index, count++);
    }

    public synchronized int getPosition(int index) {
        int pos = 0;
        for(Integer key : positionMapping.keySet()) {
            if(key < index) {
                pos += 1 + positionMapping.get(key);
            }
        }
        return pos;
    }

    public synchronized int getPositionMinus(int index) {
        int pos = 0;
        for(Integer key : positionMapping.keySet()) {
            if(key < index) {
                pos += 1 + positionMapping.get(key);
            }
        }
        return pos;
    }

    public synchronized void clear() {
        positionMapping.clear();
    }
}

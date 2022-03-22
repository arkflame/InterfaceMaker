package dev._2lstudios.interfacemaker.interfaces.holders;

import dev._2lstudios.interfacemaker.interfaces.InterfaceItem;

public class InterfaceInventoryHolder extends InterfaceItemHolder {
    private int size;

    public InterfaceInventoryHolder(int inventorySize) {
        this.size = inventorySize;
    }

    public InterfaceInventoryHolder setRows(int rows) {
        this.size = rows * 9;
        return this;
    }

    public int getSize() {
        return size;
    }

    public InterfaceInventoryHolder fill(int gap, InterfaceItem ...items) {
        int firstSlot = gap * 8 + gap * 2;
        int itemIndex = 1;

        for (int slot = firstSlot; slot < size; slot++) {
            if (itemIndex - 1 >= items.length) {
                break;
            }

            setItem(slot, items[itemIndex - 1]);

            if (itemIndex % (9 - gap * 2) == 0) {
                slot += (gap * 2);
            }

            itemIndex++;
        }
        return this;
    }

    public InterfaceInventoryHolder fillEmpty(InterfaceItem item) {
        for (int i = 0; i < size; i++) {
            if (!hasItem(i)) {
                setItem(i, item);
            }
        }
        return this;
    }
}

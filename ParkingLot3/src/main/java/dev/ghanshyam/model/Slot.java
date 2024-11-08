package dev.ghanshyam.model;

import dev.ghanshyam.model.enums.SlotType;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Slot {
    private static int globalSlotId = 1;
    protected int slotId;
    protected int fixedPrice;
    @Setter
    protected Vehicle vehicle;
    @Setter
    protected Token token;

    public Slot(int fixedPrice) {
        this.slotId = globalSlotId++;
        this.fixedPrice = fixedPrice;
    }
}

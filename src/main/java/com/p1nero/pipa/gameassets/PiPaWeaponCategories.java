package com.p1nero.pipa.gameassets;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum PiPaWeaponCategories implements WeaponCategory {
    PI_PA;
    private PiPaWeaponCategories(){
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }
    final int id;
    @Override
    public int universalOrdinal() {
        return this.id;
    }
}

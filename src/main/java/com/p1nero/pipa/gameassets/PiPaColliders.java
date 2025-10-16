package com.p1nero.pipa.gameassets;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

public class PiPaColliders {
    public static final Collider PI_PA = new MultiOBBCollider(4, 0.3, 0.3, 0.9, 0.0, 0.0, -0.8);
    public static final Collider PI_PA_PLUS = new MultiOBBCollider(4, 0.3, 0.3, 1.2, 0.0, 0.0, -0.8);

}

package io.github.redwallhp.scottysigns;


import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Transporter implements ConfigurationSerializable {


    public String signWorld;
    public String targetWorld;
    public Integer signX;
    public Integer signY;
    public Integer signZ;
    public Integer targetX;
    public Integer targetY;
    public Integer targetZ;


    public Transporter(Block signBlock, String targetWorld, Integer targetX, Integer targetY, Integer targetZ) {
        this.signWorld = signBlock.getWorld().getName();
        this.targetWorld = targetWorld;
        this.signX = signBlock.getX();
        this.signY = signBlock.getY();
        this.signZ = signBlock.getZ();
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("signWorld", this.signWorld);
        map.put("targetWorld", this.targetWorld);
        map.put("signX", this.signX);
        map.put("signY", this.signY);
        map.put("signZ", this.signZ);
        map.put("targetX", this.targetX);
        map.put("targetY", this.targetY);
        map.put("targetZ", this.targetZ);
        return map;
    }

}

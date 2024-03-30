package com.void01.bukkit.jerm.core.animation.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class AnimationDataPojo {
    @SerializedName("animations")
    private Map<String, Animation> animations;

    public Map<String, Animation> getAnimations() {
        return animations;
    }

    public static class Animation {
        @SerializedName("animation_length")
        private double animationLength;

        public double getAnimationLength() {
            return animationLength;
        }
    }
}

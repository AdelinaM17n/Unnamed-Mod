package net.fabricmc.example.mixin;

import net.fabricmc.example.IGameOptions;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.GameOptions;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.input.Keyboard;

@Mixin(GameOptions.class)
public class GameOptionsMixin implements IGameOptions {

    @Shadow
    @Mutable
    public KeyBinding[] keysAll;

    public KeyBinding keyCutefulModMenu;

    @Inject(
            method = "load",
            at = @At(
                    value = "HEAD"
            )
    )
    private void onLoadInjectAtHead(CallbackInfo ci) {
        keyCutefulModMenu = new KeyBinding("key.cutefull",Keyboard.KEY_F7, "CutefulMod");
        keysAll = ArrayUtils.add(keysAll, keyCutefulModMenu);
    }

    @Override
    public KeyBinding getCutefulModMenu() {
        return keyCutefulModMenu;
    }
}
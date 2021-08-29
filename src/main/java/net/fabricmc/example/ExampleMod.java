package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.config.Configs;
import net.minecraft.server.command.GameRuleCommand;
import net.minecraft.world.GameRuleManager;

import java.io.IOException;

public class ExampleMod implements ModInitializer {



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		try {
			new Configs();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Hello Fabric world!");
	}
}

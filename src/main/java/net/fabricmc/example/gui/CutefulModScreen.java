package net.fabricmc.example.gui;

import net.fabricmc.example.config.Config;
import net.fabricmc.example.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.gui.widget.OptionPairWidget;
import net.minecraft.text.LiteralText;

import java.io.IOException;

public class CutefulModScreen extends Screen {

    private EntryListWidget list;
    private final Configs configs;
    protected String title = "Video Settings";

    public CutefulModScreen() {
        //super(new LiteralText("CutefulMod Options"));
        super();
        configs = Configs.getInstance();
    }

    @Override
    public void init() {
        //assert this.client != null;
        super.init(client, width, height);
        list = new OptionPairWidget(client, width, height, 32, this.height - 32, 25);

        int heightOfButton = 40;
        int widthOfButton = 170;
        /*for (Config config : configs.allConfigs) {
            this.buttons.add(new ButtonWidget(this.width / 2 - widthOfButton / 2, heightOfButton, widthOfButton, 20, new LiteralText(config.name + " : " + config.value), (buttonWidget) -> {
                config.value = !config.value;
                client.openScreen(CutefulModScreen());
            }));
            heightOfButton += 24;
        }
        this.buttons.add(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, new LiteralText("Done"), (buttonWidget) -> {
            this.client.setScreen(null);
        }));
        this.buttons.add(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, new LiteralText("Reset config"), (buttonWidget) -> {
            for (Config config : configs.allConfigs) {
                {
                    config.value = false;
                }
            }
            this.client.setScreen(new CutefulModScreen());
        })); */


    }

    @Override
    public void removed() {
        super.removed();
        try {
            configs.saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(int mousex, int mousey, float delta) {
        this.renderBackground(0);
        this.list.render(mousex, mousey, delta);
        assert client != null;
        this.drawCenteredString(client.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(mousex, mousey, delta);
    }


    public boolean shouldPauseGame() {return false;}
}

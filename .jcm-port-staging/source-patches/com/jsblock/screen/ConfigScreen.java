package com.jsblock.screen;

import com.jsblock.Joban;
import com.jsblock.client.ClientConfig;
import com.jsblock.data.InlineComponentEntry;
import com.jsblock.data.ScreenAlignment;
import com.jsblock.data.ScreenRoot;
import com.jsblock.data.TextLabel;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import mtr.data.IGui;
import mtr.mappings.Text;
import mtr.mappings.UtilitiesClient;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.SystemToast.SystemToastId;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends ConfigScreenBase implements IGui {
   private boolean enableRendering;
   private boolean ignoreVerCheck;
   private boolean debugMode;
   private boolean initalized;
   private static final Component TITLE_TEXT = Text.translatable("gui.jsblock.brand", new Object[0]);
   private static final Component VERSION_TEXT = Text.translatable("gui.jsblock.version", new Object[]{Joban.getVersion()});

   public ConfigScreen() {
      super(new ScreenRoot(ScreenAlignment.HORZ_CENTER, 1.25F, 1.0F), new TextLabel(TITLE_TEXT, 2.0F, ScreenAlignment.HORZ_CENTER, 6), new TextLabel(VERSION_TEXT, 1.0F, ScreenAlignment.HORZ_CENTER, 6));
   }

   protected void m_7856_() {
      if (!this.initalized) {
         this.initalized = true;
         this.enableRendering = ClientConfig.getRenderDisabled();
         this.ignoreVerCheck = ClientConfig.getVersionCheckDisabled();
         this.debugMode = ClientConfig.getDebugModeEnabled();
         this.registerConfigRowButton(Text.translatable("gui.jsblock.config.enable_render", new Object[0]), getBooleanButtonText(this.enableRendering), (button) -> {
            this.enableRendering = ClientConfig.setRenderDisabled(!this.enableRendering);
            button.m_93666_(getBooleanButtonText(this.enableRendering));
         });
         this.registerConfigRowButton(Text.translatable("gui.jsblock.config.ignore_ver_check", new Object[0]), getBooleanButtonText(this.ignoreVerCheck), (button) -> {
            this.ignoreVerCheck = ClientConfig.setVersionCheckDisabled(!this.ignoreVerCheck);
            button.m_93666_(getBooleanButtonText(this.ignoreVerCheck));
         });
         this.registerConfigRowButton(Text.translatable("gui.jsblock.config.debug_mode", new Object[0]), getBooleanButtonText(this.debugMode), (button) -> {
            this.debugMode = ClientConfig.setDebugMode(!this.debugMode);
            button.m_93666_(getBooleanButtonText(this.debugMode));
         });
         Button saveButton = UtilitiesClient.newButton(Text.translatable("gui.jsblock.config.save", new Object[0]), (button1) -> {
            this.closeScreen(true);
         });
         Button discardButton = UtilitiesClient.newButton(Text.translatable("gui.jsblock.config.discard", new Object[0]), (button1) -> {
            this.closeScreen(false);
         });
         Button resetButton = UtilitiesClient.newButton(Text.translatable("gui.jsblock.config.reset", new Object[0]), (button1) -> {
            ClientConfig.setRenderDisabled(false);
            ClientConfig.setVersionCheckDisabled(false);
            ClientConfig.setDebugMode(false);
            this.closeScreen(true);
         });
         Button latestLogButton = UtilitiesClient.newButton(Text.translatable("gui.jsblock.config.openlog", new Object[0]), (button1) -> {
            this.showLog();
         });
         Button crashLogButton = UtilitiesClient.newButton(Text.translatable("gui.jsblock.config.opencrashlog", new Object[0]), (button1) -> {
            this.showCrashLog();
         });
         InlineComponentEntry inlineRowConfig = new InlineComponentEntry(ScreenAlignment.HORZ_CENTER, ScreenAlignment.VERT_BOTTOM, new AbstractWidget[]{saveButton, discardButton, resetButton});
         InlineComponentEntry inlineRowLogs = new InlineComponentEntry(ScreenAlignment.HORZ_CENTER, ScreenAlignment.VERT_BOTTOM, new AbstractWidget[]{latestLogButton, crashLogButton});
         this.registerInlineRow(new InlineComponentEntry[]{inlineRowLogs, inlineRowConfig});
      }

      super.m_7856_();
   }

   private void showLog() {
      File latestLog = Paths.get(this.f_96541_.f_91069_.toString(), "logs", "latest.log").toFile();
      if (latestLog.exists()) {
         Util.m_137581_().m_137644_(latestLog);
      }

   }

   private void showCrashLog() {
      Path logDir = Paths.get(this.f_96541_.f_91069_.toString(), "crash-reports");
      if (Files.exists(logDir, new LinkOption[0])) {
         File[] crashLogList = logDir.toFile().listFiles();
         if (crashLogList != null && crashLogList.length > 0) {
            Arrays.sort(crashLogList, Comparator.comparingLong(File::lastModified));
            Util.m_137581_().m_137644_(crashLogList[0]);
         } else {
            SystemToast errorToast = SystemToast.multiline(this.f_96541_, SystemToastId.TUTORIAL_HINT, Text.translatable("gui.jsblock.brand", new Object[0]), Text.translatable("gui.jsblock.config.nocrashlogfound", new Object[0]));
            this.f_96541_.m_91300_().m_94922_(errorToast);
         }
      }

   }

   public void onSave() {
      try {
         ClientConfig.writeConfig();
      } catch (Exception var3) {
         SystemToast errorToast = SystemToast.multiline(this.f_96541_, SystemToastId.TUTORIAL_HINT, Text.translatable("gui.jsblock.brand", new Object[0]), Text.translatable("gui.jsblock.config.savefailed", new Object[0]));
         this.f_96541_.m_91300_().m_94922_(errorToast);
      }

      super.m_7379_();
   }
}


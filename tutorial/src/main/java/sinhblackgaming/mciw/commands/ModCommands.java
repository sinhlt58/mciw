package sinhblackgaming.mciw.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.capabilities.IMoreMode;
import sinhblackgaming.mciw.capabilities.MoreMode;
import sinhblackgaming.mciw.capabilities.MoreModeProvider;

import java.util.List;

public class ModCommands {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String COMMAND_START = "start";
    public static final String COMMAND_STOP = "stop";
    public static final String COMMAND_STATUS = "status";
    public static final String COMMAND_PAUSE = "pause";
    public static final String COMMAND_UNPAUSE = "unpause";

    public static final String [] MODE_COMMANDS = {
            COMMAND_START,
            COMMAND_STOP,
            COMMAND_STATUS,
            COMMAND_PAUSE,
            COMMAND_UNPAUSE,
    };

    private static final SuggestionProvider<CommandSource> SUGGEST_MODES = (source, builder) -> {
        return ISuggestionProvider.suggest(MoreMode.USED_MODE_NAMES, builder);
    };

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> rootCommand = Commands.literal("sinhblack").requires((p) -> {
            return p.hasPermissionLevel(4);
        });

        for (String modeCommand : MODE_COMMANDS) {
            rootCommand.then(Commands.literal(modeCommand).then(Commands.argument("mode", StringArgumentType.string())
                .suggests(SUGGEST_MODES).executes((p) -> {
                        return updateMode(p.getSource(), modeCommand, StringArgumentType.getString(p, "mode"));
                })));
        }

        dispatcher.register(rootCommand);
    }

    public static int updateMode(CommandSource source, String modeCommand, String mode) {
        ServerWorld world = source.getWorld();
        world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
            updateMode(capMoreMode, source, modeCommand, mode);
        });
        return 1;
    }

    public static int updateMode(IMoreMode capMoreMode, CommandSource source, String modeCommand, String mode) {
        if (!capMoreMode.hasMode(mode)) {
            sendFeedBackMode(source, "no.mode", mode);
            return 1;
        }

        switch (modeCommand) {
            case COMMAND_START:
                if (mode.equals(capMoreMode.getModeAll())) {
                    capMoreMode.startAllModes();
                    sendFeedBackMode(source, "start.all");
                } else {
                    capMoreMode.startMode(mode);
                    sendFeedBackMode(source, "start", mode);
                }
                break;
            case COMMAND_STOP:
                if (mode.equals(capMoreMode.getModeAll())) {
                    capMoreMode.stopAllModes();
                    sendFeedBackMode(source, "stop.all");
                } else {
                    capMoreMode.stopMode(mode);
                    sendFeedBackMode(source, "stop", mode);
                }
                break;
            case COMMAND_PAUSE:
                if (mode.equals(capMoreMode.getModeAll())) {
                    capMoreMode.pauseAllModes();
                    sendFeedBackMode(source, "pause.all");
                } else {
                    capMoreMode.stopMode(mode);
                    sendFeedBackMode(source, "pause", mode);
                }
                break;
            case COMMAND_UNPAUSE:
                if (mode.equals(capMoreMode.getModeAll())) {
                    capMoreMode.unPauseAllModes();
                    sendFeedBackMode(source, "unpause.all");
                } else {
                    capMoreMode.stopMode(mode);
                    sendFeedBackMode(source, "unpause", mode);
                }
                break;
            case COMMAND_STATUS:
                if (mode.equals(capMoreMode.getModeAll())) {
                    List<String> runningModes = capMoreMode.getRunningModeNames();
                    String runningModesStr = String.join(", ", runningModes);
                    if (runningModesStr.isEmpty()){
                        runningModesStr = "...";
                    }
                    sendFeedBackMode(source, "status.all", runningModesStr);
                } else {
                    String status = capMoreMode.checkModeStatus(mode);
                    sendFeedBackMode(source, "status", mode, status);
                }
                break;
            default:
                break;
        }
        return 1;
    }

    public static void sendFeedBackMode(CommandSource source, String concatCommand) {
        sendFeedBackMode(source, concatCommand, "", "");
    }

    public static void sendFeedBackMode(CommandSource source, String concatCommand, String param1) {
        sendFeedBackMode(source, concatCommand, param1, "");
    }

    private static void sendFeedBackMode(CommandSource source, String concatCommand, String param1, String param2) {
        source.sendFeedback(
                new TranslationTextComponent(
                        "commands.sinhblack." + concatCommand, param1, param2
                ), true);
    }
}

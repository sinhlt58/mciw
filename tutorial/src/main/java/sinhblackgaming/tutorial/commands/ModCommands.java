package sinhblackgaming.tutorial.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ModCommands {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String [] MOD_MODES = {"silverfish", "multiply"};

    public static final String [] MODE_COMMANDS = {"start", "status", "stop"};
    private static final SuggestionProvider<CommandSource> SUGGEST_MODES = (source, builder) -> {
        return ISuggestionProvider.suggest(MOD_MODES, builder);
    };

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> rootCommand = Commands.literal("sinhblack").requires((p) -> {
            return p.hasPermissionLevel(3);
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
        source.sendFeedback(new TranslationTextComponent("commands.sinhblack.start"), true);
        LOGGER.info("modeCommand: " + modeCommand + ", mode: " + mode);
        return 1;
    }
}

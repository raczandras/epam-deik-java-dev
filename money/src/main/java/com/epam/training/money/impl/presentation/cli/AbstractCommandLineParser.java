package com.epam.training.money.impl.presentation.cli;

import com.epam.training.money.impl.presentation.cli.command.Command;
import com.epam.training.money.impl.presentation.cli.command.CommandLineParser;
import com.epam.training.money.impl.presentation.cli.command.InvalidCommand;

public abstract class AbstractCommandLineParser implements CommandLineParser {
    private CommandLineParser successor;

    @Override
    public Command createCommand(String commandLine) {
        if (canCreateCommand(commandLine)) {
            return doCreateCommand(commandLine);
        }
        if (successor != null ) {
            return successor.createCommand(commandLine);
        }
        return new InvalidCommand();
    }

    @Override
    public void setSuccessor(CommandLineParser successor) {
        if (this.successor == null) {
            this.successor = successor;
        } else {
            this.successor.setSuccessor(successor);
        }
    }

    protected abstract boolean canCreateCommand(String commandLine);

    protected abstract Command doCreateCommand(String commandLine);
}
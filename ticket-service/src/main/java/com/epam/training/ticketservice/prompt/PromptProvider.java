package com.epam.training.ticketservice.prompt;

import org.jline.utils.AttributedString;

public interface PromptProvider {
    AttributedString getPrompt();
}
package com.sanctuary.dialogue;

import com.sanctuary.config.DialogueConfig;
import com.sanctuary.core.GameService;

import java.util.logging.Logger;

public class DialogueService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(DialogueService.class.getName());

    private final DialogueConfig config;
    private final DialogueSequenceParser sequenceParser;
    private final DialogueTextAnimator textAnimator;

    private DialogueView dialogueView;
    private DialogueSequence currentSequence;
    private DialogueState state = DialogueState.CLOSED;

    public DialogueService(DialogueConfig config) {
        this.config = config;
        this.sequenceParser = new DialogueSequenceParser(config);
        this.textAnimator = new DialogueTextAnimator();
    }

    @Override
    public void initialize() {
        dialogueView = new DialogueView(config);
    }

    @Override
    public void dispose() {
        closeDialogue();
    }

    public void startDialogue(String speakerName, String dialogueText) {
        ensureInitialized();

        if (isDialogueOpen()) {
            advance();
            return;
        }

        currentSequence = sequenceParser.parse(speakerName, dialogueText);

        LOGGER.info(() -> "Starting dialogue with: " + speakerName);

        showCurrentLine();
    }

    public void advance() {
        switch (state) {
            case PRINTING -> skipAnimation();
            case WAITING_FOR_ADVANCE -> moveToNextLineOrClose();
            case CLOSED -> {
                // Nothing
            }
        }
    }

    public boolean isDialogueOpen() {
        return state != DialogueState.CLOSED;
    }

    private void showCurrentLine() {
        DialogueLine line = currentSequence.getCurrentLine();

        dialogueView.show(
                line,
                currentSequence.getCurrentIndex() + 1,
                currentSequence.getLineCount()
        );

        transitionTo(DialogueState.PRINTING);

        textAnimator.start(
                line.text(),
                config.typewriterDelay(),
                dialogueView::updateText,
                () -> transitionTo(DialogueState.WAITING_FOR_ADVANCE)
        );
    }

    private void skipAnimation() {
        textAnimator.completeImmediately(dialogueView::updateText);
        transitionTo(DialogueState.WAITING_FOR_ADVANCE);
    }

    private void moveToNextLineOrClose() {
        if (currentSequence.hasNextLine()) {
            currentSequence.moveToNextLine();
            showCurrentLine();
            return;
        }

        closeDialogue();
    }

    private void closeDialogue() {
        textAnimator.stop();

        if (dialogueView != null) {
            dialogueView.hide();
        }

        currentSequence = null;
        transitionTo(DialogueState.CLOSED);

        LOGGER.info("Dialogue closed");
    }

    private void transitionTo(DialogueState nextState) {
        state = nextState;
    }

    private void ensureInitialized() {
        if (dialogueView == null) {
            throw new IllegalStateException(
                    "DialogueService is not initialized. Call initialize() before using it."
            );
        }
    }
}
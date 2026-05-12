package com.sanctuary.dialogue;

import com.sanctuary.config.DialogueConfig;
import com.sanctuary.core.GameService;

import java.util.logging.Logger;

public class DialogueService implements GameService {

    private static final Logger LOGGER = Logger.getLogger(DialogueService.class.getName());

    private DialogueView dialogueView;
    private final DialogueTextAnimator textAnimator;

    private DialogueSequence currentSequence;
    private DialogueState state = DialogueState.CLOSED;

    public DialogueService() {
        this.textAnimator = new DialogueTextAnimator();
    }

    public void startDialogue(String speakerName, String dialogueText) {
        if (isDialogueOpen()) {
            advance();
            return;
        }

        currentSequence = DialogueSequence.fromText(speakerName, dialogueText);

        LOGGER.info(() -> "Starting dialogue with: " + speakerName);

        showCurrentLine();
    }

    public void advance() {
        if (state == DialogueState.CLOSED || currentSequence == null) {
            return;
        }

        if (state == DialogueState.PRINTING) {
            textAnimator.completeImmediately(getDialogueView()::updateText);
            state = DialogueState.WAITING_FOR_ADVANCE;
            return;
        }

        if (state == DialogueState.WAITING_FOR_ADVANCE) {
            if (currentSequence.hasNextLine()) {
                currentSequence.moveToNextLine();
                showCurrentLine();
            } else {
                closeDialogue();
            }
        }
    }

    public boolean isDialogueOpen() {
        return state != DialogueState.CLOSED;
    }

    @Override
    public void dispose() {
        closeDialogue();
    }

    private void showCurrentLine() {
        DialogueLine line = currentSequence.getCurrentLine();

        DialogueView view = getDialogueView();

        view.show(
                line,
                currentSequence.getCurrentIndex() + 1,
                currentSequence.getLineCount()
        );

        state = DialogueState.PRINTING;

        textAnimator.start(
                line.getText(),
                DialogueConfig.TYPEWRITER_DELAY,
                view::updateText,
                () -> state = DialogueState.WAITING_FOR_ADVANCE
        );
    }

    private DialogueView getDialogueView() {
        if (dialogueView == null) {
            dialogueView = new DialogueView();
        }

        return dialogueView;
    }

    private void closeDialogue() {
        textAnimator.stop();

        if (dialogueView != null) {
            dialogueView.hide();
        }

        currentSequence = null;
        state = DialogueState.CLOSED;

        LOGGER.info("Dialogue closed");
    }
}
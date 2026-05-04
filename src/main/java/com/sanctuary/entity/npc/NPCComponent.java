package com.sanctuary.entity.npc;

import com.almasb.fxgl.entity.component.Component;

public class NPCComponent extends Component {

    private final String name;
    private final String dialogue;

    public NPCComponent(String name, String dialogue) {
        this.name = name;
        this.dialogue = dialogue;
    }

    public String getName() {
        return name;
    }

    public String getDialogue() {
        return dialogue;
    }
}
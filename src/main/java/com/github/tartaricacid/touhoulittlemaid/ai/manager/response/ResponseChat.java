package com.github.tartaricacid.touhoulittlemaid.ai.manager.response;

import org.apache.commons.lang3.StringUtils;

public class ResponseChat {
    public String chatText;
    public String ttsText;

    public ResponseChat(String input) {
        String[] split = input.trim().split("---", 2);
        this.chatText = split[0].replaceAll("---", "").trim();
        this.ttsText = split.length > 1 ? split[1].replaceAll("---", "").trim() : this.chatText;
        if (StringUtils.isBlank(ttsText)) {
            this.ttsText = this.chatText;
        }
        this.ttsText = stripActions(this.ttsText);
    }

    public ResponseChat(String chatText, String ttsText) {
        this.chatText = chatText;
        this.ttsText = stripActions(ttsText);
    }

    /**
     * Fork change: removes *actions* from the text sent to text-to-speech,
     * so the voice never reads them aloud. If nothing but actions remains,
     * falls back to the action words without asterisks, since blank TTS text is treated as an error.
     */
    private static String stripActions(String text) {
        if (text == null) {
            return null;
        }
        String spoken = text.replaceAll("\\*[^*\\n]*\\*", " ")
                .replace("*", "")
                .replaceAll("\\s{2,}", " ")
                .trim();
        if (StringUtils.isBlank(spoken)) {
            return text.replace("*", "").trim();
        }
        return spoken;
    }

    public String getChatText() {
        return chatText;
    }

    public String getTtsText() {
        return ttsText;
    }

    @Override
    public String toString() {
        return "%s---%s".formatted(chatText, ttsText);
    }
}

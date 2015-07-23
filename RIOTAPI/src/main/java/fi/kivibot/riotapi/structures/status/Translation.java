package fi.kivibot.riotapi.structures.status;

import java.util.Objects;

public class Translation {

    private String content;
    private String locale;
    private String updated_at;

    public Translation(String content, String locale, String updated_at) {
        this.content = content;
        this.locale = locale;
        this.updated_at = updated_at;
    }

    public String getContent() {
        return content;
    }

    public String getLocale() {
        return locale;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.content);
        hash = 53 * hash + Objects.hashCode(this.locale);
        hash = 53 * hash + Objects.hashCode(this.updated_at);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Translation other = (Translation) obj;
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.locale, other.locale)) {
            return false;
        }
        if (!Objects.equals(this.updated_at, other.updated_at)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Translation{" + "content=" + content + ", locale=" + locale + ", updated_at=" + updated_at + '}';
    }

}

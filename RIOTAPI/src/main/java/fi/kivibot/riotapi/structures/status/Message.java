package fi.kivibot.riotapi.structures.status;

import java.util.List;
import java.util.Objects;

public class Message {
    
    private String author;
    private String content;
    private String created_at;
    private long id;
    private String severity;
    private List<Translation> translations;
    private String updated_at;

    public Message(String author, String content, String created_at, long id, String severity, List<Translation> translations, String updated_at) {
        this.author = author;
        this.content = content;
        this.created_at = created_at;
        this.id = id;
        this.severity = severity;
        this.translations = translations;
        this.updated_at = updated_at;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public long getId() {
        return id;
    }

    public String getSeverity() {
        return severity;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.author);
        hash = 31 * hash + Objects.hashCode(this.content);
        hash = 31 * hash + Objects.hashCode(this.created_at);
        hash = 31 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 31 * hash + Objects.hashCode(this.severity);
        hash = 31 * hash + Objects.hashCode(this.translations);
        hash = 31 * hash + Objects.hashCode(this.updated_at);
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
        final Message other = (Message) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.created_at, other.created_at)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.severity, other.severity)) {
            return false;
        }
        if (!Objects.equals(this.translations, other.translations)) {
            return false;
        }
        if (!Objects.equals(this.updated_at, other.updated_at)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Message{" + "author=" + author + ", content=" + content + ", created_at=" + created_at + ", id=" + id + ", severity=" + severity + ", translations=" + translations + ", updated_at=" + updated_at + '}';
    }
    
}

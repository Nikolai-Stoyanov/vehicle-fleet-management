package my.project.vehiclefleetmanagement.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class Messages {
@Value("${current-language}")
private String lang;

    @PostConstruct
    protected void init() {
        Locale.setDefault(Locale.forLanguageTag(lang));
    }

    public static String getLocaleMessage(String messageKey) {
        return ResourceBundle.getBundle("i18n/messages", Locale.getDefault())
                .getString(messageKey);
    }
}

package View;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class UserMarkup extends ReplyKeyboardMarkup {

    private List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();

    private final String FIRST_BUTTON = "Хочу мемас";

    UserMarkup()
    {
        this.setResizeKeyboard(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(FIRST_BUTTON);
        keyboardRows.add(keyboardRow);

        this.setKeyboard(keyboardRows);
    }
}

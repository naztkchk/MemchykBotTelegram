package View;

import Controller.PhotoFromVk;
import Model.Mem;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private final static String NAME_OF_BOT = "MemchykBot";
    private final static String BOT_TOKEN = "461379082:AAEfA-5WVb2MdXSpbpgfTqM6wqvV9Lglcsk";

    private UserMarkup userMarkup = new UserMarkup();
    private PhotoFromVk photoFromVk = new PhotoFromVk();

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if(message.getText().equals("/start")){
                sendMsg(message, "Тисни \"Хочу мемас\" щоб отримати порцію сміхоти :)", userMarkup);
        }
        else
            if(message.getText().equals("Хочу мемас")){
                photoFromVk.setPhotos();
                Mem mem = photoFromVk.getMem();
                //System.out.println(mem.getTextToPhoto());

                if(mem.getTextToPhoto() != "null1") {
                    sendMsg(message, mem.getTextToPhoto(), userMarkup);
                }
                sendPhoto(message,mem.getPhotoUrl(), userMarkup);
            }
        else{
                sendMsg(message, "Упсс.. помилка", userMarkup);
            }
    }

    private void sendMsg(Message message, String textOfMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sMsg = new SendMessage();
        sMsg.setChatId(message.getChatId().toString());
        sMsg.setReplyMarkup(replyKeyboardMarkup);
        sMsg.setText(textOfMessage);

        try {
            sendMessage(sMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPhoto(Message message, String photoUrl, ReplyKeyboardMarkup replyKeyboardMarkup){
        SendPhoto sPhoto = new SendPhoto();
        sPhoto.setChatId(message.getChatId().toString());
        sPhoto.setReplyMarkup(replyKeyboardMarkup);
        sPhoto.setPhoto(photoUrl);

        try {
            sendPhoto(sPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return NAME_OF_BOT;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}

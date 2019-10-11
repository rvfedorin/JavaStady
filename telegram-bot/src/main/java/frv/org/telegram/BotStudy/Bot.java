/*
 * Created by Roman V. Fedorin
 */
package frv.org.telegram.BotStudy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 *
 * @author Wolf
 */
public class Bot extends TelegramLongPollingBot {

    private String name;
    private String token;
    private String login;
    private static final String path = "bot-data.properties";

    public static void main(String[] args) {
        ApiContextInitializer.init();

        Properties botConfig = new Properties();
        Bot bot = new Bot();
        try {
            botConfig.load(bot.getClass().getResourceAsStream(path));
            bot.setName(botConfig.getProperty("bot.name"));
            bot.setToken(botConfig.getProperty("bot.token"));
            bot.setLogin(botConfig.getProperty("bot.login"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("token = " + bot.getToken());
        System.out.println("name = " + bot.getName());
        System.out.println("login = " + bot.getLogin());

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    } // ** main()

    @Override
    public String getBotToken() {
        return getToken();
    }

    private void sendMsg(Message message, String text) {
        SendMessage toSendMessage = new SendMessage();
        toSendMessage.enableMarkdown(true);
        toSendMessage.setChatId(message.getChatId());
        toSendMessage.setReplyToMessageId(message.getMessageId());
        toSendMessage.setText(text);
        System.out.println("[<-] " + text);
        
        try {
            setButtons(toSendMessage);
            execute(toSendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Model model = new Model();

        if (message != null && message.hasText()) {
            String text = message.getText();
            System.out.println("[->] " + text);
            switch (text) {
                case "/help":
                    sendMsg(message, "Чем могу помочь?");
                    break;
                case "/settings":
                    sendMsg(message, "Что будем настраивать?");
                    break;
                default:
                    sendMsg(message, Weather.getWeather(message.getText(), model));
            }
        }
    }
    
    public void setButtons(SendMessage message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(Boolean.TRUE);
        replyKeyboardMarkup.setResizeKeyboard(Boolean.TRUE);
        replyKeyboardMarkup.setOneTimeKeyboard(Boolean.FALSE);
        
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyRowFirst = new KeyboardRow();
        keyRowFirst.add(new KeyboardButton("/help"));
        keyRowFirst.add(new KeyboardButton("/settings"));
        
        keyboardRowList.add(keyRowFirst);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String getBotUsername() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

} // ** class Bot

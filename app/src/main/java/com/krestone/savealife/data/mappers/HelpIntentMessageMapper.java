package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.sqlite.MessagesTable;
import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;

import java.util.Map;

public class HelpIntentMessageMapper {

    public HelpIntentMessageModel convert(Map<String, String> messageData) {
        HelpIntentMessageModel message = new HelpIntentMessageModel();
        int messageType = Integer.valueOf(messageData.get(MessagesTable.KEY_MESSAGE_TYPE));

        message.setDistance(Double.valueOf(messageData.get(MessagesTable.KEY_DISTANCE)));
        message.setFirstName(messageData.get(MessagesTable.KEY_FIRST_NAME));
        message.setLastName(messageData.get(MessagesTable.KEY_LAST_NAME));
        message.setPhoneNumber(messageData.get(MessagesTable.KEY_PHONE_NUMBER));
        message.setTime(messageData.get(MessagesTable.KEY_TIME));
        message.setGlobalMessageType(messageType);

        return message;
    }
}

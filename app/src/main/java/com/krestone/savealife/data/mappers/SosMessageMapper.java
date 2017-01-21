package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.sqlite.MessagesTable;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;

import java.util.Map;

public class SosMessageMapper {

    public SosMessageModel convert(Map<String, String> messageData) {
        SosMessageModel message = new SosMessageModel();
        int messageType = Integer.valueOf(messageData.get(MessagesTable.KEY_MESSAGE_TYPE));

        message.setFirstName(messageData.get(MessagesTable.KEY_FIRST_NAME));
        message.setLastName(messageData.get(MessagesTable.KEY_LAST_NAME));
        message.setPhoneNumber(messageData.get(MessagesTable.KEY_PHONE_NUMBER));
        message.setTime(messageData.get(MessagesTable.KEY_TIME));
        message.setMessage(messageData.get(MessagesTable.KEY_MESSAGE_TEXT));
        message.setLatitude(Double.valueOf(messageData.get(MessagesTable.KEY_LATITUDE)));
        message.setLongitude(Double.valueOf(messageData.get(MessagesTable.KEY_LONGITUDE)));
        message.setGlobalMessageType(messageType);

        if (messageType == MessagesTable.MESSAGE_TYPE_INTENT_START) {
            message.setStart(true);
        }

        return message;
    }
}

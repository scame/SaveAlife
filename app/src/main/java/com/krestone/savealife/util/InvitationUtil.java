package com.krestone.savealife.util;


import android.content.Context;
import android.content.Intent;

public class InvitationUtil {

    // EXTRA_SUBJECT should be a pure link, otherwise Facebook app will crash
    public static void showInviteWindow(Context context, String recipient) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "savealife.com");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello " + recipient + "! Join SaveaLife community savealife.com");

        Intent chooserIntent = Intent.createChooser(shareIntent, "Share with");
        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(chooserIntent);
    }
}

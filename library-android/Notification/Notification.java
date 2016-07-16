public void createNotificaton(String noti) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.notification_template_icon_bg).setContentTitle("Money Management").setContentText(noti);
        mBuilder.setSmallIcon(R.drawable.a32);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        mBuilder.setVibrate(new long[]{0, 200, 200, 600, 600});
        mBuilder.setLights(Color.RED, 3000, 3000);
        mBuilder.setAutoCancel(true);
        //khi can tao pending intent
//        Intent intent = new Intent(getActivity(),MainActivity.class);
//        PendingIntent pending = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//       mBuilder.setContentIntent(pending);
        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(001, mBuilder.build());
    }
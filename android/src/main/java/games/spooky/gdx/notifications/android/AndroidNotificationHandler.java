/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2021 Spooky Games
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package games.spooky.gdx.notifications.android;

import static games.spooky.gdx.notifications.NotificationUtils.checkNotNull;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import games.spooky.gdx.notifications.NotificationHandler;
import games.spooky.gdx.notifications.NotificationParameters;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidNotificationHandler implements NotificationHandler {

	private final String CHANNEL_ID = "19161107";

	private final Context context;
	private final NotificationManager manager;

	private int icon;

	public AndroidNotificationHandler(Context context) {
		this.context = context;
		this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		if (this.manager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Default", NotificationManager.IMPORTANCE_DEFAULT);
			channel.setDescription("Default channel");
			this.manager.createNotificationChannel(channel);
		}

		this.icon = context.getApplicationInfo().icon;
	}

	@Override
	public void showNotification(NotificationParameters parameters) {

		checkNotNull(parameters, "parameters");

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

		decorate(builder, parameters);

		manager.notify(parameters.getId(), builder.build());
	}

	@Override
	public void hideNotification(NotificationParameters parameters) {
		manager.cancel(parameters.getId());
	}

	/**
	 * Sets the small icon for all notifications
	 * See <a href="https://developer.android.com/guide/practices/ui_guidelines/icon_design_status_bar">here</a> for guidelines
	 * @param icon A resource ID in the application's package of the drawable to use.
	 */
	public void setIcon(int icon) {
		this.icon = icon;
	}

	protected void decorate(NotificationCompat.Builder builder, NotificationParameters parameters) {

		checkNotNull(parameters.getTitle(), "parameters.title");
		checkNotNull(parameters.getText(), "parameters.text");

		builder
			.setDefaults(Notification.DEFAULT_ALL)
			.setContentTitle(parameters.getTitle())
			.setContentText(parameters.getText())
			.setSmallIcon(this.icon);
	}

}

/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017 Spooky Games
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
package net.spookygames.gdx.notifications.android;

import static net.spookygames.gdx.notifications.NotificationUtils.checkNotNull;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import net.spookygames.gdx.notifications.NotificationHandler;
import net.spookygames.gdx.notifications.NotificationParameters;
//import net.spookygames.gdx.notifications.android.R;
import android.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidNotificationHandler implements NotificationHandler {

	private final Context context;
	private final NotificationManager manager;

	public AndroidNotificationHandler(Context context) {
		this.context = context;
		this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void showNotification(NotificationParameters parameters) {
		checkNotNull(parameters, "parameters");

		Builder builder = new Builder(context);
		
		decorate(builder, parameters);
		
		@SuppressWarnings("deprecation")
		Notification notification = builder.getNotification();	// build() is only available starting from Jelly Bean

		int notificationId = parameters.id;

		manager.notify(notificationId, notification);
	}

	@Override
	public void hideNotification(NotificationParameters parameters) {
		manager.cancel(parameters.id);
	}

	protected void decorate(Builder builder, NotificationParameters parameters) {
		checkNotNull(parameters.title, "parameters.title");
		checkNotNull(parameters.text, "parameters.text");
		
		builder
			.setDefaults(Notification.DEFAULT_ALL)
			.setContentTitle(parameters.title)
			.setContentText(parameters.text)
			.setSmallIcon(R.drawable.ic_dialog_info) // Should have been local ic_launcher, really, so please FIXME gradle build
				;
	}

}

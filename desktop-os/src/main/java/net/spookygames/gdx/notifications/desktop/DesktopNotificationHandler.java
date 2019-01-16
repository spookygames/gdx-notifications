/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2019 Spooky Games
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
package net.spookygames.gdx.notifications.desktop;

import static net.spookygames.gdx.notifications.NotificationUtils.checkNotNull;

import net.spookygames.gdx.notifications.NotificationHandler;
import net.spookygames.gdx.notifications.NotificationParameters;
import notify.MessageType;
import notify.Notify;

public class DesktopNotificationHandler implements NotificationHandler {

	private final Notify notifier = Notify.getInstance();

	public DesktopNotificationHandler() {
		super();
	}

	@Override
	public void showNotification(NotificationParameters parameters) {

		checkNotNull(parameters, "parameters");
		checkNotNull(parameters.getTitle(), "parameters.title");
		checkNotNull(parameters.getText(), "parameters.text");
		
		notifier.notify(defineMessageType(parameters), parameters.getTitle(), parameters.getText());
	}

	@Override
	public void hideNotification(NotificationParameters parameters) {
		// Not supported
	}

	protected MessageType defineMessageType(NotificationParameters parameters) {
		Object payload = parameters.getPayload();
		if(payload instanceof MessageType)
			return (MessageType) payload;

		return MessageType.NONE;
	}

	public static void main(String[] args) {
		MessageType[] types = MessageType.values();
		NotificationHandler handler = new DesktopNotificationHandler();

		for (int i = 0; i < 10; i++) {
			NotificationParameters parameters = new NotificationParameters(i, "Notification " + i, "Lorem ipsum");
			parameters.setPayload(types[i % types.length]);
			handler.showNotification(parameters);
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 10; i++) {
			NotificationParameters parameters = new NotificationParameters();
			handler.hideNotification(parameters);

			parameters.setId(i);
		}
	}

}

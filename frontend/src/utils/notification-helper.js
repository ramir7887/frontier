import { EventEmitter } from "./event-emitter";

export const notificationEventBus = new EventEmitter();

export function showNotification(text) {
    notificationEventBus.emit("show", text);
}

export class EventEmitter {
    subscribers = {};

    on(event, callback) {
        if (!this.subscribers[event]) {
            this.subscribers[event] = [];
        }

        this.subscribers[event].push(callback);

        return {
            unsubscribe: () =>
                (this.subscribers[event] = this.subscribers[event].filter(cb => cb !== callback))
        };
    }

    emit(event, ...args) {
        (this.subscribers[event] ?? []).forEach(cb => cb(...args));
    }
}

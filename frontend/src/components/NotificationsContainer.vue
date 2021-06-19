<template>
    <v-snackbar top right color="primary" v-model="isShowing">{{ notificationText }}</v-snackbar>
</template>

<script>
import { notificationEventBus } from "../utils/notification-helper";

export default {
    name: "NotificationsContainer",
    data: () => ({
        isShowing: false,
        notificationColor: "",
        notificationText: ""
    }),
    subscription: null,
    created() {
        this.subscription = notificationEventBus.on("show", text => {
            this.notificationText = text;
            this.isShowing = true;
        });
    },
    beforeDestroy() {
        this.subscription.unsubscribe();
    }
};
</script>

<style scoped></style>

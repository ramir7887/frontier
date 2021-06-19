<template>
    <v-app>
        <header>
            <router-link to="/" class="logo"></router-link>
            <div class="switch">
                <span>Области</span>
                <v-switch color="primary" :value="switchValue" @change="toggleSwitch" />
                <span>Источники</span>
            </div>
        </header>

        <main>
            <router-view />
        </main>

        <NotificationsContainer />
    </v-app>
</template>

<script>
import NotificationsContainer from "./components/NotificationsContainer";
import { mapMutations } from "vuex";

export default {
    components: { NotificationsContainer },
    data: () => ({
        switchValue: false
    }),
    methods: {
        ...mapMutations(["changeDataSourceName"]),
        toggleSwitch() {
            this.switchValue = !this.switchValue;

            if (this.switchValue) {
                this.changeDataSourceName("Источники");
            } else {
                this.changeDataSourceName("Области");
            }
        }
    }
};
</script>

<style scoped lang="scss">
main {
    width: 1200px;
    margin: 0 auto;
}

header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 40px;
    border-bottom: 1px solid #e5e7eb;

    .logo {
        width: 45px;
        height: 45px;
        background: #10b981;
        border-radius: 50%;
    }

    .switch {
        display: flex;
        align-items: center;

        > :not(:last-child) {
            margin-right: 30px;
        }
    }
}
</style>

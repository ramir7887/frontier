<template>
    <v-app>
        <header>
            <div class="d-flex">
            <router-link to="/" class="logo">
                <v-img class="logo" src="./assets/img/logo1.png" alt="Subject in promising direction"></v-img>
            </router-link>
            <div class="title align-center  ml-4 my-auto text-h4 font-weight-bold" color="green">Фронтир NG</div>
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
        /*background: #10b981;*/
        border-radius: 50%;
    }
    .title {
        color: #10b981;
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

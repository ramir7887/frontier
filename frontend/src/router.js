import Vue from "vue";
import VueRouter from "vue-router";
import Home from "./views/Home.vue";

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/analyze",
        name: "Analyze",
        component: () => import("./views/AnalyzePage.vue")
    },
    {
        path: "/charts",
        name: "Charts",
        component: () => import("./views/ChartsPage.vue")
    },
    {
        path: "/diagramm",
        name: "Diagramm",
        component: () => import("./views/DiagrammPage.vue")
    },
    {
        path: "/tag-clouds",
        name: "TagCloud",
        component: () => import("./views/TagCloudPage.vue")
    }
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;

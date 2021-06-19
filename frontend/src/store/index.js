import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        dataSourceName: "Области"
    },
    mutations: {
        changeDataSourceName(state, value) {
            state.dataSourceName = value;
        }
    },
    actions: {},
    modules: {}
});

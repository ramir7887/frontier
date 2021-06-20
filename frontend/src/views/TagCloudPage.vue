<template>
    <div>
        <div class="heading">
            <div class="buttons">
                <v-btn 
                    outlined 
                    color="primary"
                    class="mr-2" 
                    @click="goToChartPage">
                    <v-icon left> bar_chart </v-icon>
                    Онлайн анализ
                </v-btn>

                <v-btn
                    outlined
                    color="primary"
                    depressed
                    @click="goToDiagrammPage"
                >
                    <v-icon left> table_view </v-icon>
                    Матрица
                </v-btn>
            </div>
        </div>
        <div v-if="isLoading" class="d-flex justify-center mt-5">
            <v-progress-circular size="50" color="primary" indeterminate />
        </div>
        <div class="d-flex justify-center diagramm-container">
            <vue-word-cloud
                style="height: 800px; width: 1000px"
                :words="tagWords"
                :color="
                    ([, weight]) => (weight > 10 ? 'DeepPink' : weight > 5 ? 'RoyalBlue' : 'Indigo')
                "
                font-family="Roboto"
            />
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { Chart, registerables } from "chart.js";
import VueWordCloud from "vuewordcloud";

Chart.register(...registerables);

const colors = ["red", "blue", "green"];

export default {
    components: {
        [VueWordCloud.name]: VueWordCloud
    },
    name: "TagCloudPage",
    data: () => ({
        isLoading: false,
        tagWords: [],
        subjectName: ""
    }),
    mounted() {
        const subjectNames = this.$route.query.subjects.split(",");
        this.subjectName = subjectNames[0];
        Promise.all([this.initTagWords(subjectNames)]).then(() => (this.isLoading = false));
    },
    methods: {
        async initTagWords(subjectNames) {
            subjectNames.map((name) => {
                return new Promise((resolve) => {
                    axios
                        .get(`https://frontier-analytics.kovalev.team/keywords?query=${name}`)
                        .then((d) => {
                            this.tagWords = this.tagWords.concat(
                                Object.entries(d.data.keywords)
                                    .filter((a) => a[1] > 1)
                                    .map((a) => {
                                        if (a[1] > 10) {
                                            a[1] = 10;
                                        }
                                        return a;
                                    })
                            );
                        });
                });
            });
        },
        goToChartPage() {
            const subjectName = this.subjectName;
            this.$router.push(`/charts?subjects=${subjectName}`);
        },
        goToDiagrammPage() {
            const subjectName = this.subjectName;
            this.$router.push(`/diagramm?subjects=${subjectName}`);
        }
    }
};
</script>

<style scoped>
.heading {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px;
}
.cover {
    width: 700px;
    height: 600px;
    position: absolute;
}
.bubles {
    position: relative;
    left: -60px;
    top: -27px;
}
.diagramm-container {
    margin-top: 75px;
}
</style>


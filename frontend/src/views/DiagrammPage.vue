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
                    @click="goToCloudPage"
                >
                    <v-icon left> cloud </v-icon>
                    Карта технологий
                </v-btn>
            </div>
        </div>
        <div v-if="isLoading" class="d-flex justify-center mt-5">
            <v-progress-circular size="50" color="primary" indeterminate />
        </div>
        <div class="d-flex justify-center diagramm-container">
            <div class="cover-div">
                <img class="cover" src="../assets/img/cover.png" alt="Subject is frontier" />
                <canvas class="bubles" ref="articlesChart" height="700" width="750"></canvas>
            </div>
        </div>
        <vue-word-cloud
            style="height: 480px; width: 640px"
            :words="tagWords"
            :color="
                ([, weight]) => (weight > 10 ? 'DeepPink' : weight > 5 ? 'RoyalBlue' : 'Indigo')
            "
            font-family="Roboto"
        />
        <div class="d-flex justify-center">
            <div>
                <v-row>
                    <v-col>
                        <v-select
                            :items="segments"
                            v-model="selectedSegment"
                            label="Область применения"
                        ></v-select>
                    </v-col>
                    <v-col>
                        <v-text-field
                            type="number"
                            v-model="level"
                            step="0.1"
                            required
                        ></v-text-field>
                    </v-col>
                    <v-col>
                        <v-btn v-on:click="addData" class="mr-4" type="submit"> Добавить </v-btn>
                    </v-col>
                </v-row>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

export default {
    name: "DiagrammPage",
    data: () => ({
        yearArticles: [],
        isLoading: true,
        chart: {},
        segments: [],
        selectedSegment: null,
        endPercent: null,
        level: null,
        subjectName: ""
    }),
    mounted() {
        const subjectNames = this.$route.query.subjects.split(",");
        this.subjectName = subjectNames[0];
        Promise.all([this.initYearArticleChart(subjectNames)]).then(() => (this.isLoading = false));
    },
    methods: {
        addData() {
            if (!this.selectedSegment) {
                return null;
            }
            this.chart.data.datasets.push({
                label: this.selectedSegment,
                data: [{ x: this.endPercent, y: this.level, r: 15 }],
                borderColor: "black",
                backgroundColor: "white"
            });
            this.chart.update();
        },
        async initYearArticleChart(subjectNames) {
            const yearArticlesPromises = subjectNames.map((name) => {
                return new Promise((resolve) => {
                    axios
                        .get(`https://frontier-api.kovalev.team/subject/findByName?name=${name}`)
                        .then((d) =>
                            resolve({
                                name,
                                data: d.data
                            })
                        );
                });
            });

            const yearArticles = await Promise.all(yearArticlesPromises);
            this.segments = yearArticles[0].data.segment.split(",");
            this.endPercent = yearArticles[0].data.readinessToday;
            const labels = [];
            let datasets = [];

            const ctx = this.$refs.articlesChart;
            this.ctx = ctx;
            this.chart = new Chart(ctx, {
                type: "bubble",
                data: {
                    labels,
                    datasets
                },

                options: {
                    responsive: false,
                    scales: {
                        x: {
                            suggestedMin: 0,
                            suggestedMax: 100,
                            title: {
                                display: true,
                                text: "процент готовности технологий",
                                font: {
                                    family: "Comic Sans MS",
                                    size: 20,
                                    weight: "bold",
                                    lineHeight: 1.2
                                }
                            },
                            grid: {
                                display: false,
                                drawBorder: false,
                                drawOnChartArea: false,
                                drawTicks: false
                            }
                        },
                        y: {
                            suggestedMin: 1,
                            suggestedMax: 5,
                            title: {
                                display: true,
                                text: "Степень влияния области применения",
                                font: {
                                    family: "Comic Sans MS",
                                    size: 20,
                                    weight: "bold",
                                    lineHeight: 1.2
                                }
                            },
                            grid: {
                                display: false,
                                drawBorder: false,
                                drawOnChartArea: false,
                                drawTicks: false
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            position: "top",
                            display: false
                        },
                        title: {
                            display: true,
                            text: ""
                        }
                    }
                }
            });
        },
        goToChartPage() {
            const subjectName = this.subjectName;
            this.$router.push(`/charts?subjects=${subjectName}`);
        },
        goToCloudPage() {
            const subjectName = this.subjectName;
            this.$router.push(`/tag-clouds?subjects=${subjectName}`);
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


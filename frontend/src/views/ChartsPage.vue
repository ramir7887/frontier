<template>
    <div>
        <div class="heading">
            <div class="buttons">
                <v-btn
                    outlined
                    color="primary"
                    :disabled="!disableBtn"
                    class="mr-2"
                    @click="goToDiagrammPage"
                >
                    <v-icon left> table_view </v-icon>
                    Матрица
                </v-btn>

                <v-btn
                    outlined
                    color="primary"
                    :disabled="!disableBtn"
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

        <div class="d-flex">
            <canvas ref="articlesChart" height="400" width="400" />
            <canvas ref="moneyChart" height="400" width="400" />
            <canvas ref="patentsChart" height="400" width="400" />
        </div>

        <canvas ref="totalChart" height="400" width="1200" class="mt-6" />
    </div>
</template>

<script>
import axios from "axios";
import { Chart, registerables } from "chart.js";
import { httpClient } from "../http-client";
import _ from "lodash";

Chart.register(...registerables);

const colors = ["#F87171", "#38BDF8", "#34D399"];
const predictionColors = ["#B91C1C", "#0369A1", "#047857"];
const predictionColorsBackground = ["#FEE2E2", "#E0F2FE", "#D1FAE5"];

export default {
    name: "ChartsPage",
    data: () => ({
        yearArticles: [],
        isLoading: true,
        disableBtn: false,
        subjectName: "",
        articlesDataset: null,
        moneyDataset: null,
        patentsDataset: null
    }),
    mounted() {
        const subjectNames = this.$route.query.subjects.split(",");
        if (subjectNames.length > 1) {
            this.disableBtn = false;
        } else {
            this.disableBtn = true;
            this.subjectName = subjectNames[0];
        }
        Promise.all([
            this.initYearArticleChart(subjectNames),
            this.initMoneyCharts(subjectNames),
            this.initPatentsChart(subjectNames)
        ]).then(() => {
            this.isLoading = false;
            this.calculateTotalChart();
        });
    },
    methods: {
        async initYearArticleChart(subjectNames) {
            const yearArticlesPromises = subjectNames.map(name => {
                return axios
                    .get(
                        `https://frontier-analytics.kovalev.team/year-articles?query=${name}&start_year=2017&end_year=2020`
                    )
                    .then(d => ({
                        name,
                        data: d.data.data
                    }));
            });

            const yearArticles = await Promise.all(yearArticlesPromises);

            const labels = ["2017", "2018", "2019", "2020"];
            const datasets = yearArticles.map((a, i) => ({
                label: a.name,
                data: a.data.map((d, index) => ({ y: d.count, x: labels[index] })),
                borderColor: colors[i] ?? "gray"
            }));

            this.articlesDataset = datasets;

            const futureYears = ["2021", "2022", "2023", "2024", "2025"];

            const responses = await Promise.all(
                subjectNames.map(name => {
                    return axios
                        .post(`https://frontier-maxim.kovalev.team/articles/`, {
                            name,
                            start_year: 2006,
                            end_year: 2020
                        })
                        .then(r => ({
                            data: r.data,
                            name
                        }));
                })
            );

            const predictionDatasets = [];

            responses.forEach((prediction, i) => {
                predictionDatasets.push({
                    legend: false,
                    data: Object.values(prediction.data).map((d, i) => ({
                        y: d.yhat,
                        x: futureYears[i]
                    })),
                    borderColor: predictionColors[i] ?? "gray"
                });

                predictionDatasets.push({
                    label: "",
                    data: Object.values(prediction.data).map((d, i) => ({
                        y: d.yhat_lower,
                        x: futureYears[i]
                    })),
                    borderColor: "transparent",
                    backgroundColor: predictionColorsBackground[i] ?? "lighgray",
                    pointRadius: 0,
                    tension: 0,
                    fill: "-1"
                });

                predictionDatasets.push({
                    label: "",
                    data: Object.values(prediction.data).map((d, i) => ({
                        y: d.yhat_upper,
                        x: futureYears[i]
                    })),
                    borderColor: "transparent",
                    backgroundColor: predictionColorsBackground[i] ?? "lightgray",
                    pointRadius: 0,
                    tension: 0,
                    fill: "-1"
                });
            });

            datasets.forEach((d, i) => {
                d.data.push(predictionDatasets[i * 3].data[0]);
            });

            const ctx = this.$refs.articlesChart;
            new Chart(ctx, {
                type: "line",
                data: {
                    labels,
                    datasets: [...datasets, ...predictionDatasets]
                },
                options: {
                    responsive: false,
                    plugins: {
                        legend: {
                            position: "top",
                            labels: {
                                filter(item) {
                                    return item && subjectNames.includes(item.text);
                                }
                            }
                        },
                        title: {
                            display: true,
                            text: "Статьи"
                        }
                    }
                }
            });
        },
        async initMoneyCharts(subjectNames) {
            const money = (await httpClient().post("/subject/money", subjectNames)).data;
            money.forEach(m => (m.money = m.money.filter(r => r.year !== "2021")));

            const labels = ["2017", "2018", "2019", "2020"];
            const datasets = money.map((m, i) => ({
                label: m.subjectScience.name,
                data: m.money.map((r, index) => ({
                    y: r.amount,
                    x: labels[index]
                })),
                borderColor: colors[i] ?? "gray"
            }));

            this.moneyDataset = datasets;

            const predictionData = await Promise.all(
                subjectNames.map(s => {
                    return axios
                        .post("https://frontier-maxim.kovalev.team/money/", [s])
                        .then(response => response.data);
                })
            );

            const futureYears = ["2021", "2022", "2023", "2024", "2025"];

            const predictionDatasets = [];
            predictionData.forEach((prediction, i) => {
                predictionDatasets.push({
                    legend: false,
                    data: Object.values(prediction).map((d, i) => ({
                        y: d.yhat,
                        x: futureYears[i]
                    })),
                    borderColor: predictionColors[i] ?? "gray"
                });

                predictionDatasets.push({
                    label: "",
                    data: Object.values(prediction).map((d, i) => ({
                        y: d.yhat_lower,
                        x: futureYears[i]
                    })),
                    borderColor: "transparent",
                    backgroundColor: predictionColorsBackground[i] ?? "lighgray",
                    pointRadius: 0,
                    tension: 0,
                    fill: "-1"
                });

                predictionDatasets.push({
                    label: "",
                    data: Object.values(prediction).map((d, i) => ({
                        y: d.yhat_upper,
                        x: futureYears[i]
                    })),
                    borderColor: "transparent",
                    backgroundColor: predictionColorsBackground[i] ?? "lightgray",
                    pointRadius: 0,
                    tension: 0,
                    fill: "-1"
                });
            });

            datasets.forEach((d, i) => {
                d.data.push(predictionDatasets[i * 3].data[0]);
            });

            const ctx = this.$refs.moneyChart;
            new Chart(ctx, {
                type: "line",
                data: {
                    labels,
                    datasets: [...datasets, ...predictionDatasets]
                },
                options: {
                    responsive: false,
                    plugins: {
                        legend: {
                            position: "top",
                            labels: {
                                filter(item) {
                                    return item && subjectNames.includes(item.text);
                                }
                            }
                        },
                        title: {
                            display: true,
                            text: "Бюджет"
                        }
                    }
                }
            });
        },
        async initPatentsChart(subjectNames) {
            const labels = ["2017", "2018", "2019", "2020"];

            const patentsPromises = [];

            labels.forEach(year => {
                subjectNames.forEach(name => {
                    const promise = httpClient()
                        .get(`/patents/findPatents?start=${year}0101&end=${year}1231&key=${name}`)
                        .then(response => ({
                            count: response.data.count,
                            name,
                            year
                        }));

                    patentsPromises.push(promise);
                });
            });

            const result = await Promise.all(patentsPromises);
            const groupedResult = _.groupBy(result, "name");
            const datasets = Object.entries(groupedResult).map(([name, data], i) => ({
                label: name,
                data: data.map((d, index) => ({ y: d.count, x: labels[index] })),
                borderColor: colors[i] ?? "gray"
            }));

            this.patentsDataset = datasets;

            const ctx = this.$refs.patentsChart;
            new Chart(ctx, {
                type: "line",
                data: {
                    labels,
                    datasets
                },
                options: {
                    responsive: false,
                    plugins: {
                        legend: {
                            position: "top"
                        },
                        title: {
                            display: true,
                            text: "Патенты"
                        }
                    }
                }
            });
        },
        calculateTotalChart() {
            const datasets = [];
            for (let i = 0; i < this.articlesDataset.length; i++) {
                const data = _.cloneDeep(this.articlesDataset[i].data);
                for (let j = 0; j < this.moneyDataset[i].data.length; j++) {
                    data[j].y = parseInt(data[j].y) + parseInt(this.moneyDataset[i].data[j].y);
                }
                for (let j = 0; j < this.patentsDataset[i].data.length; j++) {
                    data[j].y = parseInt(data[j].y) + parseInt(this.patentsDataset[i].data[j].y);
                }
                datasets.push({
					label: this.articlesDataset[i].label,
					borderColor: this.articlesDataset[i].borderColor,
					data
				});
            }

			const labels = ["2017", "2018", "2019", "2020"];

			const ctx = this.$refs.totalChart;
			new Chart(ctx, {
				type: "line",
				data: {
					labels,
					datasets
				},
				options: {
					responsive: false,
					plugins: {
						legend: {
							position: "top"
						},
						title: {
							display: true,
							text: "Суммарный тренд"
						}
					}
				}
			});
        },
        goToCloudPage() {
            const subjectName = this.subjectName;
            this.$router.push(`/tag-clouds?subjects=${subjectName}`);
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
</style>
